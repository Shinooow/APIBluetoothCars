package fr.irit.smac.bluetoothclient;

import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;

import fr.irit.smac.bluetoothcars.BluetoothCar;
import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.skill;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.skills.Skill;

@skill(name = "Bluetooth")
@doc(value = "The Bluetooth skill makes Gama able to connect itself to the vehicules via Bluetooth, "
		+ "this skill gives all the actions to move a car (move forward, move backward"
		+ "turn to the left ...)") 
public class BluetoothClient extends Skill{

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
		findDevices();
		testService();
		System.out.println("Client created");
	}

	/* find */
	public void findDevices() {
		try {
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC, listener);
			synchronized (lock) {
				lock.wait();
			}
		} catch (BluetoothStateException | InterruptedException a) {
			System.out.println("-- Bluetooth: finding devices error");
		}
	}

	public void testService() {
		UUID[] uuidSet = new UUID[1];
		uuidSet[0] = new UUID(0x1101);

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
	
	@action(name = "connectCar")
	public Integer connectCar(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.connection();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "disconnectCar")
	public Integer disconnectCar(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.disconnection();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "moveForward")
	public Integer moveForward(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.moveForward();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "moveBackward")
	public Integer moveBackward(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.moveBackward();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "forwardToLeft")
	public Integer forwardToLeft(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.forwardToLeft();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "forwardToRight")
	public Integer forwardToRight(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.forwardToRight();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "backwardToLeft")
	public Integer backwardToLeft(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.backwardToLeft();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}

	@action(name = "backwardToRight")
	public Integer backwardToRight(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.backwardToRight();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "leftUTurn")
	public Integer leftUTurn(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.leftUTurn();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "rightUTurn")
	public Integer rightUTurn(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.rightUTurn();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "clockwiseCircle")
	public Integer clockwiseCircle(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.clockwiseCircle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "antiClockwiseCircle")
	public Integer antiClockwiseCircle(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.antiClockwiseCircle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "parallelParking")
	public Integer parallelParking(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.parallelParking();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "backwardParking")
	public Integer backwardParking(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.backwardParking();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "forwardParking")
	public Integer forwardParking(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.forwardParking();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
	}
	
	@action(name = "slalom")
	public Integer slalom(final IScope scope) throws GamaRuntimeException {
		Object idGuidableObject = scope.getAgentVarValue(scope.getAgent(), "idGuidable");
		Integer idGuidable = (Integer) idGuidableObject;
		try {
			BluetoothCar bluetoothCar = bluetoothCars.get(idGuidable);
			bluetoothCar.slalom();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid car");
		}
		return 0;
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
