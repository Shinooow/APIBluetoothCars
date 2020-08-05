package fr.irit.smac.bluetoothcars;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class BluetoothCar {

	private StreamConnection connection;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private String url;

	public BluetoothCar(String url) {
		this.url = url;
	}

	public void connection() {
		try {
			connection = (StreamConnection) Connector.open(url);
			outputStream = connection.openOutputStream();
			printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
		} catch (IOException e) {
			System.out.println("Connection failed!");
		}
	}

	public void disconnection() {
		try {
			printWriter.close();
			outputStream.close();
			connection.close();
		} catch (IOException e) {
			System.out.println("Disconnection failed");
		}
	}

	/* UTILS FOR ORDERS */
	private void resetWheels() {
		printWriter.write(0x0004);
		printWriter.flush();
	}

	private void stopBeforeBackward() {
		resetWheels();
		printWriter.write(0x0000);
		printWriter.flush();
	}

	private void stopBeforeForward() {
		resetWheels();
		printWriter.write(0x0002);
		printWriter.flush();
	}

	/* ACTIONS */
	public void moveForward() {
		stopBeforeForward();
		printWriter.write(0x0001);
		printWriter.flush();
	}

	public void moveBackward() {
		stopBeforeBackward();
		printWriter.write(0x0003);
		printWriter.flush();
	}

	public void forwardToLeft() {
		printWriter.write(0x0005);
		printWriter.write(0x0001);
		printWriter.flush();
	}

	public void forwardToRight() {
		printWriter.write(0x0007);
		printWriter.write(0x0001);
		printWriter.flush();
	}

	public void backwardToLeft() {
		printWriter.write(0x0005);
		printWriter.write(0x0003);
		printWriter.flush();
	}

	public void backwardToRight() {
		printWriter.write(0x0007);
		printWriter.write(0x0003);
		printWriter.flush();
	}

	/* BASIC MACROS */
	// TODO rename
	public void m1() {
		try {
			forwardToLeft();
			Thread.sleep(1000);
			backwardToRight();
			Thread.sleep(1000);
			forwardToLeft();
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	/* macrose t*/
	public void m2() {
		try {
			forwardToRight();
			Thread.sleep(1000);
			backwardToLeft();
			Thread.sleep(1000);
			forwardToRight();
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m3() {
		try {
			forwardToRight();
			Thread.sleep(500);
			forwardToRight();
			Thread.sleep(500);
			forwardToRight();
			Thread.sleep(500);
			forwardToRight();
			Thread.sleep(500);
			forwardToRight();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m4() {
		try {
			forwardToLeft();
			Thread.sleep(500);
			forwardToLeft();
			Thread.sleep(500);
			forwardToLeft();
			Thread.sleep(500);
			forwardToLeft();
			Thread.sleep(500);
			forwardToLeft();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m5() {
		try {
			moveForward();
			Thread.sleep(500);
			backwardToRight();
			Thread.sleep(1000);
			backwardToRight();
			Thread.sleep(1000);
			backwardToLeft();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m6() {
		try {
			forwardToLeft();
			Thread.sleep(1000);
			moveBackward();
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m7() {
		try {
			backwardToLeft();
			Thread.sleep(1000);
			moveForward();
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public void m8() {
		try {
			forwardToRight();
			Thread.sleep(700);
			forwardToLeft();
			Thread.sleep(700);
			forwardToRight();
			Thread.sleep(700);
			forwardToLeft();
			Thread.sleep(700);
			forwardToRight();
			Thread.sleep(700);
			forwardToLeft();
			Thread.sleep(700);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception !");
		}
	}

	public String getUrl() {
		return url;
	}
	
}
