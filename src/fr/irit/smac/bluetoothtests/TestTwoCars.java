package fr.irit.smac.bluetoothtests;

import fr.irit.smac.bluetoothcars.BluetoothCar;
import fr.irit.smac.bluetoothclient.BluetoothClient;

public class TestTwoCars {
	
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
		}

		Thread taskThread = new Thread(new Runnable() {
			@Override
			public void run() {
				BluetoothCar bDevice = discover.getBluetoothCars().get(0);
				for (int j = 0; j < 3; j++) {
					bDevice.moveForward();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		taskThread.start();
		Thread taskThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				BluetoothCar bDevice = discover.getBluetoothCars().get(1);
				for (int j = 0; j < 3; j++) {
					bDevice.moveForward();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		taskThread2.start();
	}
}
