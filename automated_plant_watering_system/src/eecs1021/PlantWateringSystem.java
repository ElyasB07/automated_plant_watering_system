package eecs1021;

// Standard Java libraries
import java.awt.*;
import java.util.Timer;
import java.io.IOException;
import java.util.ArrayList;

// Firmata / Arduino libraries
import org.firmata4j.Pin;
import org.firmata4j.IODevice;
import org.firmata4j.I2CDevice;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.firmata.FirmataDevice;

// Graphing library
import edu.princeton.cs.introcs.StdDraw;

public class PlantWateringSystem {

	public static void main(String[] args) throws IOException, InterruptedException {

		// Serial port used to communicate with the Arduino
		String MyPort = "/dev/cu.usbserial-0001";
		IODevice arduinoDevice = new FirmataDevice(MyPort);

		// Moisture threshold values
		int LowmoistureThreshold = 2;
		int HighmoistureThreshold = 1;

		// Start communication with the Arduino
		try {
			arduinoDevice.start();
			System.out.println("Plant watering System has started");
			arduinoDevice.ensureInitializationIsDone();
		} catch (Exception ex) {
			System.out.println("Plant watering System does not work, please try again");
		}

		// Initialize button, pump, and moisture sensor pins
		Pin buttonPin = arduinoDevice.getPin(6);
		buttonPin.setMode(Pin.Mode.INPUT);

		Pin pumpPin = arduinoDevice.getPin(2);
		pumpPin.setMode(Pin.Mode.OUTPUT);

		Pin sensorPin = arduinoDevice.getPin(15);
		sensorPin.setMode(Pin.Mode.ANALOG);

		// Setup the OLED LED display using I2C
		I2CDevice i2cDevice = arduinoDevice.getI2CDevice((byte) 0x3C);
		SSD1306 displayDevice = new SSD1306(i2cDevice, SSD1306.Size.SSD1306_128_64);
		displayDevice.init();

		// Attach button listener to control pump activation
		ButtonListener buttonListener = new ButtonListener(pumpPin, buttonPin, displayDevice);
		arduinoDevice.addEventListener(buttonListener);

		// System state and timing variables
		boolean systemActive = false;
		long lastButtonPressTime = 0;
		double moistureLevel;

		// Store historical moisture values for graphing
		ArrayList<Double> moistureHistory = new ArrayList<>();

		// Schedule periodic updates for the LED display
		LEDDisplay display = new LEDDisplay(systemActive, displayDevice);
		Timer timer = new Timer();
		timer.schedule(display, 500, 1000);

		// Main control loop
		while (true) {

			long currentTime = System.currentTimeMillis();
			long buttonValue = buttonPin.getValue();

			// Toggle system state when button is pressed (with debounce)
			if (buttonValue == 1 && currentTime - lastButtonPressTime > 500) {
				lastButtonPressTime = currentTime;
				systemActive = !systemActive;

				System.out.println(systemActive
						? "The watering plant system has been activated."
								: "The watering plant system has been deactivated.");

				if (!systemActive) {
					pumpPin.setValue(0);
					System.out.println("Pump has been stopped manually!");
					System.out.println("Please press the button again to restart.");
				}
			}

			// Execute watering logic only when system is active
			if (systemActive) {

				// Read moisture sensor value
				long moistureValue = sensorPin.getValue();

				// Normalize sensor readings using thresholds
				if (moistureValue < 550) {
					moistureValue = HighmoistureThreshold;
				} else if (moistureValue > 700) {
					moistureValue = LowmoistureThreshold;
				}

				// Convert moisture reading into percentage
				moistureLevel = 1 - (moistureValue - HighmoistureThreshold)
						/ (double) (LowmoistureThreshold - HighmoistureThreshold);
				moistureLevel = moistureLevel * 100.0;

				// Store moisture data for visualization
				moistureHistory.add(moistureLevel);

				// Display moisture trend graph
				GraphMoistureData(moistureHistory);
				Thread.sleep(500);

				// Control pump based on moisture level
				if (moistureValue > HighmoistureThreshold) {
					pumpPin.setValue(1);
					display.setSystemActive(true);
					display.setMoistureValue((float) moistureLevel);
				} else {
					pumpPin.setValue(0);
					display.setMoistureValue((float) moistureLevel);
					display.setSystemActive(false);
				}

				// Print moisture level to console and update display
				System.out.println("Moisture level: " + moistureLevel);
				display.moistureLevel(moistureLevel);
			}
		}
	}

	// Draws a real-time graph of moisture levels over time
	private static void GraphMoistureData(ArrayList<Double> data) {

		StdDraw.clear();

		// Draw axes
		StdDraw.line(0, 0, data.size(), 0);
		StdDraw.line(0, 0, 0, 100);

		StdDraw.setXscale(-1, data.size() + 1);
		StdDraw.setYscale(-0.10, 100);

		// Axis labels
		StdDraw.text(data.size() / 2.0, -2, "Time");
		StdDraw.setPenColor(Color.red);
		StdDraw.text(data.size() / 2.0, 101, "Time vs Moisture Graph");

		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(-0.5, 40, "Moisture", 90);

		// Plot moisture values
		for (int i = 1; i < data.size(); i++) {
			double x1 = i - 1;
			double y1 = data.get(i - 1);
			double x2 = i;
			double y2 = data.get(i);

			StdDraw.line(x1, y1, x2, y2);
		}
	}
}