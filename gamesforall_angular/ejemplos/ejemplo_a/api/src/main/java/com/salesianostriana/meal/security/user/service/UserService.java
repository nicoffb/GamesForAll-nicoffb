package com.salesianostriana.meal.security.user.service;

import com.salesianostriana.meal.error.exception.HasRestaurantException;
import com.salesianostriana.meal.error.exception.InvalidPasswordException;
import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.Roles;
import com.salesianostriana.meal.security.user.dto.ChangePasswordRequest;
import com.salesianostriana.meal.security.user.dto.CreateUserRequest;
import com.salesianostriana.meal.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean checkOwnership(Restaurante restaurante, UUID userId){
        return userRepository.findFirstById(userId).map(u -> {
            boolean isAdministrator = u.getAdministra().stream().anyMatch(r -> r.equals(restaurante));
            if (!isAdministrator) throw new NotOwnerException();
            return isAdministrator;
        }).orElseThrow(() -> new NotOwnerException());
    }

    public User createUser(CreateUserRequest createUserRequest, Set<Roles> roles) {
        User user =  User.builder()
                .nombre(createUserRequest.getUsername())
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .nombre(createUserRequest.getNombre())
                .email(createUserRequest.getEmail())
                .roles(roles)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Set.of(Roles.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Set.of(Roles.ADMIN));
    }

    public User createUserWithOwnerRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Set.of(Roles.OWNER));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> edit(User user) {
        return userRepository.findById(user.getId())
                .map(u -> {
                    u.setEmail(user.getEmail());
                    return userRepository.save(u);
                }).or(() -> Optional.empty());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public User editPassword(User user, ChangePasswordRequest changePasswordRequest) {
        if(!passwordMatch(user, changePasswordRequest.getOldPassword()))
            throw new InvalidPasswordException();
        return userRepository.findById(user.getId())
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                    return userRepository.save(u);
                }).orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public void delete(User user) {
        if(user.getRoles().contains(Roles.OWNER) || user.getRoles().contains(Roles.ADMIN)){
            List<Restaurante> administra = userRepository.findFirstById(user.getId()).map(u -> u.getAdministra()).orElseThrow(() -> new EntityNotFoundException());
            if (!administra.isEmpty()) throw new HasRestaurantException();
        }
        userRepository.deleteRatings(user.getId());
        deleteById(user.getId());
    }

    public void deleteById(UUID id) {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public Optional<User> findAdminOf(UUID id){
        return userRepository.findFirstById(id);
    }

}
