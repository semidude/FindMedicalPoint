package org.semisoft.findmp.controller;

import org.semisoft.findmp.domain.MedicalPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController
{
    @RequestMapping("/")
    public String home(Model model)
    {
        return "index";
    }

    @RequestMapping("/showloc")
    public @ResponseBody
    String showLocation(@RequestParam double latitude, @RequestParam double longitude)
    {
        return latitude + " " + longitude;
    }

}
