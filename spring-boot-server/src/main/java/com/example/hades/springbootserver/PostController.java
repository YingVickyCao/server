package com.example.hades.springbootserver;

import com.example.hades.springbootserver.bean.LoginResult;
import com.example.hades.springbootserver.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    /**
     * Post Way 1：Receive form-data
     */
    /*
      http://localhost:7777/login

      If 没有传递参数，报错。
      解决方法：
      方法1：使用 required = false 标注参数是非必须的。
      方法2：使用 defaultValue 给参数指定个默认值。
     */
    @ResponseBody
    @PostMapping("/login")
//    public LoginResult login(@RequestParam("name") String name, @RequestParam("pwd") String pwd) {
    public LoginResult login(@RequestParam(value = "name", defaultValue = "mock_name") String name, @RequestParam(value = "pwd", required = false) String pwd) {
        return new LoginResult("ok", System.currentTimeMillis());
    }

    /**
     * Post Way 2：Receive map
     */
    // http://localhost:7777/login2
    @ResponseBody
    @PostMapping("/login2")
    public LoginResult login2(@RequestParam Map<String, Object> params) {
        for (Object item : params.values()) {
            System.err.println(item);
        }
        return new LoginResult("ok", System.currentTimeMillis());
    }

    /**
     * Post Way 3：Receive array
     */
    // http://localhost:7777/login3
    @ResponseBody
    @PostMapping("/login3")
    public LoginResult login3(@RequestParam("name") String[] names) {
        for (String item : names) {
            System.out.println("login3," + item);
        }
        return new LoginResult("ok", System.currentTimeMillis());
    }

    /**
     * Post Way 4：Receive Object / Json of Bean
     */
    /*
      http://localhost:7777/login4

      传递Json of Bean 也可以
      User：必须有set和get函数，否则ERROR:Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.example.hades.server.LoginResult]
      如果传递的参数有前缀，且前缀与接收实体类的名称相同，那么参数也是可以正常传递的
      如果传递的参数有前缀，且前缀与接收实体类的名称不同相，那么参数无法正常传递:使用@InitBinder 解决这个问题，通过参数预处理来指定使用的前缀为 u.
     */
//    @ResponseBody
//    @PostMapping("/login4")
//    public LoginResult login4(User user) {
//        System.out.println("login4," + user.toString());
//        return new LoginResult("ok", System.currentTimeMillis());
//    }
    @ResponseBody
    @PostMapping("/login4")
    public LoginResult login4(@ModelAttribute("u") User user) {
        System.out.println("login4," + user.toString());
        return new LoginResult("ok", System.currentTimeMillis());
    }

    @InitBinder("u")
    private void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("u.");
    }

    /**
     * Post Way 5 : Receive a Json of List
     */
    // http://localhost:7777/login5
    @ResponseBody
    @PostMapping("/login5")
    public LoginResult login5(@RequestBody List<User> users) {
        System.out.println("login5 ----->");
        for (User item : users) {
            System.out.println(item.toString());
        }
        System.out.println("login5 <-----");
        return new LoginResult("ok", System.currentTimeMillis());
    }
}
