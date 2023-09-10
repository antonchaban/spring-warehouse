package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.models.enums.Role;
import com.warehouse.springwarehouseweb.repositories.UserRepository;
import com.warehouse.springwarehouseweb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findUserByLogin(principal.getName());
    }

    @Override
    public void createUser(User user) throws NameAlreadyBoundException {
        if (userRepository.findUserByLogin(user.getLogin()) != null) {
            throw new NameAlreadyBoundException("This login is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.CUSTOMER);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    public void editUser(User updUser, Long id) {
        User user = userRepository.findById(id).get();
        if (!(updUser.getName() == null)) user.setName(updUser.getName());
        if (!(updUser.getLogin() == null)) user.setLogin(updUser.getLogin());
        try {
            String role = updUser.getRoles().toArray()[0].toString();
            user.getRoles().clear();
            user.getRoles().add(Role.valueOf(role));
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Role is null, leave same value");
        }
        userRepository.save(user);
    }
}
