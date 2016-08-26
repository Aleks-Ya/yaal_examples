package ru.yaal.spring.mvc.controller.form;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ru.yaal.spring.mvc.data.form.Student;

import org.springframework.ui.ModelMap;

@Controller
public class FormController {

   @RequestMapping(value = "/form/student", method = RequestMethod.GET)
   public ModelAndView student() {
      return new ModelAndView("form/student", "command", new Student());
   }
   
   @RequestMapping(value = "/form/addStudent", method = RequestMethod.POST)
   public String addStudent(@ModelAttribute("SpringWeb")Student student, ModelMap model) {
      model.addAttribute("name", student.getName());
      model.addAttribute("age", student.getAge());
      model.addAttribute("id", student.getId());
      
      return "form/result";
   }
}