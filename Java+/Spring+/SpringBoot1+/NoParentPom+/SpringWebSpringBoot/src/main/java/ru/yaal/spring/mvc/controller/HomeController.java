package ru.yaal.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Redirect to home.jsp
 */
@Controller
@RequestMapping("/")
public class HomeController {
   @RequestMapping(method = RequestMethod.GET)
   public String printHello() {
      return "home";
   }
}