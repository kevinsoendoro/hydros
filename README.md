# Modelling Hydros (Hydroponics Autonomous)


### Summary
Hydros is a model to classify the healthy status of corps and also detecting the sickness type of the corps, if they have one. By using this model, farmers or hydroponics farmers can detect the sickness of the plant easily just by pointing their camera towards the plant.

This repository mainly consists of 2 files:
1. `tflearning_Hydros_(MobileNetV2).ipynb` that the flow of process from the making of the model, pre-processing, processing, compiling and fitting, evaluating, also saving the model and converting them to Tensorflow lite (.tflite) file
3. `testing_model.ipynb` that for manually testing the model that we already had

## 1. tflearning_Hydros_(MobileNetV2).ipynb
### A. How To Make The Model?
1. Data Processing and Data Preparation for Modelling
2. Modelling Process (Transfer Learning)
3. Evaluation
4. Save the model and tflite model

### B. Data Preparation for Modelling
As the data from the dataset is the raw data, we need to process the data first before it is able to be used well. To get the best model, we need to supply it with the best processed data, not a raw data. After that the data is already "cleansed" and "chopped", what's left is to prepare it for the modelling process.
1. Connect to Google Drive to access the dataset (optional)
2. Load Libraries
3. Defining the image size and the path
4. Preprocessing the Image data

### C. Modelling Process
1. Initializing the base model
2. Adding Extra layers to Pre-trained Model
3. Compile and fit the model

### D. Evaluation
1. Plot the accuracy of the model in the epoch. The plot is for the accuracy of both the train set and the test set. After plotting, check the graph. Does the accuracy continue to increase or keep decreasing and if it continues to increase, then the model is well-fit 

<p align='center'>
  <img src="https://github.com/kevinsoendoro/hydros/blob/ml-development/image/model_acc.png" width="450" />
</p>

2. Plot the loss of the model in the epoch. As the opposite of accuracy, the plot is for loss of both the train set and the test set should decrease in each epoch. After plotting, check the graph. Does the loss continue to decrease or keep increasing and if it continues to decrease, then the model is well-fit                         

<p align='center'>
  <img src="https://github.com/kevinsoendoro/hydros/blob/ml-development/image/model_loss.png" width="450" />
</p>

3. Table of relevant in accuracy and loss

   | Accuracy | Val_accuracy | Loss   | Val_loss | Model    |
   | -------- | ------------ | ------ | -------- | -------- |
   | 0.7881   | 0.8006       | 0.7045 | 0.6827   | Good fit |
   

## 2. testing_model.ipynb
### A. Import libraries

### B. Load the TFLite model of the best accuracy that we've already had before

### C. Load and Preprocess the test image

### D. Setting the input and output tensor

### E. Run the Analysis using the model

### F. Get the result class

---

![image](https://i.ibb.co/PtmxJvN/Hydros-Logo-318.png)
![image](https://i.ibb.co/CzVRCh5/msedge-GYR2-Tsp95t.png)

# Bangkit Capstone Project
This is a documentation about Hydros (Hydroponic Autonomous) Applicaton!

# Our Member
|            Member           | Student ID  |               University               |        Path         |
| :-------------------------: | :---------: | :------------------------------------: | :-----------------: |
|        Kevin Soendoro       | M038DSX1472 |  Institut Teknologi Sepuluh Nopember   |  Machine Learning   |
|      Ichsan Hibatullah      | M169DSX1878 |        Universitas Gadjah Mada         |  Machine Learning   |
|        Muhamad Akbar        | M346DSX2831 |         Universitas Sriwijaya          |  Machine Learning   |
|    M. Dava Rananditya P.    | C172DSX3144 |         Universitas Gunadarma          |   Cloud Computing   |
|    Ryan Nelson Rukyanto     | C169DKX3958 |        Universitas Gadjah Mada         |   Cloud Computing   |
|         Nitamayega          | A360DSY3377 |           Universitas Telkom           |  Mobile Development |
