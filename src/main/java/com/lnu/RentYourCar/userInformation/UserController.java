package com.lnu.RentYourCar.userInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user-Info")
    public String getUsers(Model model){
        model.addAttribute("userLists" , userService.getUsers());
        return "userInfo/allUsersData";

    }

    @GetMapping("/showSaveFrom")
    public String showEditForm(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "userInfo/save_Info";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute ("user") User user){
        userService.addUser(user);
        return "redirect:/user-Info";
    }

    @GetMapping("/ShowUpdateForm/{id}")
    public String showUpdateForm(@PathVariable (value = "id")long id,Model model){

        model.addAttribute("user" , userService.getUserById(id));
        return "userInfo/update_Info";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){

        userService.deleteUser(id);
        return "redirect:/user-Info";
    }

}
