var http = require('http');
var url = require("url");
var querystring = require('querystring');

/**
 * node node_js_post_include_html_sumit.js
 * http://localhost:7777/login
 * 
 *  Request Method: POST
    FormData:
    name: ga
    pwd: 2
 */

var postHTML =
  '<html><head><meta charset="utf-8"><title>菜鸟教程 Node.js 实例</title></head>' +
  '<body>' +
  '<form method="post">' +
  'Name <input name="name"><br>' +
  'Password <input name="pwd"><br>' +
  '<input type="submit">' +
  '</form>' +
  '</body></html>';

http.createServer(function (req, res) {
  var body = "";
  req.on('data', function (chunk) {
    body += chunk;
  });
  req.on('end', function () {
    var pathname = url.parse(req.url).pathname;

    console.log("===============>");
    console.log("body 1:", body);
    console.log("body.name 1:" + body.name);
    body = decodeURI(body);
    console.log("body 2:", body);
    console.log("body.name 2:" + body.name);
    
    body = querystring.parse(body);  // 反序列化 body string : string => object
    console.log("body 3:", body);
    console.log("url:" + req.url);
    console.log("pathname:" + pathname);
    console.log("body.name 3 :" + body.name);
    console.log("body[\"name\"]:" + body["name"]);
    console.log("body.pwd:" + body.pwd);

    if (pathname == '/login') {
      if (body.name && body.pwd) {
        res.writeHead(200, { 'Content-Type': 'application/json; charset=utf8' });
        var json = JSON.stringify({
          msg: "sucess",
          name: body.name,
          status: 1
        });
        console.log("json:" + json);
        res.write(json);
      }
      else {
        res.writeHead(200, { 'Content-Type': 'text/html; charset=utf8' });
        res.write(postHTML);
      }
    }
    else {
      res.writeHead(200, { 'Content-Type': 'text/html; charset=utf8' });
      res.write(postHTML);
    }

    res.end();
    console.log("<===============");
  });
}).listen(7777);

// Ref
// https://blog.csdn.net/u011146511/article/details/79876976
// http://www.runoob.com/nodejs/node-js-get-post.html
// http://www.runoob.com/nodejs/nodejs-router.html