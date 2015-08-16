package com.huston.jxinput.panel;

import javax.swing.JLabel;

import de.hardcode.jxinput.Directional;

public class DirectionalLabel extends JLabel {

	private static final long serialVersionUID = -5878866372097219316L;
	private Directional mDirectional;
	int mCurrent = 0;

	public DirectionalLabel(Directional directional) {
		super(directional.getName());
		mDirectional = directional;
	}

	public void update() {
		int dir = mDirectional.getDirection();
		if (dir != mCurrent) {
			this.setText(mDirectional.getName()	+ ":  "	+ (mDirectional.isCentered() ? "-" : Integer.toString(dir)));
			mCurrent = dir;
		}
	}
}
