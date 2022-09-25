package com.application.pedidoapi.utils;

import org.springframework.web.servlet.ModelAndView;

public class DataErrorHandler {

    public static ModelAndView throwMessage(String message){
        return new ModelAndView("invalid-data-errorhandler")
                .addObject("message", message);
    }
}
