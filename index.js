var http = require('http');
var fs = require('fs');
var path = require('path');
var bodyParser=require('body-parser');
var sql = require('mysql');
var express = require('express');
var session = require('express-session');
var con = sql.createConnection({
    host : "localhost",
    user : "root",
    password:"omprakash_7739",
    database: "shoprecord"
});
con.connect(function(err){
    if(err) console.log(err);
    console.log("database connected");
});
var app =express();
app.use(session({secret: 'awcptry'}));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());

app.get('/show',function(req,res){
    var statement="SELECT *FROM mobileinfo";
  con.query(statement,function(err,result){
        if(err) console.log(err);
        else {
            res.json(result);
  }
    });
});
app.get('/',function(req,res){
            fs.readFile("./webpages/merchantLogin.html","UTF-8",function(err,data){
                if(err) {
                    console.log(err);
                }
                res.writeHead(200,{"content-type":"text/html"});
                res.end(data);
            });
});
app.get('/merchantIntexPage',function(req,res){
    fs.readFile("./webpages/merchantIntexPage.html","UTF-8",function(err,data){
        if(err) {
            console.log(err);
        }
        res.writeHead(200,{"content-type":"text/html"});
        res.end(data);
    });
});


var server = app.listen(3000,function(){
    var host =server.address().address;
    var port = server.address().port;
    console.log("started to listen at http//%s:%s",host,port);
});