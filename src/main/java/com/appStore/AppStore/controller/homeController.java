package com.appStore.AppStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

    @RequestMapping("/administrativo")
    public String home(){
        return "administrativo/home";
    }
}
