package com.lnu.RentYourCar.Auth;

import com.lnu.RentYourCar.userInformation.IUserService;
import com.lnu.RentYourCar.userInformation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private IUserService userService;

    @Autowired
    public RegistrationController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "auth/registration";
    }

    @PostMapping("/auth/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        try {
            userService.registerNewUser(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            System.out.println("User already exists");
            bindingResult.addError(new FieldError("user", "email", "This email is already taken."));
            return "auth/registration";
        }

        return "redirect:/";
    }
}
