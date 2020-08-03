package fr.irit.smac.bluetoothclient;

import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;

import fr.irit.smac.bluetoothcars.BluetoothCar;

public class BluetoothClient {

	private DiscoveryAgent discoveryAgent;
	private LocalDevice localDevice;
	private BluetoothDiscoveryListener listener;
	private List<RemoteDevice> discoveredDevices;
	private List<BluetoothCar> bluetoothCars;

	public static Object lock = new Object();

	public BluetoothClient() {
		discoveredDevices = new ArrayList<>();
		bluetoothCars = new ArrayList<>();
		listener = new BluetoothDiscoveryListener(this);
		try {
			localDevice = LocalDevice.getLocalDevice();
			discoveryAgent = localDevice.getDiscoveryAgent();
		} catch (BluetoothStateException e) {
			System.out.println("-- OS Bluetooth init error");
		}
	}

	/* find */
	public void findDevices() {
		try {
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC, listener);
			synchronized (lock) {
				lock.wait();
			}
		} catch (BluetoothStateException | InterruptedException a) {
			System.out.println("-- Bluetooth/wait error");
		}
	}

	public void testService() {
		UUID[] uuidSet = new UUID[1];
		uuidSet[0] = new UUID(0x1101); // OBEX Object Push service

		int[] attrIDs = new int[] { 0x0100 };

		for (RemoteDevice device : discoveredDevices) {
			try {
				discoveryAgent.searchServices(attrIDs, uuidSet, device, listener);
			} catch (BluetoothStateException e1) {
				e1.printStackTrace();
			}
			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	public void moveForward(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.moveForward();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void moveBackward(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.moveBackward();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void forwardToLeft(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.forwardToLeft();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void forwardToRight(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.forwardToRight();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void backwardToLeft(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.backwardToLeft();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}

	public void backwardToRight(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.backwardToRight();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m1(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m1();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m2(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m2();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m3(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m3();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m4(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m4();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m5(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m5();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m6(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m6();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m7(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m7();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public void m8(int carId) {
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(carId);
			bluetoothCar.m8();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
	}
	
	public List<RemoteDevice> getDiscoveredDevices() {
		return discoveredDevices;
	}

	public void addBluetoothCar(BluetoothCar bluetoothCar) {
		bluetoothCars.add(bluetoothCar);
	}

	public List<BluetoothCar> getBluetoothCars() {
		return bluetoothCars;
	}

	public void addDiscoveredDevice(RemoteDevice device) {
		discoveredDevices.add(device);
	}

	public void removeDiscoveredDevice(RemoteDevice device) {
		discoveredDevices.remove(device);
	}

	public void removeDiscoveredDevice(int id) {
		discoveredDevices.remove(id);
	}

}
