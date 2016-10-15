package ordering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by adampermann on 10/15/16.
 */
@RestController
public class MenuController {


    @GetMapping("/getMenu")
    public MenuOption[] getMenu() {
        MenuOption[] mockMenu = this.getMockMenu();
        return mockMenu;
    }

    private MenuOption[] getMockMenu() {
        MenuOption[] result = new MenuOption[5];
        result[0] = new MenuOption("Cheese Pizza", 11, 0);
        result[1] = new MenuOption("Pepperoni Pizza", 13, 0);
        result[2] = new MenuOption("Sausage Pizza", 13, 0);
        result[3] = new MenuOption("Supreme Pizza", 14, 0);
        result[4] = new MenuOption("Super 8 Special", 15, 0);

        return result;
    }
}
