package com.example.hades.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    /*
      http://localhost:7777/login
      Post Way 1：Receive form-data

      If 没有传递参数，报错。
      解决方法：
      方法1：使用 required = false 标注参数是非必须的。
      方法2：使用 defaultValue 给参数指定个默认值。
     */
    @ResponseBody
    @PostMapping("/login")
//    Spring_Boost_Post_Postman_1.png
//    public LoginResult login(@RequestParam("name") String name, @RequestParam("pwd") String pwd) {

    // Spring_Boost_Post_Postman_2.png
    public LoginResult login(@RequestParam(value = "name", defaultValue = "mock_name") String name, @RequestParam(value = "pwd", required = false) String pwd) {
        return new LoginResult("ok", System.currentTimeMillis());
    }

    /**
     * http://localhost:7777/login2
     * <p>
     * Post Way 2：Receive map
     * Spring_Boost_Post_Postman_1.png
     */
    @ResponseBody
    @PostMapping("/login2")
    public LoginResult login2(@RequestParam Map<String, Object> params) {
        for (Object item : params.values()) {
            System.err.println(item);
        }
        return new LoginResult("ok", System.currentTimeMillis());
//        return name + pwd;
    }

    /**
     * http://localhost:7777/login3
     * <p>
     * Post Way 3：Receive array
     * Spring_Boost_Post_Postman_3.png
     */
    @ResponseBody
    @PostMapping("/login3")
    public LoginResult login3(@RequestParam("name") String[] names) {
        for (String item : names) {
            System.out.println("login3," + item);
        }
        return new LoginResult("ok", System.currentTimeMillis());
    }

    /*
      http://localhost:7777/login4
      Post Way 3：Receive Object / Json of Bean
      Spring_Boost_Post_Postman_4.png
      Spring_Boost_Post_Postman_6.png : 传递Json of Bean 也可以

      User：必须有set和get函数，否则ERROR:Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.example.hades.server.LoginResult]

      如果传递的参数有前缀，且前缀与接收实体类的名称相同，那么参数也是可以正常传递的
      Spring_Boost_Post_Postman_5.png

      如果传递的参数有前缀，且前缀与接收实体类的名称不同相，那么参数无法正常传递:使用@InitBinder 解决这个问题，通过参数预处理来指定使用的前缀为 u.
     */
//    @ResponseBody
//    @PostMapping("/login4")
//    public LoginResult login4(User user) {
//        System.out.println("login4," + user.toString());
//        return new LoginResult("ok", System.currentTimeMillis());
//    }

    public LoginResult login4(@ModelAttribute("u") User user) {
        System.out.println("login4," + user.toString());
        return new LoginResult("ok", System.currentTimeMillis());
    }

    @InitBinder("u")
    private void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("u.");
    }

    /*
        http://localhost:7777/login5
         Post Way 4 : Receive a Json of List
         Spring_Boost_Post_Postman_5.png
     */
    @ResponseBody
    @PostMapping("/login5")
    public LoginResult login5(@RequestBody List<User> users) {
        System.out.println("login5 ----->");
        for (User item : users) {
            System.out.println(item.toString());
        }
        System.out.println("login5 <-----");
        return new LoginResult("ok", System.currentTimeMillis());
//        return name + pwd;
    }
}
