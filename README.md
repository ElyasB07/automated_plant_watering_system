This project implements an automated plant watering system using Java integrated with an Arduino-based hardware setup. The system monitors soil moisture in real time and automatically activates a water pump when the soil becomes dry.

⚙️ How It Works
	•	A soil moisture sensor continuously measures soil conditions.
	•	Sensor data is sent to the Java application via serial communication.
	•	When moisture drops below a set threshold:
	•	The system activates a MOSFET/Relay module.
	•	The water pump turns on.
	•	Once optimal moisture is reached, the pump turns off.
	•	A live graph displays soil moisture levels in real time.

Technologies Used
	•	Java
	•	Arduino
	•	Soil Moisture Sensor
	•	MOSFET / Relay Module
	•	Serial Communication

 Results

The system successfully:
	•	Detected dry soil conditions
	•	Activated and deactivated the pump automatically
	•	Displayed real-time moisture data

 Key Concepts
	•	Object-Oriented Programming
	•	Hardware–Software Integration
	•	Real-Time Monitoring
	•	Automation Logic
