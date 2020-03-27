package util;

public class MutableBoolean {

	private boolean value;

	public MutableBoolean() {}

	public MutableBoolean(boolean value) {
		super();
		this.value = value;
	}

	public boolean booleanValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
