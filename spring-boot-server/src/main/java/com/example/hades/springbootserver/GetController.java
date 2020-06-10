package com.example.hades.springbootserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Controller:表示相应请求
 */
@Controller
public class GetController {
    /**
     * Way 1:  无参数
     */
    /*
      Way 1:  无参数
      http://localhost:7777/hello
      "World"
     */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "World";
    }

    /**
     * Way 2 : Path has variable
     */
    /*
     Way 2 : Path has variable
     http://localhost:7777/hi/vicky
     Hi,vicky

     http://localhost:7777/hi/A
     Hi,A
     */
    @ResponseBody
    @RequestMapping("/hi/{user}")
    public String hi(@PathVariable(name = "user") String user) {
        return "Hi," + user;
    }

    /*
      http://localhost:7777/order/1000?name=vicky
      1000_num
     */
    @ResponseBody
    @RequestMapping("/order/{id}")
    public String getOrder(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {
        System.out.println(id + "," + name);
        return id + "_num";
    }

    /**
     * Way 3 : Param after ?
     */
    /*
      http://localhost:7777/sum?num1=15&num2=10
      25
     */
    /*
    @ResponseBody
    @RequestMapping("/sum")
    public int sum(@RequestParam(name = "num1") int num1, @RequestParam(name = "num2") int num2) {
        return num1 + num2;
    }*/

    /*
      http://localhost:7777/sum?num1=15
      20

       http://localhost:7777/sum?num2=10
       ERROR:java.lang.IllegalStateException: Optional int parameter 'num1' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
     */
    /*
    @ResponseBody
    @RequestMapping("/sum")
    // ERROR:java.lang.IllegalStateException: Optional int parameter 'num1' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
    public int sum(@RequestParam(name = "num1", required = false) int num1, @RequestParam(name = "num2", defaultValue = "5") int num2) {
        if (num1 == null) {
            num1 = 0;
        }
        return num1 + num2;
    }
    */

    /*
      http://localhost:7777/sum?num1=15
      20

      http://localhost:7777/sum?num2=10
      10
     */
    @ResponseBody
    @RequestMapping("/sum")
    public int sum(@RequestParam(name = "num1", required = false) Integer num1, @RequestParam(name = "num2", defaultValue = "5") int num2) {
        // Fix ERROR:NullPointerException
        if (num1 == null) {
            num1 = 0;
        }
        return num1 + num2;
    }

    /**
     * Way 4 : map
     */
    /*
      http://localhost:7777/userInfo?name=vicky&id=1
      2
     */
    @ResponseBody
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam Map<String, Object> params) {
        for (Object item : params.values()) {
            System.out.println(item);
        }
        return null == params ? "0" : params.size() + "";
    }

    /**
     * Way 5 :array
     */
    /*
     http://localhost:7777/users?name=vicky&name=strawberry
     2
     */
    @ResponseBody
    @GetMapping("/users")
    public String users(@RequestParam("name") String[] users) {
        for (String item : users) {
            System.out.println(item);
        }
        return null == users ? "0" : users.length + "";
    }

    /**
     * Way 6 : Bean
     */
    /*
      http://localhost:7777/printUser?name=vicky&pwd=abc
      vicky_abc
     */
    /*
    @ResponseBody
    @GetMapping("/printUser")
    public String printUser(User user) {
        System.out.println(user);
        return user.getName() + "_" + user.getPwd();
    }
    */

    /*
      传递的参数有前缀,使用 InitBinder 指定参数有指定
      http://localhost:7777/printUser?user.name=vicky&user.pwd=abc
      vicky_abc
     */
    @ResponseBody
    @GetMapping("/printUser")
    public String printUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        return user.getName() + "_" + user.getPwd();
    }

    @InitBinder("user")
    private void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user.");
    }

    /*
        使用多个对象来接收参数
        http://localhost:7777/listInfo?user.name=vicky&user.pwd=abc&number=100
        vicky_abc_100
     */
    @ResponseBody
    @GetMapping("/listInfo")
    public String listInfo(User user, Phone phone) {
        System.out.println(user);
        System.out.println(phone);
        return user.getName() + "_" + user.getPwd() + "_" + phone.getNumber();
    }
}