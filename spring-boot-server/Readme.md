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

| Annotation   | DESC                             |
| ------------ | -------------------------------- |
| RequestParam | 参数                             |
| PostMapping  | 表示 处理的是 Post 请求          |
| ResponseBody | 返回值。可以是 a Json，String 等 |

# 1 Get Method

## Way 1: 无参数

```java

/*
    http://localhost:7777/hello
    "World"
*/
@ResponseBody
@RequestMapping("/hello")
public String hello() {
    return "World";
}
```

## Way 2 : Path has variable

```java
/*
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
```

```java
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
```

## Way 3 : Param after ?

```java
/*
    http://localhost:7777/sum?num1=15&num2=10
    25
*/
@ResponseBody
@RequestMapping("/sum")
public int sum(@RequestParam(name = "num1") int num1, @RequestParam(name = "num2") int num2) {
    return num1 + num2;
}
```

- 没有传递参数时,报错  
  解决：  
  没有传递参数时，使用 required = false 标注参数是非必须的，或 使用默认值

```java
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
```

## Way 4 : map

```java
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
```

## Way 5 :array

```java
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
```

## Way 6 : Bean

```java
/*
    http://localhost:7777/printUser?name=vicky&pwd=abc
    vicky_abc
*/
@ResponseBody
@GetMapping("/printUser")
public String printUser(User user) {
    System.out.println(user);
    return user.getName() + "_" + user.getPwd();
}
```

- 传递的参数有前缀,使用 InitBinder 指定参数有指定

```java
/*
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
```

- 使用多个对象来接收参数

```java
/*
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
```

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
- https://www.hangge.com/blog/cache/detail_2484.html
