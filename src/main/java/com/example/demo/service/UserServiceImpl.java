package com.example.demo.service;

import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.models.service.UserServiceModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private Set<UserRole> getAuthorities(String authority) {
        Set<UserRole> userAuthorities = new HashSet<>();
        userAuthorities.add(this.roleRepository.getByAuthority(authority));
        for (UserRole userAuthority : userAuthorities) {
            System.out.println(userAuthority.toString());
        }
        return userAuthorities;
    }

    private String getUserAuthority(String userId) {
        return this.userRepository.findById(userId)
                .get()
                .getAuthorities()
                .stream()
                .findFirst().get()
                .getAuthority();
    }

    @Override
    public boolean createUser(UserServiceModel userServiceModel) {
        User userEntity = this.modelMapper
                .map(userServiceModel, User.class);
        userEntity.setPassword(this.bCryptPasswordEncoder
                .encode(userEntity.getPassword()));
        if (this.userRepository.findAll().isEmpty()) {
            userEntity.setAuthorities(this.getAuthorities("ADMIN"));
        } else {
            userEntity.setAuthorities(this.getAuthorities("USER"));
        }
        System.out.println(userEntity.getAuthorities());
        try {
            this.userRepository.save(userEntity);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    public Set<UserServiceModel> getAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(x -> this.modelMapper
                        .map(x, UserServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public UserServiceModel getById(String id) {
        User userEntity = this.userRepository
                .findById(id)
                .orElse(null);
        return this.modelMapper
                .map(userEntity, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getByUserName(String userName) {
        User userEntity = this.userRepository
                .findByUsername(userName)
                .orElse(null);
        if (userEntity == null) return null;

        return this.modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public boolean promoteUser(String id) {
        User user = this.userRepository
                .findById(id).orElse(null);
        if (user == null) return false;
        String userAuthority = this.getUserAuthority(user.getId());

        switch (userAuthority) {
            case "USER":
                user.setAuthorities(this.getAuthorities("MODERATOR"));
                break;
            case "MODERATOR":
                user.setAuthorities(this.getAuthorities("ADMIN"));
                break;
            default:
                throw new IllegalArgumentException("There is no role, higher than ADMIN");
        }
        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean demoteUser(String id) {
        User user = this.userRepository.findById(id)
                .orElse(null);
        if (user == null)return false;
        String userAuthority = this.getUserAuthority(user.getId());

        switch (userAuthority){
            case "ADMIN":
                user.setAuthorities(this.getAuthorities("MODERATOR"));
                break;
            case "MODERATOR":
                user.setAuthorities(this.getAuthorities("USER"));
                break;
                default:throw new IllegalArgumentException("There is no role, lower than USER");
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = this.userRepository.findByUsername(username)
              .orElse(null);
      if(user == null) throw new UsernameNotFoundException("No such user");
        return user;
    }
}
