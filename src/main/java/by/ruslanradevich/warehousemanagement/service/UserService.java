package by.ruslanradevich.warehousemanagement.service;

import by.ruslanradevich.warehousemanagement.Exception.UserNotFoundException;
import by.ruslanradevich.warehousemanagement.entity.User;
import by.ruslanradevich.warehousemanagement.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void updateUserPassword(String username, String newPassword) {
        Logger logger = Logger.getLogger(UserService.class.getName());

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String message = "User not found: " + username;
                    logger.warn(message);
                    return new UserNotFoundException(message);
                });

        user.setPassword(newPassword);
        userRepository.update(user);
        logger.info("Password updated successfully for user: " + username);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User getUserByName(String username) {
        Logger logger = Logger.getLogger(UserService.class.getName());

        return userRepository.findByUsername(username)
                .filter(user -> user.getUsername().equals(username))
                .map(user -> {
                    User byUser = new User();
                    byUser.setUsername(user.getUsername());
                    byUser.setId(user.getId());
                    byUser.setPassword(user.getPassword());
                    return byUser;
                })
                .orElseThrow(() -> {
                    String message = "User not found: " + username;
                    logger.warn(message);
                    return new UserNotFoundException(message);
                });
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
