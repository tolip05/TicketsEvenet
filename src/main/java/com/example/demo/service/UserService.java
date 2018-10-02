package com.example.demo.service;

import com.example.demo.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {
boolean createUser(UserServiceModel userServiceModel);
Set<UserServiceModel>getAll ();
UserServiceModel getById(String id);
UserServiceModel getByUserName(String userName);
boolean promoteUser(String id);
boolean demoteUser(String id);
}
