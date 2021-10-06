package src.src.factory;

import src.src.entities.ElectricalAppliance;
import src.src.entities.WireDevice;
import src.src.entities.WirelessDevice;

/**
 * Electrical device factory
 */
public class ElectricalDeviceFactory {
	/**
	 * Devices factory func
	 * @param wireless if device wireless or not
	 * @param connected if device has connected
	 * @param power device's power
	 * @param param changeable parameter
	 * @return Wire/WirelessDevice depends on "wireless"
	 */
	public static ElectricalAppliance getDevice(boolean wireless, boolean connected, int power, double param){
		if(wireless)
			return new WirelessDevice(true, connected, power, param);
		else
			return new WireDevice(false, connected, power, param);
	}
}
