package com.empresa.SpringTweet.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.empresa.SpringTweet.model.User;
import com.empresa.SpringTweet.repository.UserRepository;

/**
 * Camada de serviço para operações sobre usuários.
 * Mantém a lógica de negócio separada dos controllers.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> update(Integer id, User updatedUser) {
        return userRepository.findById(id).map(existing -> {
            if (updatedUser.getPassword() != null) existing.setPassword(updatedUser.getPassword());
            if (updatedUser.getScreenName() != null) existing.setScreenName(updatedUser.getScreenName());
            if (updatedUser.getProfileImage() != null) existing.setProfileImage(updatedUser.getProfileImage());
            if (updatedUser.getFollowing() != null) existing.setFollowing(updatedUser.getFollowing());
            if (updatedUser.getBio() != null) existing.setBio(updatedUser.getBio());
            if (updatedUser.getRole() != null) existing.setRole(updatedUser.getRole());
            return userRepository.save(existing);
        });
    }

    public boolean delete(Integer id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
