package com.superpizza.users;

import com.superpizza.ordering.Address;
import com.superpizza.ordering.Order;

import java.util.List;

/**
 * Created by adampermann on 11/20/16.
 */
public class User {
    public String userId;
    public String username;
    public String email;
    public String password;
    public String joinKey;
    public UserRole role;
    public Address address;

    public User(){}

    public User(String userId, String username, String email, String password, UserRole role, Address address, String joinKey) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.joinKey = joinKey;
    }

    public enum Role {
        Customer(1), Admin(2);

        private final int id;
        Role(int id) { this.id = id; }
        public int getValue() { return id; }
    }
}
