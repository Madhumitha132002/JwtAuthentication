
package com.SpringBootProject.StudentDetails.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.SpringBootProject.StudentDetails.Model.CustomerModel;

public class MyCustomerDetails implements UserDetails {

    @Autowired
    private CustomerModel customerModel;

    public MyCustomerDetails(CustomerModel customer) {
        super();
        this.customerModel = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(customerModel.getRole());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return customerModel.getPassword();
    }

    @Override
    public String getUsername() {
        return customerModel.getCustomerName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
