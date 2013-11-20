package com.psilonsoft.controllers;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psilonsoft.auth.PasswordManager;
import com.psilonsoft.dao.UserDao;
import com.psilonsoft.entities.User;

@Controller
public class LoginController {

    @Autowired
    public UserDao userDao;

    @Autowired
    public PasswordManager passwordManager;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/signup")
    public String signup(@ModelAttribute("account") final User user) {
        return "signup";
    }
    
    @RequestMapping(value = "/signup", method = { RequestMethod.POST })
    public String addUser(@ModelAttribute("account") final User account, final BindingResult result) {

        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            result.rejectValue("email", "email-empty", "Email is empty");
        }

        if (account.getPasswordHash() == null || account.getPasswordHash().isEmpty()) {
            result.rejectValue("passwordHash", "password-empty", "Password is empty");
        }
        
        if (result.hasErrors()) {
            return "signup";
        } else {
            String salt = passwordManager.generateRandomStr();
            String hash;
            try {
                hash = passwordManager.generateUserPasswordHash(account.getPasswordHash(), salt);
            } catch (NoSuchAlgorithmException e) {
                hash = null;
                e.printStackTrace();
            }

            account.setPasswordHash(hash);
            account.setPasswordSalt(salt);

            userDao.save(account);
        }

        return "redirect:/";
    }

}
