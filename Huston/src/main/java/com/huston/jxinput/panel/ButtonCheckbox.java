package com.huston.jxinput.panel;

import javax.swing.JCheckBox;

import de.hardcode.jxinput.Button;

public class ButtonCheckbox extends JCheckBox {

	private static final long serialVersionUID = -6541357356155670047L;
	private Button mButton;

	public ButtonCheckbox(String name, Button button) {
		super(name);
		this.setEnabled(false);
		mButton = button;
	}

	public void update() {
		boolean state = mButton.getState();
		if (state != this.isSelected()) {
			this.setSelected(state);
		}
	}
}
