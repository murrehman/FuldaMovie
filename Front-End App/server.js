const express = require('express');
const app = express();

app.use(express.static("./dist/movieApp"));

app.use('*', function (req,res) {
  res.sendFile(__dirname + '/dist/movieApp/index.html');
});

app.listen(4200, () => {
  console.log(`Frontend listening at http://localhost:4200`);
})


