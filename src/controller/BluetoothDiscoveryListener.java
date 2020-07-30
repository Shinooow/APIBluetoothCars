package controller;

import java.io.IOException;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

public class BluetoothDiscoveryListener implements DiscoveryListener{

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
			discover.addDiscoveredDevice(bluetoothDevice);
		} catch(IOException e) {
			System.out.println("BluetoothError");
		}
		System.out.println("DeviceDiscovered!");
		System.out.println("Name: " + bluetoothFriendlyName + "\nAddress: " + bluetoothAddress + " " + deviceClass.getServiceClasses());
		
	}

	@Override
	public void inquiryCompleted(int arg0) {
		synchronized(BluetoothClient.lock){
            BluetoothClient.lock.notify();
        }
		
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		synchronized(BluetoothClient.lock){
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
                System.out.println("service " + serviceName.getValue() + " found " + url);
                //HERE
                BluetoothCar newBluetoothCar = new BluetoothCar(url);
                discover.addBluetoothCar(newBluetoothCar);
            } else {
                System.out.println("service found " + url);
            }
            
          
        }
	}

}
