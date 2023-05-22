package com.antlord.controller;

import com.antlord.service.DemoService;
import com.antlord.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(String param) {
        return demoService.test(param);
    }
}
