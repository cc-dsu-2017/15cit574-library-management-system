package in.edu.dsu.cit15.lms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by abhishekmunie on 17/01/16.
 */
@Controller
@RequestMapping("/")
public class MainController {

  @RequestMapping("/dashboard")
  public String dashboard() {
    return "home";
  }

}
