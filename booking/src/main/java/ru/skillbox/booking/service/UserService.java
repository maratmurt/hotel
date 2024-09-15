package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.statistics.event.RegistrationEvent;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.User;
import ru.skillbox.booking.repository.UserRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;

    private final NullAwareMapper nullAwareMapper;

    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, RegistrationEvent> kafkaTemplate;

    private static final String[] MESSAGES = {
            "Запрошенный пользователь не найден!",
            "Пользователь с такими данными уже существует!"
    };

    @Override
    public List<User> findAll(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            throw new ValidationException(MESSAGES[1]);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        RegistrationEvent event = new RegistrationEvent(user.getId());
        kafkaTemplate.send("registration-topic", UUID.randomUUID().toString(), event);

        return user;
    }

    @Override
    public User update(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
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
                new EntityNotFoundException(MESSAGES[0]));
    }
}
