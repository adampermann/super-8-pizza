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
//@EnableWebSecurity
public class Application {

//    @Configuration
//    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            // @formatter:off
//            http
//                    .httpBasic()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/index.html", "/home.html").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                    .csrf()
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//            // @formatter:on
//        }
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManagerConfigurer userConfig = auth.inMemoryAuthentication();
//        userConfig.withUser("user").password("password").roles("USER");
//        userConfig.withUser("admin").password("password").roles("ADMIN");
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //DataSetup_NOT_FOR_PRODUCTION.setupUsers();
    }
}
