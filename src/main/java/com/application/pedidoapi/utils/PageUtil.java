package com.application.pedidoapi.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    private static final int rowsPerPage = 15;

    public static Pageable getPageable(int page) {
        return PageRequest.of(page, rowsPerPage);
    }

}