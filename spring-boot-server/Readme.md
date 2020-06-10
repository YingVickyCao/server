# Spring Boot Sever

- Environment:  
  Postman  
  IDEA

- Language:  
  Spring Boot(Java)

- How to run server?

Click Run

```
# application.properties

# http://localhost:7777
# Default port is 8080
server.port: 7777
```

# 1 Get Method

# 2 Post Method

`PostController.java`

## Way 1：Receive form-data

```java
// http://localhost:7777/login
@ResponseBody
@PostMapping("/login"
public LoginResult login(@RequestParam("name") String name, @RequestParam("pwd") String pwd) {
    return new LoginResult("ok", System.currentTimeMillis());
}
```

![Spring_Boost_Post_Postman_1](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_1.png)

- If 没有传递参数，报错。  
  解决方法：  
  方法 1：使用 required = false 标注参数是非必须的。  
  方法 2：使用 defaultValue 给参数指定个默认值。

```java
@ResponseBody
@PostMapping("/login"
public LoginResult login(@RequestParam(value = "name", defaultValue = "mock_name") String name, @RequestParam(value = "pwd", required = false) String pwd) {
    return new LoginResult("ok", System.currentTimeMillis());
}
```

![Spring_Boost_Post_Postman_2](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_2.png)

## Way 2：Receive Receive map

```java
/*
    http://localhost:7777/login2
*/
@ResponseBody
@PostMapping("/login2")
public LoginResult login2(@RequestParam Map<String, Object> params) {
    for (Object item : params.values()) {
        System.err.println(item);
    }
    return new LoginResult("ok", System.currentTimeMillis());
}
```

![Spring_Boost_Post_Postman_9](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_9.png)

## Way 3：Receive Array

```java
/*
    http://localhost:7777/login3
*/
@ResponseBody
@PostMapping("/login3")
public LoginResult login3(@RequestParam("name") String[] names) {
    for (String item : names) {
        System.out.println("login3," + item);
    }
    return new LoginResult("ok", System.currentTimeMillis());
}
```

![Spring_Boost_Post_Postman_3](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_3.png)

## Way 4：Receive Object / Json of Bean

```java
@ResponseBody
@PostMapping("/login4")
public LoginResult login4(User user) {
    System.out.println("login4," + user.toString());
    return new LoginResult("ok", System.currentTimeMillis());
}
```

![Spring_Boost_Post_Postman_4](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_4.png)

OR

![Spring_Boost_Post_Postman_6](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_6.png)

- User：必须有 set 和 get 函数，否则 ERROR:Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.example.hades.server.LoginResult]

- 如果传递的参数有前缀，且前缀与接收实体类的名称相同，那么参数也是可以正常传递的  
  ![Spring_Boost_Post_Postman_5](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_5.png)

- 如果传递的参数有前缀，且前缀与接收实体类的名称不同相，那么参数无法正常传递:使用@InitBinder 解决这个问题，通过参数预处理来指定使用的前缀为 u.

```java
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
```

![Spring_Boost_Post_Postman_7](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_7.png)

## Way 5：Receive a Json of List

```java
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
```

![Spring_Boost_Post_Postman_8](https://yingvickycao.github.io/img/Spring_Boost_Post_Postman_8.png)

# Refs

- https://www.hangge.com/blog/cache/detail_2485.html
