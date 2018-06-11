package com.chan.study.web;

import com.chan.study.bean.ValidDemoBean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo/annotation/controller")
public class ControllerDemoController {
//    @Resource
//    UserService userService;

    @RequestMapping("/register-into-web-application")
    @ResponseBody
    public String registerIntoWebApplication(@Validated ValidDemoBean validDemoBean, String name, String id) {
        System.out.println(validDemoBean);
        return "Yes ! RestController is register into WebApplication";
    }

    @RequestMapping("/message")
    @ResponseBody
    public void aa(@RequestBody String str) {
        System.out.println(str);

    }

    public  void main(String[] args) {
        Long s1 = 23L;
        String s2 = "24";
        Long a = s1 != null ? s1 : Long.parseLong(s2);
        long i = a;
    }
}
