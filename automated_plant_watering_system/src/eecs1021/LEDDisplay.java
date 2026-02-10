package eecs1021;

import org.firmata4j.ssd1306.SSD1306;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class LEDDisplay extends TimerTask {

	private boolean systemActive;
	private SSD1306 displayDevice;
	private float moistureValue;
	private double calculatedMoisture;

	public LEDDisplay(boolean systemActive, SSD1306 displayDevice) {
		this.displayDevice = displayDevice;
		this.systemActive = systemActive;
	}

	@Override
	public void run() {
		// Display pump status
		if (systemActive) {
			displayDevice.getCanvas().drawString(6, 5, "Pump is ON");
		} else {
			displayDevice.getCanvas().drawString(6, 5, "Pump is OFF");
		}

		DecimalFormat format = new DecimalFormat("###.##");
		displayDevice.getCanvas().drawString(
				6,
				20,
				"Moisture " + format.format(calculatedMoisture)
				);

		displayDevice.display();
	}

	public void setValue(float moistureValue) {
		this.moistureValue = moistureValue;
	}

	public void setSystemActive(boolean systemActive) {
		this.systemActive = systemActive;
	}

	public void moistureLevel(double calculatedMoisture) {
		this.calculatedMoisture = calculatedMoisture;
	}

	public void setMoistureValue(float moistureLevel) {
		// Optional: implement if needed
	}
}