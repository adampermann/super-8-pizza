package hello;

import menu.MenuController;
import menu.MenuItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;


/**
 * Created by adampermann on 10/14/16.
 */
@SpringBootApplication
@RestController
public class Application {


    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        MenuController mc = new MenuController();
        Map<String, MenuItem> menu1 = mc.getMenu();
        mc.addItem("new item", 20.02);
        Map<String, MenuItem> menu2 = mc.getMenu();
        boolean ok = 1 + 2 == 3;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting").allowedOrigins("http://localhost:9000");
                registry.addMapping("/getMenu").allowedOrigins("http://localhost:9000");
            }
        };
    }

}
