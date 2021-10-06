package entities;

import java.util.Objects;

public class WirelessDevice extends ElectricalAppliance implements Comparable<WirelessDevice> {

	/**
	 * Wireless electrical appliance constructor
	 */
	public WirelessDevice(boolean wireless, boolean connected, int power, double timeWithoutCharging) {
		super(wireless, connected, power);
		this.timeWithoutCharging = timeWithoutCharging;
	}

	/**
	 * time without charging getter
	 *
	 * @return time without charging(double)
	 */
	public double getTimeWithoutCharging() {
		return timeWithoutCharging;
	}

	/**
	 * time without charging setter
	 *
	 * @param timeWithoutCharging time without charging(double)
	 */
	public void setTimeWithoutCharging(double timeWithoutCharging) {
		this.timeWithoutCharging = timeWithoutCharging;
	}

	/**
	 * describes devices autonomic work time
	 */
	public double timeWithoutCharging;

	/**
	 * exploring equality of objects
	 *
	 * @param o exploring object
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WirelessDevice)) return false;
		if (!super.equals(o)) return false;
		WirelessDevice that = (WirelessDevice) o;
		return Double.compare(that.getTimeWithoutCharging(), getTimeWithoutCharging()) == 0;
	}

	/**
	 * Hash func for electrical appliances
	 *
	 * @return device's hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTimeWithoutCharging());
	}

	/**
	 * converting device's info to string
	 *
	 * @return string contains device data
	 */
	@Override
	public String toString() {
		return "WirelessDevice{" +
		"wireless=" + wireless +
		", connected=" + connected +
		", power=" + power +
		", timeWithoutCharging=" + timeWithoutCharging +
		'}';
	}

	@Override
	public int compareTo(WirelessDevice u) {
		if (this.power == u.power) {
			return Double.compare(this.timeWithoutCharging, u.timeWithoutCharging);
		}
		return Integer.compare(this.power, u.power);
	}
}

