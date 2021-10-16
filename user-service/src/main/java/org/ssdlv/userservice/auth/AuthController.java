package org.ssdlv.userservice.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.utils.forms.AuthForm;

import javax.validation.Valid;

@Controller
public class AuthController {
    @GetMapping(value = "/login", produces = "text/html")
    public String login(Model model) {
        AuthForm authForm = new AuthForm();
        authForm.setEmail("denver@ssdlv.com");
        authForm.setPassword("password");
        model.addAttribute("auth", authForm);
        return "login";
    }

    @PostMapping(path = "/loginMvc")
    public String saveProduct(@ModelAttribute @Valid AuthForm authForm, BindingResult bindingResult){
        if(bindingResult.hasGlobalErrors()) {
            System.err.println(" [1] " + authForm.getEmail() + " [] " + authForm.getPassword());
            return "/login";
        }
        //productRepository.save(product);
        System.err.println(" [2] " + authForm.getEmail() + " [] " + authForm.getPassword());
        return "redirect:/login";
    }
}
