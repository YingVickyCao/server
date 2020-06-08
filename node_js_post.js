var http = require('http');
var url = require("url");
var querystring = require('querystring');
/**
 * node node_js_post.js
 * http://localhost:8888/login
 */
http.createServer(function (req, res) {
    var body = "";
    //请求链接
    console.log(res.body);

    //每当接收到请求体数据，累加到post中
    req.on('data', function (chunk) {
        body += chunk;  //一定要使用+=，如果body=chunk，因为请求favicon.ico，body会等于{}
    });

    //在end事件触发后，通过querystring.parse将post解析为真正的POST请求格式，然后向客户端返回。
    req.on('end', function () {

        console.log("----------------------->");
        console.log("method:",req.method)
        console.log("body 1:", body);
        body = decodeURI(body);
        console.log("body 2:", body);

        console.log("body.name:" + body.name);

        body = querystring.parse(body);  // 反序列化 body string : string => format
        var pathname = url.parse(req.url).pathname;

        console.log("body 3:", body);
        console.log("url:" + req.url);
        console.log("pathname:" + pathname);
        console.log("body.name:" + body.name);
        console.log("body[name]" + body["name"]);
        console.log("body.pwd:" + body.pwd);

        /**
        // http://localhost:8888/login
       Request Method: POST
       FormData:
       name: ga
       pwd: 2
       */
        if (pathname == '/login' && req.method === 'POST' && body.name && body.pwd) {
            var json = JSON.stringify({
                msg: "sucess",
                name: body.name,
                status: 1
            });

            res.write(json);
        } else {
            var json = JSON.stringify({
                msg: "fail",
                name: body.name,
                status: -1
            });
            res.write(json);
        }
        res.end();
        console.log("<-----------------------");
    });
}).listen(8888);

// Ref
// https://blog.csdn.net/u011146511/article/details/79876976
// http://www.runoob.com/nodejs/node-js-get-post.html
// http://www.runoob.com/nodejs/nodejs-router.html