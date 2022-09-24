package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    public Servico save(Servico servico){
        return servicoRepository.save(servico);
    }

    public List<Servico> findAll(){
        return servicoRepository.findAll();
    }
}
