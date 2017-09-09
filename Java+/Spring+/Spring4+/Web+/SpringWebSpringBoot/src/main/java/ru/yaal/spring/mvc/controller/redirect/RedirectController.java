package ru.yaal.spring.mvc.controller.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Redirect to another controller instead of JSP.
 */
@Controller
@RequestMapping("/redirect")
public class RedirectController {
   @RequestMapping(method = RequestMethod.GET)
   public String redirectFrom() {
      return "redirect:/redirect/redirectTo";
   }
   
   @RequestMapping(value="redirectTo", method = RequestMethod.GET)
   public String redirectTo() {
      return "redirect/redirect_to";
   }
}