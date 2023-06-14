'use strict'
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const config = require('./config');
const plantstRoutes = require('./routes/plants_routes');

const app = express();

app.use(express.json());
app.use(cors());
app.use(bodyParser.json());

app.use('/api', plantsRoutes.routes);

app.listen(config.port, ()=> console.log('app start on url https://localhost:' + config.port))

console.log(process.env.API_KEY);