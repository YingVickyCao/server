/*
Ref:
    http://www.runoob.com/nodejs/nodejs-http-server.html
    http://www.jb51.net/article/48465.htm
*/

// [Get]http://localhost:8888/getSum?num1=5&num2=15
var http = require('http');
var url = require('url');
var util = require('util');

http.createServer(function(req, res){
    res.writeHead(200, {'Content-Type': 'text/plain'});

    // 解析 url 参数
    var params = url.parse(req.url, true).query;
    var result;

    if (params.num1 &&  params.num2){
        // 因为Content-Type = 'text/plain', 所以，params.num1 不是一个整形，是一个string。在进行加法计算之前，先转化我int。
        result = responseGetSum(parseInt(params.num1), parseInt(params.num2));
        console.log('result='+result);
    }
    res.write(result + "");
    res.end();

}).listen(7777);

// [Get]http://localhost:7777/getSum?num1=5&num2=15
// http://127.0.0.1:7777/getSum?num1=5&num2=15
var responseGetSum = function(num1, num2){
    var sum  = num1 + num2;
    return sum;
}





