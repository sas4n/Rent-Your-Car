package com.lnu.RentYourCar.userInformation;

import com.lnu.RentYourCar.Auth.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public void addUser(User user) {
        repository.save(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> op = repository.findById(id);
        User user;
        if (op.isPresent())
            user = op.get();
        else
            throw new RuntimeException("this user does not exist");
        return user;
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    @Override
    public void registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPersonalNumber(userDto.getPersonalNumber());

        addUser(user);
    }

    private boolean emailExist(String email) {
        return repository.findByEmail(email) != null;
    }
}
