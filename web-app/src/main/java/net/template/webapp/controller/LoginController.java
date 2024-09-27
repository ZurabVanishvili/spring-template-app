package net.template.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/public/login")
    public ModelAndView loginPage() {
        return new ModelAndView("forward:" + "index.html");
    }

}

