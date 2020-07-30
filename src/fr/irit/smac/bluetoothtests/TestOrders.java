package fr.irit.smac.bluetoothtests;

import fr.irit.smac.bluetoothcars.BluetoothCar;
import fr.irit.smac.bluetoothclient.BluetoothClient;

public class TestOrders {

	public static void main(String[] args) {
		System.out.println("Creating objects...");
		BluetoothClient discover = new BluetoothClient();
		System.out.println("Recherche..");
		discover.findDevices();
		System.out.println("Services..");
		discover.testService();
		System.out.println("Order tests");
		for (BluetoothCar bDevice : discover.getBluetoothCars()) {
			bDevice.connection();
			bDevice.moveForward();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.forwardToLeft();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.forwardToRight();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.moveBackward();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.backwardToLeft();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.backwardToRight();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bDevice.disconnection();
		}
	}
}
