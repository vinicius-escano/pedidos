package com.application.pedidoapi.repositorytest;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.PedidoItemRepository;
import com.application.pedidoapi.repository.ProdutoJPARepository;
import com.application.pedidoapi.service.PedidoItemService;
import com.application.pedidoapi.service.ProdutoService;
import com.application.pedidoapi.util.TestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProdutoJPARepositoryTest {

    @MockBean
    ProdutoJPARepository produtoJPARepository;

    @MockBean
    PedidoItemService pedidoItemService;

    @InjectMocks
    ProdutoService produtoService;

    @MockBean
    PedidoItemRepository pedidoItemRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    @Order(1)
    public void saveProduto() {
        when(produtoJPARepository.save(any())).thenReturn(TestUtil.getProduto(Tipo.PRODUTO, true));
        Produto produto = TestUtil.getProduto(Tipo.PRODUTO, true);
        produto.setId(null);

        // When
        Optional<Produto> opProduto = Optional.ofNullable(produtoJPARepository.save(produto));

        //Then
        verify(produtoJPARepository).save(produto);
        verifyNoMoreInteractions(produtoJPARepository);
        assertTrue(opProduto.isPresent());
    }

    @Test
    @Order(2)
    public void saveServico() {
        when(produtoJPARepository.save(any())).thenReturn(TestUtil.getProduto(Tipo.SERVICO, true));
        Produto servico = TestUtil.getProduto(Tipo.SERVICO, true);
        servico.setId(null);
        Optional<Produto> opProduto = Optional.ofNullable(produtoJPARepository.save(servico));
        verify(produtoJPARepository).save(servico);
        verifyNoMoreInteractions(produtoJPARepository);
        assertTrue(opProduto.isPresent());
    }

    @Test
    @Order(3)
    public void update() {
        // Arrange
        ArgumentCaptor<Produto> entityArgumentCaptor = ArgumentCaptor.forClass(Produto.class);
        Produto inputDto = TestUtil.getProduto(Tipo.PRODUTO, false);

        // Act
        Optional<Produto> opProduto = Optional.ofNullable(produtoJPARepository.save(inputDto));
        verify(produtoJPARepository, times(1)).save(entityArgumentCaptor.capture());

        // Assert
        Produto savedEntity = entityArgumentCaptor.getValue();
        Assertions.assertEquals(inputDto.getId(), savedEntity.getId());
    }

    @Test
    @Order(4)
    public void findById() {
        UUID uuid = new UUID(1, 2);
        // Given
        when(produtoJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getProduto(Tipo.PRODUTO, true)));
        // When
        Optional<Produto> opProduto = produtoService.findById(uuid);
        // Then
        verify(produtoJPARepository).findById(any());
        verifyNoMoreInteractions(produtoJPARepository);
        assertTrue(opProduto.isPresent());
    }

    @Test
    @Order(5)
    public void findAll() {
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(produtoJPARepository.findAll(pageable)).thenReturn(TestUtil.getItemPage(20, 0));

        // When
        Page<Produto> itemPage = produtoService.findAll(pageable);

        // Then
        verify(produtoJPARepository).findAll(pageable);
        verifyNoMoreInteractions(produtoJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    @Order(6)
    public void findAllPaginated(){
        Pageable pageable = PageRequest.of(0, 20);
        String descricaoPesquisa = "Teste";
        // Given
        when(produtoJPARepository.findByDescricaoPageable(descricaoPesquisa, pageable)).thenReturn(TestUtil.getItemPage(2, 0));

        // When
        Page<Produto> itemPage = produtoService.findAllByDescricaoPageable(descricaoPesquisa, pageable);

        // Then
        verify(produtoJPARepository).findByDescricaoPageable(descricaoPesquisa, pageable);
        verifyNoMoreInteractions(produtoJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    @Order(7)
    public void delete() {
        // Given
        when(pedidoItemService.findPedidoItemByProdutoId(any())).thenReturn(TestUtil.getPedidoItemList(2));
        doNothing().when(produtoJPARepository).delete(any());

        // When
        boolean excluido = produtoService.delete(TestUtil.getServico(Tipo.SERVICO, true), new ArrayList<>());

        verify(produtoJPARepository).delete(any());
        verify(pedidoItemService).findAllWithProduto(any());
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(excluido);
    }

}
