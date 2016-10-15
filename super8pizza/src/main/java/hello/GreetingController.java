package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by adampermann on 10/14/16.
 */
@RestController
public class GreetingController {

    private static final String template = "Welcome to , %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required=false, defaultValue="Super 8 Pizza!") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
