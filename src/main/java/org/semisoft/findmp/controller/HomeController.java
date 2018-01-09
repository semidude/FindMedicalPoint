package org.semisoft.findmp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
@Controller
@RequestMapping("/")
public class HomeController
{
    @RequestMapping("/admin")
    ModelAndView adminPage()
    {
        ModelAndView model = new ModelAndView("adminPage");
        model.addObject("datetime", new Date());
        model.addObject("username", "Semisoft");
        model.addObject("mode", "development");
        return model;
    }

    @RequestMapping("/")
    ModelAndView userPage()
    {
        ModelAndView model = new ModelAndView("userPage");
        model.addObject("datetime", new Date());
        model.addObject("username", "Semisoft");
        model.addObject("mode", "development");
        return model;
    }
}
