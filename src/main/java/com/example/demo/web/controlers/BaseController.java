package com.example.demo.web.controlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String viewName,ModelAndView modelAndView){
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
protected String getPrincipalAuthority(Authentication authentication){
    GrantedAuthority principalAuthority =
            authentication.getAuthorities()
            .stream()
            .findFirst()
            .orElse(null);
return principalAuthority != null
        ? principalAuthority.getAuthority()
        : null;

}

    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String route) {
        return this.view("redirect:" + route);
    }
}
