package entities;

import java.util.Arrays;
import java.util.List;

/**
 * Search result class
 */
public class SearchResult {
	/**
	 * Wireless device array
	 */
	private WirelessDevice[] wirelessDevices;
	/**
	 * Wire device array
	 */
	private WireDevice[] wireDevices;

	public SearchResult(List<WirelessDevice> wirelessDevices, List<WireDevice> wireDevices) {
		this.wirelessDevices = new WirelessDevice[wirelessDevices.size()];
		this.wireDevices = new WireDevice[wireDevices.size()];
		int counter=0;
		for (WirelessDevice elem: wirelessDevices) {
			this.wirelessDevices[counter] = elem;
			counter++;
		}
		counter= 0;
		for (WireDevice elem: wireDevices) {
			this.wireDevices[counter] = elem;
			counter++;
		}
	}

	/**
	 * Wireless device
	 * @return - wireless device array
	 */
	public WirelessDevice[] getWirelessDevices() {
		return wirelessDevices;
	}

	/**
	 * Wireless device setter
	 * @param wirelessDevices - wireless device array
	 */
	public void setWirelessDevices(WirelessDevice[] wirelessDevices) {
		this.wirelessDevices = wirelessDevices;
	}

	/**
	 * Wire device
	 * @return - wire device array
	 */
	public WireDevice[] getWireDevices() {
		return wireDevices;
	}

	/**
	 * Wire device setter
	 * @param wireDevices - wire device array
	 */
	public void setWireDevices(WireDevice[] wireDevices) {
		this.wireDevices = wireDevices;
	}

	@Override
	public String toString() {
		return "SearchResult{" +
		"wirelessDevices=" + Arrays.toString(wirelessDevices) +
		", wireDevices=" + Arrays.toString(wireDevices) +
		'}';
	}
}
