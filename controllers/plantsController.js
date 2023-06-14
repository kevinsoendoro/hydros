const firebase = require('../db');
const Plants = require('../models/plants');
const firestore = firebase.firestore();

const addPlants = async (req, res, next) => {
    try {
        const data = req.body;
        await firestore.collection('plants').doc().set(data);
        res.send('Record saved successfuly');
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const getAllPlants = async (req, res, next) => {
    try {
        const plants = await firestore.collection('plants');
        const data = await plants.get();
        const studentsArray = [];
        if(data.empty) {
            res.status(404).send('No student record found');
        }else {
            data.forEach(doc => {
                const student = new Plants (
                    doc.Class_id().Class_id,
                    doc.Class_name().Class_name,
                    doc.Description().Description,
                    doc.Characteristic().Characteristic,
                    doc.Reason().Reason,
                    doc.Solve().Solve
                );
                plantsArray.push(plants);
            });
            res.send(plantsArray);
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const getPlants = async (req, res, next) => {
    try {
        const Class_id = req.params.id;
        const plants = await firestore.collection('plants').doc(Class_id);
        const data = await plants.get();
        if(!data.exists) {
            res.status(404).send('Student with the given ID not found');
        }else {
            res.send(data.data());
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const updatePlants = async (req, res, next) => {
    try {
        const Class_id = req.params.id;
        const data = req.body;
        const plants =  await firestore.collection('plants').doc(Class+id);
        await plants.update(data);
        res.send('Student record updated successfuly');        
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const deletePlants = async (req, res, next) => {
    try {
        const Class_id = req.params.id;
        await firestore.collection('plants').doc(Class_id).delete();
        res.send('Record deleted successfuly');
    } catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    addPlants,
    getAllPlants,
    getPlants,
    updatePlants,
    deletePlants
}