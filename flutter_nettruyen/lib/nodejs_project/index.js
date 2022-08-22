var express = require('express');
const ms = require('ms');
var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

io.on('connection', function (socket) {
    console.log(socket.id, 'joined');

    socket.emit('/welcome', 'Welcome to this ' + socket.id);


    socket.on('/test', function (msg) {
        console.log(msg);
    });


    socket.on('/message', (msg) => {
        console.log('socket' + socket.id + "=>" + msg);
        socket.broadcast.emit('/message',msg);
    });
});



var port = 8080;
console.log(port);
server.listen(port);