package org.semisoft.findmp.controller;

import org.semisoft.findmp.domain.MedicalPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping(method = RequestMethod.GET)
    ModelAndView home()    
    {
        ModelAndView model = new ModelAndView("index");
        model.addObject("datetime", new Date());
        model.addObject("username", "Semisoft");
        model.addObject("mode", "development");
        return model;
    }

    @RequestMapping("/showloc")
    public @ResponseBody
    String showLocation(@RequestParam double latitude, @RequestParam double longitude)
    {
        return latitude + " " + longitude;
    }

}
