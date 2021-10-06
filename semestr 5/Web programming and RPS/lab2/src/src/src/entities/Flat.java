package entities;

/**
 * Flat class
 */
public class Flat {
	/**
	 * Wireless devices
	 */
	private WirelessDevice[] wirelessDevices;
	/**
	 * Wire devices
	 */
	private WireDevice[] wireDevices;

	/**
	 * Flat constructor
	 * @param wirelessDevices - wireless devices array
	 * @param wireDevices - wire devices array
	 */
	public Flat(WirelessDevice[] wirelessDevices, WireDevice[] wireDevices) {
		this.wirelessDevices = wirelessDevices;
		this.wireDevices = wireDevices;
	}

	/**
	 * Wireless devices array getter
	 * @return wireless devices array
	 */
	public WirelessDevice[] getWirelessDevices() {
		return wirelessDevices;
	}

	/**
	 * Wireless devices array setter
	 * @param wirelessDevices - wireless devices input array
	 */
	public void setWirelessDevices(WirelessDevice[] wirelessDevices) {
		this.wirelessDevices = wirelessDevices;
	}

	/**
	 * Wire devices array getter
	 * @return wire devices array
	 */
	public WireDevice[] getWireDevices() {
		return wireDevices;
	}

	/**
	 * Wire devices array setter
	 * @param wireDevices - wire devices input array
	 */
	public void setWireDevices(WireDevice[] wireDevices) {
		this.wireDevices = wireDevices;
	}

}
