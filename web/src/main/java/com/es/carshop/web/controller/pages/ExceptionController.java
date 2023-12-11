package com.es.carshop.web.controller.pages;

import com.es.core.entity.car.carNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(carNotFoundException.class)
    public ModelAndView handleException(carNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPages/notFoundcar");
        modelAndView.addObject("errorMessage", exception.getErrorMessage());
        return modelAndView;
    }
}
