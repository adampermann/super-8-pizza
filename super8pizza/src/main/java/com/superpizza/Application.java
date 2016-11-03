package com.superpizza;

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

    public static void main(String[] args)
    {
//        Map<String, MenuItem> menuMap = new HashMap<>();
//        UUID id = UUID.randomUUID();
//        menuMap.put(id.toString(), new MenuItem(id.toString(), "name1", 11.01));
//        id = UUID.randomUUID();
//        menuMap.put(id.toString(), new MenuItem(id.toString(), "name2", 12.01));
//        id = UUID.randomUUID();
//        menuMap.put(id.toString(), new MenuItem(id.toString(), "name3", 13.01));
//        id = UUID.randomUUID();
//        menuMap.put(id.toString(), new MenuItem(id.toString(), "name4", 14.01));
//        id = UUID.randomUUID();
//        menuMap.put(id.toString(), new MenuItem(id.toString(), "name5", 15.01));
//
//
//        MenuRepository repo = new MenuRepository();
//        repo.saveMenu(menuMap);


        SpringApplication.run(Application.class, args);
    }
}
