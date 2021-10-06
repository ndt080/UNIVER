package entities;

import java.util.Objects;

/**
 * electrical appliance class
 */
public abstract class ElectricalAppliance {

	/**
	 * describes if it is wireless or not
	 */
	protected boolean wireless;

	/**
	 * describes if it is connected to electrical net
	 */
	protected boolean connected;
	/**
	 * power of appliance
	 */
	protected int power;

	/**
	 * Electrical appliance constructor
	 */
	public ElectricalAppliance(boolean wireless, boolean connected, int power) {
		this.wireless = wireless;
		this.connected = connected;
		this.power = power;
	}

	/**
	 * Connected getter
	 * @return true if it's connected,else - false
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Connected setter
	 * @param connected true if it's connected,else - false
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	/**
	 * isWireless getter
	 *
	 * @return true if it's wireless, false if not
	 */
	public boolean isWireless() {
		return wireless;
	}

	/**
	 * isWireless setter
	 *
	 * @param wireless true if it's wireless, false if not
	 */
	public void setWireless(boolean wireless) {
		this.wireless = wireless;
	}

	/**
	 * getAge getter
	 *
	 * @return devices power (int)
	 */
	public int getPower() {
		return power;
	}

	/**
	 * getAge setter
	 *
	 * @param power devices power (int)
	 */
	public void setPower(int power) {
		this.power = power;
	}


	/**
	 * exploring equality of objects
	 *
	 * @param o exploring object
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ElectricalAppliance)) return false;
		ElectricalAppliance that = (ElectricalAppliance) o;
		return isWireless() == that.isWireless() && connected == that.connected && getPower() == that.getPower();
	}

	/**
	 * Hash func for electrical appliances
	 *
	 * @return device's hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(isWireless(), connected, getPower());
	}

	/**
	 * converting device's info to string
	 *
	 * @return string contains device data
	 */
	@Override
	public String toString() {
		return "ElectricalAppliance{" +
		"wireless=" + wireless +
		", connected=" + connected +
		", power=" + power +
		'}';
	}
}
