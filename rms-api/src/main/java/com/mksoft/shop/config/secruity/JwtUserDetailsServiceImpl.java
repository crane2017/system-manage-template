package com.mksoft.shop.config.secruity;

import com.mksoft.shop.model.TMCustomer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        TMCustomer user = new TMCustomer();
        user.setCustomerLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", login));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
