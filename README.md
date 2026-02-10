# automated_plant_watering_system
Java-based automated plant watering system using Arduino and soil moisture sensing

## Dependencies
This project was developed using Java and requires external libraries to support Arduino communication, OLED display output, and data visualization.

- **Firmata4j**  
  Enables communication between the Java application and the Arduino using the Firmata protocol.

- **SSD1306 OLED Library**  
  Provides display control for the 128×64 OLED screen via I2C.

- **StdDraw (Princeton CS Library)**  
  Used to generate a real-time graph of soil moisture readings.

These dependencies are not included in the repository and must be manually added to the Java build path when running the project locally.  
Hardware components (Arduino, moisture sensor, pump, OLED display) are required for full system functionality.
