package com.loginmodulejwt.service.Impl;

import com.loginmodulejwt.model.User;
import com.loginmodulejwt.repository.UserRepository;
import com.loginmodulejwt.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existing = repo.findById(id).orElseThrow();
        existing.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(encoder.encode(user.getPassword()));
        }
        existing.setUsername(user.getUsername());
        existing.setRole(user.getRole());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public List<User> list() {
        return repo.findAll();
    }
}
