package com.mksoft.shop.config.secruity;

import com.mksoft.shop.model.TMCustomer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(TMCustomer customer) {
        return new JwtUser(
                customer.getCustomerUuid(),
                customer.getCustomerLogin(),
                customer.getCustomerPwd(),
                customer.getCustomerMobile(),
                null,
                null
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

