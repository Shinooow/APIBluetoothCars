package fr.irit.smac.bluetoothclient;

import java.io.IOException;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import fr.irit.smac.bluetoothcars.BluetoothCar;

public class BluetoothDiscoveryListener implements DiscoveryListener {

	private BluetoothClient discover;

	public BluetoothDiscoveryListener(BluetoothClient discover) {
		this.discover = discover;
	}

	@Override
	public void deviceDiscovered(RemoteDevice bluetoothDevice, DeviceClass deviceClass) {
		String bluetoothFriendlyName = "";
		String bluetoothAddress = "";
		try {
			bluetoothFriendlyName = bluetoothDevice.getFriendlyName(false);
			bluetoothAddress = bluetoothDevice.getBluetoothAddress();
			String upperFriendlyName = bluetoothFriendlyName.toLowerCase();
			if (upperFriendlyName.startsWith("beewi")) {
				System.out.println("-- BeeWi device discovered!");
				System.out.println("Name: " + bluetoothFriendlyName + "\nAddress: " + bluetoothAddress);
				discover.addDiscoveredDevice(bluetoothDevice);
			}
		} catch (IOException e) {
			System.out.println("BluetoothError");
		}
	}

	@Override
	public void inquiryCompleted(int arg0) {
		synchronized (BluetoothClient.lock) {
			BluetoothClient.lock.notify();
		}

	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		synchronized (BluetoothClient.lock) {
			BluetoothClient.lock.notify();
		}

	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		for (int i = 0; i < servRecord.length; i++) {
			String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			if (url == null) {
				continue;
			}
			DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
			if (serviceName != null) {
				String serviceNameValue = serviceName.getValue().toString();
				if(serviceNameValue.startsWith("SPP")) {
					System.out.println("service " + serviceName.getValue() + " found " + url);
					BluetoothCar newBluetoothCar = new BluetoothCar(url);
					discover.addBluetoothCar(newBluetoothCar);
				}
			} else {
				System.out.println("service found " + url);
			}
		}
	}

}
