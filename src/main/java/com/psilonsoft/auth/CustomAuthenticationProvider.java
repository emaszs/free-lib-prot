package com.psilonsoft.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.psilonsoft.dao.UserDao;
import com.psilonsoft.entities.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordManager passwordManager;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pass = (String) authentication.getCredentials();

        User user = userDao.getUserByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Invalid username/password");
        }

        if (!passwordManager.checkPassword(user.getPasswordHash(), user.getPasswordSalt(), pass)) {
            throw new BadCredentialsException("Invalid username/password");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("user"));

        return new CustomAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
