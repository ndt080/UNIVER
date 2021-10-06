package src.src.actions;

import entities.Flat;
import entities.SearchResult;
import src.src.entities.WireDevice;
import src.src.entities.WirelessDevice;

import java.util.*;

public class Actions {
	/**
	 * @param powerMin - min power needed to find
	 * @param powerMax - max power needed to find
	 * @param input    - array of wireless devices
	 * @return list of wireless devices which own that power
	 */
	public static List<WirelessDevice> searchByPower(int powerMin, int powerMax, WirelessDevice[] input) {
		List<WirelessDevice> result = new ArrayList<WirelessDevice>();
		for (WirelessDevice elem : input) {
			if (elem.getPower() > powerMin && elem.getPower() < powerMax)
				result.add(elem);
		}
		return result;
	}

	/**
	 * @param powerMin - min power needed to find
	 * @param powerMax - max power needed to find
	 * @param input    - array of wire devices
	 * @return list of wire devices which own that power
	 */
	public static List<WireDevice> searchByPower(int powerMin, int powerMax, WireDevice[] input) {
		List<WireDevice> result = new ArrayList<WireDevice>();
		for (WireDevice elem : input) {
			if (elem.getPower() > powerMin && elem.getPower() < powerMax)
				result.add(elem);
		}
		return result;
	}

	/**
	 * @param paramMin - min time before charge needed to find
	 * @param paramMax - max time before charge needed to find
	 * @param input    - array of wireless devices
	 * @return list of wireless devices which own that power
	 */
	public static List<WirelessDevice> searchByParam(double paramMin, double paramMax, WirelessDevice[] input) {
		List<WirelessDevice> result = new ArrayList<WirelessDevice>();
		for (WirelessDevice elem : input) {
			if (elem.getTimeWithoutCharging() > paramMin && elem.getTimeWithoutCharging() < paramMax)
				result.add(elem);
		}
		return result;
	}

	/**
	 * @param paramMin - min wire length needed to find
	 * @param paramMax - max wire length needed to find
	 * @param input    - array of wire devices
	 * @return list of devices which own that power
	 */
	public static List<WireDevice> searchByParam(double paramMin, double paramMax, WireDevice[] input) {
		List<WireDevice> result = new ArrayList<WireDevice>();
		for (WireDevice elem : input) {
			if (elem.getWireLength() > paramMin && elem.getWireLength() < paramMax)
				result.add(elem);
		}
		return result;
	}

	/**
	 * Sorting by power
	 *
	 * @param input - wireless devices array
	 */
	public static void sortByPower(WirelessDevice[] input) {
		Collections.sort(Arrays.asList(input), new Comparator<WirelessDevice>() {
			public int compare(WirelessDevice o1, WirelessDevice o2) {
				return Double.compare(o1.getPower(), o2.getPower());
			}
		});
	}

	/**
	 * Sorting by power
	 *
	 * @param input - wire devices array
	 */
	public static void sortByPower(WireDevice[] input) {
		Collections.sort(Arrays.asList(input), new Comparator<WireDevice>() {
			public int compare(WireDevice o1, WireDevice o2) {
				return Double.compare(o1.getPower(), o2.getPower());
			}
		});
	}

	/**
	 * Sorting by time without charging
	 *
	 * @param input - wireless devices array
	 */
	public static void sortByTimeWithoutCharging(WirelessDevice[] input) {
		Collections.sort(Arrays.asList(input), new Comparator<WirelessDevice>() {
			public int compare(WirelessDevice o1, WirelessDevice o2) {
				return Double.compare(o1.getTimeWithoutCharging(), o2.getTimeWithoutCharging());
			}
		});
	}

	/**
	 * Sorting by wire length
	 *
	 * @param input - wire devices array
	 */
	public static void sortByWireLength(WireDevice[] input) {
		Collections.sort(Arrays.asList(input), new Comparator<WireDevice>() {
			public int compare(WireDevice o1, WireDevice o2) {
				return Double.compare(o1.getWireLength(), o2.getWireLength());
			}
		});
	}

	/**
	 * Counts whole wireless power
	 *
	 * @param input - wireless devices array
	 */
	private static int countPower(WirelessDevice[] input) {
		int result = 0;
		for (WirelessDevice elem : input) {
			if (elem.isConnected()) {
				result += elem.getPower();
			}
		}
		return result;
	}

	/**
	 * Counts whole wire power
	 *
	 * @param input - wire devices array
	 */
	private static int countPower(WireDevice[] input) {
		int result = 0;
		for (WireDevice elem : input) {
			if (elem.isConnected()) {
				result += elem.getPower();
			}
		}
		return result;
	}

	/**
	 * Counts flat whole power
	 *
	 * @param input - input flat
	 * @return flats power
	 */
	public static int countPower(Flat input) {
		return countPower(input.getWirelessDevices()) + countPower((input.getWireDevices()));
	}

	/**
	 * Counts flat whole power
	 *
	 * @param powerMin - min time before charge needed to find
	 * @param powerMax - max time before charge needed to find
	 * @param input - input flat
	 * @return flats power
	 */
	public static SearchResult searchByPower(int powerMin, int powerMax, Flat input) {
		return new SearchResult( Actions.searchByPower(powerMin, powerMax, input.getWirelessDevices()),
		Actions.searchByPower(powerMin, powerMax, input.getWireDevices()));
	}
	/**
	 * Counts flat whole power
	 *
	 * @param paramMin - min time before charge needed to find
	 * @param paramMax - max time before charge needed to find
	 * @param input - input flat
	 * @return flats power
	 */
	public static SearchResult searchByParam(double paramMin, double paramMax, Flat input) {
		return new SearchResult(Actions.searchByParam(paramMin, paramMax, input.getWirelessDevices()),
		 Actions.searchByParam(paramMin, paramMax, input.getWireDevices()));
	}

}
