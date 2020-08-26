package com.example.hades.springbootserver._302;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class TestRedirect {

    // http://localhost:7777/before_redirect/40
    @RequestMapping("/before_redirect/{num}")
    public String redirectView(@PathVariable("num") int num, HttpSession session) {
        //这种跳转都是302跳转，通过一个redirect前缀告诉请求，要跳转到首页
        //所有的redirect请求都会跳转到目标
        //通过session来传递信息
        session.setAttribute("msg", "Jump from redirect");
//        return "redirect:/";  // go 首页
//        return "redirect:https://www.baidu.com/";
        return "redirect:/result";
    }

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession session) { // 首页
        // 显示重定向中的session
        return session.getAttribute("msg").toString();
    }

    @RequestMapping("/result")
    @ResponseBody
    public String result(HttpSession session) {
        // 显示重定向中的session
        return session.getAttribute("msg").toString();
    }
}