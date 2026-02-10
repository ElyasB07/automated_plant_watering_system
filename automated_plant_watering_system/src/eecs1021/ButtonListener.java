package eecs1021;

import org.firmata4j.*;
import java.io.IOException;
import org.firmata4j.ssd1306.SSD1306;

public class ButtonListener implements IODeviceEventListener {
	// setting the pins
	private final Pin pumpPin;
	private final Pin buttonPin;
	private final SSD1306 displayDevice;

	public ButtonListener(Pin pumpPin, Pin buttonPin, SSD1306 displayDevice) {
		this.pumpPin = pumpPin;
		this.buttonPin = buttonPin;
		this.displayDevice = displayDevice;
	}

	@Override
	public void onPinChange(IOEvent event) {
		if (event.getPin().equals(buttonPin) && event.getValue() == 1) {
			try {
				pumpPin.setValue(pumpPin.getValue() == 0 ? 1 : 0);
				displayDevice.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStart(IOEvent event) {}

	@Override
	public void onStop(IOEvent event) {}

	@Override
	public void onMessageReceive(IOEvent event, String message) {}
}
