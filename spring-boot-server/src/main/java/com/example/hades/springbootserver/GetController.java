package com.example.hades.springbootserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Controller:表示相应请求
 */
@Controller
public class GetController {
    /**
     * Controller 接受参数的方式1：无参数
     *
     * @param user
     * @return "Hello"
     */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String user) {
        return "Hello";
    }

    /**
     * Controller 接受参数的方式2：@PathVariable
     * http://localhost:7777/num/1000?name=vicky
     *
     * @param id
     * @param name
     * @return 1000_num
     */
    @ResponseBody
    @RequestMapping("/num/{id}")
    public String getNum(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {
        System.out.println(id + "," + name);
        return id + "_num";
    }

    /**
     * Controller 接受参数的方式3：@RequestParam
     * http://localhost:7777/hello2?name=15&pwd=abc
     */
    @ResponseBody
    @RequestMapping("/hello2")
    public String hello2(@RequestParam(name = "name") String name, @RequestParam(name = "pwd") String pwd) {
        System.out.println(name + "," + pwd);
        return name + "_" + pwd;
    }
}
