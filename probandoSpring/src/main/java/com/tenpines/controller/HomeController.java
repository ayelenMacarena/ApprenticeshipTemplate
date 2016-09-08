package com.tenpines.controller;

import com.tenpines.Sumador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;


@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String suma() {
        return "paginaSumador";
    }


    @RequestMapping(value = "/resultado")
    public ModelAndView resultado() {
        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime dateNow = LocalDateTime.now();
        Sumador myAcum = new Sumador();
        modelAndView.setViewName("resultadoSumador");
        modelAndView.addObject("hora", dateNow);
        modelAndView.addObject("acumulador", myAcum);


        return modelAndView;
    }

}