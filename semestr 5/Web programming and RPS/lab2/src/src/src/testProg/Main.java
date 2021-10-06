package src.src.testProg;

import actions.Actions;
import entities.Flat;
import entities.WireDevice;
import entities.WirelessDevice;
import factory.ElectricalDeviceFactory;

import java.util.Arrays;

/**
 * Test program
 */
public class Main {
	public static void main(String[] args) {
		WirelessDevice[] testArray = new WirelessDevice[5];
		testArray[0] = (WirelessDevice) ElectricalDeviceFactory.getDevice(true, false, 100, 10);
		testArray[1] = (WirelessDevice) ElectricalDeviceFactory.getDevice(true, false, 200, 10);
		testArray[2] = (WirelessDevice) ElectricalDeviceFactory.getDevice(true, true, 10, 10);
		testArray[3] = (WirelessDevice) ElectricalDeviceFactory.getDevice(true, false, 10, 9);
		testArray[4] = (WirelessDevice) ElectricalDeviceFactory.getDevice(true, true, 300, 10);
		WirelessDevice[] testArray2 = testArray.clone();

		WireDevice[] testArrayW = new WireDevice[5];
		testArrayW[0] = (WireDevice) ElectricalDeviceFactory.getDevice(false, false, 100, 10);
		testArrayW[1] = (WireDevice) ElectricalDeviceFactory.getDevice(false, false, 200, 10);
		testArrayW[2] = (WireDevice) ElectricalDeviceFactory.getDevice(false, false, 10, 10);
		testArrayW[3] = (WireDevice) ElectricalDeviceFactory.getDevice(false, false, 10, 9);
		testArrayW[4] = (WireDevice) ElectricalDeviceFactory.getDevice(false, true, 300, 10);
		WirelessDevice[] testArrayW2 = testArray.clone();

		Flat testFlat = new Flat(testArray, testArrayW);
		System.out.println("Power:" + Actions.countPower(testFlat));
		System.out.println("Array before sorting");
		for (WirelessDevice wd : testArray) {
			System.out.println('\t' + wd.toString());
		}

		System.out.println("Sorting by power");
		Actions.sortByPower(testArray2);
		for (WirelessDevice wd : testArray2) {
			System.out.println('\t' + wd.toString());
		}

		System.out.println("Sorting by default");
		Arrays.sort(testArray);
		for (WirelessDevice wd : testArray) {
			System.out.println('\t' + wd.toString());
		}

		System.out.println("Searching 9 to 11 power from testFlat");
		System.out.println(Actions.searchByPower(9, 11, testFlat).toString());
	}
}
