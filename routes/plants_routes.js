const express = require('express');
const {addPlants, 
       getAllPlants, 
       getPlants,
       updatePlants,
       deletePlants
      } = require('../controllers/plantsController');

const router = express.Router();

router.post('/plants', addPlants);
router.get('/plants', getAllPlants);
router.get('/plants/:Class_id', getPlants);
router.put('/plants/:Class_id', updatePlants);
router.delete('/plants/:Class_id', deletePlants);


module.exports = {
    routes: router
}