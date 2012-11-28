package com.example.potterwand;

public enum MyAccountConstants {
	HELLO("hello"), GOOD("goodbye"), SILENT("SILENT"), ROTATE("rotate"), LOCK("lock"), NOX("knox"), STRAIGHT("up"), 
	CANCEL("cancel"), QUIET("quiet");

	private MyAccountConstants(String name) {
		this.name = name;
	}

	private final String name;

	public String toString() {
		return name;
	}

	public static MyAccountConstants fromString(String text) {
		if (text != null) {
			for (MyAccountConstants b : MyAccountConstants.values()) { 
				if (text.equalsIgnoreCase(b.name)) {
					return b;
				}
			}
		}
		return null;
	}

}
