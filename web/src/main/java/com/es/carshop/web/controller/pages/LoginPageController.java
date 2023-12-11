package com.es.carshop.web.controller.pages;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping(value = "/signIn")
public class LoginPageController {
    @RequestMapping("/userMiniStatus")
    public String getUserMiniStatus() {
        return "userMiniStatus";
    }

    @GetMapping
    public String loginPage(HttpServletRequest request) {
        if (!Objects.equals(request.getHeader("Referer"), "http://localhost:8080/carshop-web/signIn")) {
            request.getSession().setAttribute("referer", request.getHeader("Referer"));
        }
        return "login";
    }

    @GetMapping("/afterLogin")
    public String afterLogin(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String targetUrl = (String) request.getSession().getAttribute("referer");
        request.getSession().removeAttribute("referer");
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:" + targetUrl;
        } else {
            return "redirect:/";
        }
    }
}
