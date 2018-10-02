package com.example.demo.web.controlers;

import com.example.demo.models.bindingmodel.UserRegisterBindingModel;
import com.example.demo.models.service.UserServiceModel;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return this.view("login");
    }
    @GetMapping("/register")
    public ModelAndView register(){
        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView confirmRegister(@ModelAttribute UserRegisterBindingModel
                                                    userRegisterBindingModel){
      if (!userRegisterBindingModel.getPassword()
      .equals(userRegisterBindingModel.getConfirmPassword())){
          return this.view("register");
      }
      this.userService.createUser(this.modelMapper
      .map(userRegisterBindingModel, UserServiceModel.class));

      return this.view("login");
    }
}
