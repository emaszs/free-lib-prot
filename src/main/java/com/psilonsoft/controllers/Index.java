package com.psilonsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/")
public class Index {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(final ModelMap model) {

        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return "index";

    }

    @RequestMapping(value = "/welcome/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable final String name, final ModelMap model) {

        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
        return "index";

    }

}