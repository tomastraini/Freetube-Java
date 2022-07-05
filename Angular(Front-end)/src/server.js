//
const express = require('express');
const path = require('path');
const app = express();
var dirnam = __dirname.substring(0, __dirname.length - 4);
app.use(express.static(dirnam + '/dist/Angular'));
app.get('/*', function(req,res) {
res.sendFile(path.join(dirnam+
'/dist/Angular/index.html'));});
app.listen(process.env.PORT || 8080);