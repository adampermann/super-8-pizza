package com.superpizza.users;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.superpizza.ResponseMessage;
import java.util.*;


/**
 * Created by adampermann on 11/19/16.
 */
@RestController
public class UserController implements UserRepository.UserSubscriber {

    private UserRepository repo = null;
    private Map<String, User> users;
    private final static String joinKey = "Super8Pizza";

    public UserController() {
        this.repo = new UserRepository();

        this.getUsersFromRepo();
    }

    public UserController(UserRepository repo) {
        this.repo = repo;

        this.getUsersFromRepo();
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        // TODO: 11/20/16 Validate email and username don't already exist in the database (ie make it more optimal than just a loop)

        // need 2 checks so the front end can show the error message returned.
        if (doesEmailExist(newUser)) {
            return new ResponseEntity<>(new ResponseMessage("Email already taken"), HttpStatus.BAD_REQUEST);
        }

        if (doesUserNameExist(newUser)) {
            return new ResponseEntity<>(new ResponseMessage("User name already taken"), HttpStatus.BAD_REQUEST);
        }

        if (newUser.email.length() == 0 || newUser.username.length() == 0 || newUser.password.length() == 0) {
            return new ResponseEntity<>(new ResponseMessage("Please enter all required fields"), HttpStatus.BAD_REQUEST);
        }
        // generate the random uuid
        newUser.userId = UUID.randomUUID().toString();

        // TODO: 11/20/16 check the join key and see if it matches.  If it does give them admin role.
        if (newUser.joinKey != null && newUser.joinKey.equals(this.joinKey)) {
            newUser.role = new UserRole(User.Role.Admin.getValue(), User.Role.Admin.toString());
        } else {
            newUser.role = new UserRole(User.Role.Customer.getValue(), User.Role.Customer.toString());
        }

        try {
            users.put(newUser.userId, newUser);
            repo.saveUsers(users);

            return new ResponseEntity<>(newUser, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error registering.  Please try again."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        // check username and password
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User cur = entry.getValue();
            if (cur.username.equals(user.username) && cur.password.equals(user.password)) {
                // found the user and the login request is valid
                // return the user stored in our db
                return new ResponseEntity<>(cur, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new ResponseMessage("Login Failed.  Please try again."), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/updateAccount")
    public ResponseEntity<?> updateAccount(@RequestBody User user) {
        // check all the other users besides this one for email or username overlap

        for (Map.Entry<String, User> entry : users.entrySet()) {
            User current = entry.getValue();

            // changed user name to someone else's
            if (current.username.equals(user.username) && !current.userId.equals(user.userId)) {
                return new ResponseEntity<>(new ResponseMessage("Update Invalid.  Username already taken"), HttpStatus.BAD_REQUEST);
            }

            // changed email to already taken
            if (current.email.equals(user.email) && !current.userId.equals(user.userId)) {
                return new ResponseEntity<>(new ResponseMessage("Update Invalid.  Email already taken"), HttpStatus.BAD_REQUEST);
            }
        }

        try {

            users.put(user.userId, user);
            repo.saveUsers(users);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating account.  Please try again."), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void dataChanged(Map<String, User> newData) {
        for (Map.Entry<String, User> entry : newData.entrySet()) {
            users.put(entry.getKey(), entry.getValue());
        }
    }

    private void getUsersFromRepo() {
        try {
            users = repo.getUsers(this);
        } catch (InterruptedException e) {
            // TODO: 11/20/16
            e.printStackTrace();
        }
    }

    private boolean doesUserNameExist(User newUser) {

        // loop through the users and see if the name matches.

        for (Map.Entry<String, User> entry : users.entrySet()) {
            User current = entry.getValue();

            if (current.username.equals(newUser.username)) {
                return true;
            }
        }

        return false;
    }

    private boolean doesEmailExist(User newUser) {

        // loop through the users and see if the email matches.

        for (Map.Entry<String, User> entry : users.entrySet()) {
            User current = entry.getValue();

            if (current.email.equals(newUser.email)) {
                return true;
            }
        }

        return false;
    }
}
