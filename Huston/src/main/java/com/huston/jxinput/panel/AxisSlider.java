package com.huston.jxinput.panel;

import java.awt.Font;
import java.util.Dictionary;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import de.hardcode.jxinput.Axis;

public class AxisSlider extends JSlider {

	private static final long serialVersionUID = -5301384482514732283L;
	private static final Font AXIS_SLIDER_FONT = new Font("Verdana", Font.PLAIN, 9);
	private Axis mAxis;

	public AxisSlider(Axis axis) {
		super((Axis.SLIDER == axis.getType() ? 0 : -100), 100);
		this.setMajorTickSpacing(Axis.SLIDER == axis.getType() ? 25 : 50);
		this.setMinorTickSpacing(5);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		this.setEnabled(false);

		Dictionary<?, ?> labeldict = this.getLabelTable();
		Enumeration<?> labels = labeldict.elements();
		while (labels.hasMoreElements()) {
			JLabel label = (JLabel) labels.nextElement();
			label.setFont(AXIS_SLIDER_FONT);
			label.setSize(32, 12);
			label.setHorizontalAlignment(SwingConstants.LEFT);
		}
		mAxis = axis;
	}

	public void update() {
		int ax = (int) (mAxis.getValue() * 100.0);
		if (ax != this.getValue()) {
			this.setValue(ax);
			this.setToolTipText(mAxis.getName() + ": " + Double.toString(mAxis.getValue()));
		}
	}
}
