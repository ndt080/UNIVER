package entities;

import java.util.Objects;

public class WireDevice extends ElectricalAppliance implements Comparable<WireDevice> {
	/**
	 * Wire electrical appliance constructor
	 */
	public WireDevice(boolean wireless, boolean connected, int power, double wireLength) {
		super(wireless, connected, power);
		this.wireLength = wireLength;
	}

	/**
	 * wire length getter
	 *
	 * @return wire length (double), meters
	 */
	public double getWireLength() {
		return wireLength;
	}

	/**
	 * wire length setter
	 *
	 * @param wireLength length of wire(double)
	 */
	public void setWireLength(double wireLength) {
		this.wireLength = wireLength;
	}

	/**
	 * describes wire length
	 */
	public double wireLength;

	/**
	 * exploring equality of objects
	 *
	 * @param o exploring object
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WireDevice)) return false;
		if (!super.equals(o)) return false;
		WireDevice that = (WireDevice) o;
		return Double.compare(that.getWireLength(), getWireLength()) == 0;
	}

	/**
	 * Hash func for electrical appliances
	 *
	 * @return device's hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getWireLength());
	}

	/**
	 * converting device's info to string
	 *
	 * @return string contains device data
	 */
	@Override
	public String toString() {
		return "WireDevice{" +
		"wireless=" + wireless +
		", connected=" + connected +
		", power=" + power +
		", wireLength=" + wireLength +
		'}';
	}

	@Override
	public int compareTo(WireDevice u) {
		if (this.power == u.power) {
			return Double.compare(this.wireLength, u.wireLength);
		}
		return Integer.compare(this.power, u.power);
	}
}
