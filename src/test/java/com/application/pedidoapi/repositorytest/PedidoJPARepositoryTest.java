package com.application.pedidoapi.repositorytest;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.repository.PedidoItemJPARepository;
import com.application.pedidoapi.repository.PedidoJPARepository;
import com.application.pedidoapi.service.PedidoItemService;
import com.application.pedidoapi.service.PedidoService;
import com.application.pedidoapi.util.TestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PedidoJPARepositoryTest {

    @MockBean
    PedidoJPARepository pedidoJPARepository;

    @MockBean
    PedidoItemService pedidoItemService;

    @InjectMocks
    PedidoService pedidoService;

    @Mock
    PedidoItemJPARepository pedidoItemJPARepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    @Order(1)
    public void savePedido() {
        Pedido pedido = TestUtil.getPedido(2);

        when(pedidoJPARepository.save(any())).thenReturn(pedido);

        Pedido pedidoToSave = TestUtil.getPedido(2);
        pedidoToSave.setId(null);

        // When
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoJPARepository.save(pedido));

        //Then
        verify(pedidoJPARepository).save(any());
        verifyNoMoreInteractions(pedidoJPARepository);
        assertTrue(opPedido.isPresent());
    }

    @Test
    @Order(2)
    public void updatePedido() {
        // Arrange
        ArgumentCaptor<Pedido> entityArgumentCaptor = ArgumentCaptor.forClass(Pedido.class);
        Pedido inputPedido = TestUtil.getPedido(2);
        inputPedido.setDesconto(15.0);

        // Act
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoJPARepository.save(inputPedido));
        verify(pedidoJPARepository, times(1)).save(entityArgumentCaptor.capture());

        // Assert
        Pedido savedEntity = entityArgumentCaptor.getValue();
        Assertions.assertEquals(inputPedido.getId(), savedEntity.getId());
    }

    @Test
    @Order(3)
    public void findById() {
        UUID uuid = new UUID(1, 2);
        // Given
        when(pedidoJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getPedido(2)));
        // When
        Optional<Pedido> opPedido = pedidoService.findById(uuid);
        // Then
        verify(pedidoJPARepository).findById(any());
        verifyNoMoreInteractions(pedidoJPARepository);
        assertTrue(opPedido.isPresent());
    }

    @Test
    @Order(4)
    public void findAll() {
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(pedidoJPARepository.findAll(pageable)).thenReturn(TestUtil.getPedidoPage(20, 0));

        // When
        Page<Pedido> itemPage = pedidoService.findAll(pageable);

        // Then
        verify(pedidoJPARepository).findAll(pageable);
        verifyNoMoreInteractions(pedidoJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    @Order(5)
    public void findAllPaginated(){
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(pedidoJPARepository.findBySituacaoPedidoEquals(SituacaoPedido.EM_ABERTO, pageable)).thenReturn(TestUtil.getPedidoPage(2, 0));

        // When
        Page<Pedido> itemPage = pedidoJPARepository.findBySituacaoPedidoEquals(SituacaoPedido.EM_ABERTO, pageable);

        // Then
        verify(pedidoJPARepository).findBySituacaoPedidoEquals(SituacaoPedido.EM_ABERTO, pageable);
        verifyNoMoreInteractions(pedidoJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    @Order(7)
    public void delete() {
        // Given
        when(pedidoItemService.delete(anyList())).thenReturn(true);
        doNothing().when(pedidoJPARepository).delete(any());

        // When
        boolean excluido = pedidoService.delete(TestUtil.getPedido(2));

        verify(pedidoJPARepository).delete(any());
        verify(pedidoItemService).delete(anyList());
        verifyNoMoreInteractions(pedidoJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(excluido);
    }

}
