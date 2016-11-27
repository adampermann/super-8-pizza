package com.superpizza;

import com.superpizza.DAL.DataSetup_NOT_FOR_PRODUCTION;
import com.superpizza.menu.MenuController;
import com.superpizza.menu.MenuItem;
import com.superpizza.menu.MenuRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by adampermann on 10/14/16.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}
