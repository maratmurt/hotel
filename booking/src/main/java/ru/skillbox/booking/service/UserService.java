package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.User;
import ru.skillbox.booking.repository.UserRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;

    private final NullAwareMapper nullAwareMapper;

    @Override
    public List<User> findAll(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Запрошенный пользователь не найден!"));
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByNameAndEmail(user.getName(), user.getEmail())) {
            throw new ValidationException("Пользователь с такими данными уже существует!");
        }

        return userRepository.save(user);
    }

    @Override
    public User update(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElseThrow(()->
                new EntityNotFoundException("Запрошенный пользователь не найден!"));
        try {
            nullAwareMapper.copyProperties(existingUser, updatedUser);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow(()->
                new EntityNotFoundException("Запрошенный пользователь не найден!"));
    }
}
