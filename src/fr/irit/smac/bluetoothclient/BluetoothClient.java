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
