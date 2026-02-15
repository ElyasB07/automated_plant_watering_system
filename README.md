# 🌱 Autonomous Plant Watering System (Java + Arduino)

## Introduction
This project implements an automated plant watering system using **Java** integrated with an **Arduino-based hardware setup**. The system monitors soil moisture in real time and automatically waters the plant when the soil becomes dry.

---

## Design Analysis
The system operates autonomously using a soil moisture sensor and a water pump. The Java application processes incoming sensor data and controls the pump based on predefined moisture thresholds.

### Software Components
- Java control program  
- Serial communication interface  
- Real-time moisture monitoring  

### Hardware Components
- Arduino board  
- Soil moisture sensor  
- MOSFET or Relay module  
- Water pump  

---

## Procedure
1. The soil moisture sensor continuously measures soil conditions.  
2. Sensor data is transmitted to the Java application.  
3. If the soil is dry, the system activates the pump.  
4. Once optimal moisture is reached, the pump is turned off.  
5. The monitoring cycle repeats automatically.

---

## Results
The system successfully:
- Detected dry soil conditions  
- Activated and deactivated the pump appropriately  
- Displayed real-time soil moisture data  
