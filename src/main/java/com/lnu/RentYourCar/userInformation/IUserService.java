package com.lnu.RentYourCar.userInformation;

import com.lnu.RentYourCar.Auth.UserDto;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    void addUser(User user);
    User getUserById(long id);
    void deleteUser(long id);
    void registerNewUser(UserDto userDto) throws UserAlreadyExistException;
}
