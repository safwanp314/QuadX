package com.huston.jxinput.buttons;

import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.event.JXInputButtonEventListener;
import de.hardcode.jxinput.event.JXInputEventManager;

public class ToggleKeyButton implements Button, JXInputButtonEventListener {

	private Button keyButton;
	private boolean stat = false;
	private boolean hasChanged = false;
	
	public ToggleKeyButton(Button keyButton) {
		this.keyButton = keyButton;
		JXInputEventManager.addListener(this, keyButton);
	}
	
	@Override
	public String getName() {
		return keyButton.getName();
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public boolean getState() {
		return stat;
	}
	
	public void setState(boolean stat) {
		this.stat = stat;
	}

	@Override
	public int getType() {
		return Button.TOGGLEBUTTON;
	}

	@Override
	public void changed(JXInputButtonEvent event) {
		if(keyButton.getState()) {
			stat = !stat;
			hasChanged = true;
		} else {
			hasChanged = false;
		}
	}
}