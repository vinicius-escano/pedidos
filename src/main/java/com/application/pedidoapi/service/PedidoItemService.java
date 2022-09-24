package com.application.pedidoapi.service;

import com.application.pedidoapi.repository.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository pedidoItemRepository;
}
