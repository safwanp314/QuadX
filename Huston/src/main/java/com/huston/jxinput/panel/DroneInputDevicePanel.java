package com.huston.jxinput.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;

public class DroneInputDevicePanel extends JPanel {

	private static final long serialVersionUID = -3642439824298196611L;
	
	private JPanel axisPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel directionalPanel = new JPanel();
	
	private final int NUM_OF_AXIS = 10;
	private final int NUM_OF_BUTTONS = 12;
	
	private final ArrayList<AxisSlider> mAxisSliders = new ArrayList<AxisSlider>();
	private final ArrayList<ButtonCheckbox> mButtonCheckboxes = new ArrayList<ButtonCheckbox>();
	private final ArrayList<DirectionalLabel> mDirectionalLabels = new ArrayList<DirectionalLabel>();
	
	/**
	 * Create the panel.
	 */
	public DroneInputDevicePanel() {
		super();
		
		setLayout(new BorderLayout(5, 5));
		
		axisPanel.setLayout(new GridLayout(NUM_OF_AXIS, 1, 0, 0));
		axisPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		add(axisPanel,BorderLayout.CENTER);
		
		buttonPanel.setLayout(new GridLayout(NUM_OF_BUTTONS, 0, 0, 0));
		buttonPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(buttonPanel,BorderLayout.EAST);
		
		directionalPanel.setLayout(new GridLayout(0, 6, 5, 5));
		directionalPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		add(directionalPanel,BorderLayout.SOUTH);
		
		initilize();	
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if(axisPanel!=null) {
			axisPanel.setBackground(bg);
			Component[] components = axisPanel.getComponents();
			for (Component component : components) {
				JPanel tAxisPanel = (JPanel) component;
				tAxisPanel.setBackground(bg);
				tAxisPanel.getComponent(0).setBackground(bg);
				tAxisPanel.getComponent(1).setBackground(bg);
			}
		}
		if(buttonPanel!=null) {
			buttonPanel.setBackground(bg);
			Component[] components = buttonPanel.getComponents();
			for (Component component : components) {
				component.setBackground(bg);
			}
		}
		if(directionalPanel!=null) {
			directionalPanel.setBackground(bg);
			Component[] components = directionalPanel.getComponents();
			for (Component component : components) {
				component.setBackground(bg);
			}
		}
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		
		int axisPanelWeight = (int) (0.80*preferredSize.width);
		int axisPanelHeight = preferredSize.height;
		axisPanel.setPreferredSize(new Dimension(axisPanelWeight, axisPanelHeight));
		Component[] axisComponents = axisPanel.getComponents();
		for (Component component : axisComponents) {
			JPanel tAxisPanel = (JPanel) component;
			int weightLabel = (int) (0.12*axisPanelWeight)-10;
			int weightAxis = (int) (0.88*axisPanelWeight)-10;
			int height = axisPanelHeight/NUM_OF_AXIS-10;
			tAxisPanel.setPreferredSize(new Dimension(axisPanelWeight, axisPanelHeight));
			tAxisPanel.getComponent(0).setPreferredSize(new Dimension(weightLabel,height));
			tAxisPanel.getComponent(1).setPreferredSize(new Dimension(weightAxis,height));
		}
		
		int buttonPanelWeight = (int) (0.10*preferredSize.width);
		int buttonPanelHeight = preferredSize.height;
		buttonPanel.setPreferredSize(new Dimension(buttonPanelWeight, buttonPanelHeight));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	}
	
	private void initilize() {
	}

	public void add(Axis axis) {
		JPanel tAxisPanel = new JPanel();
		tAxisPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tAxisPanel.setBackground(axisPanel.getBackground());
		
		Label axisLabel = new Label(axis.getName());
		axisLabel.setForeground(Color.LIGHT_GRAY);
		axisLabel.setBackground(tAxisPanel.getBackground());
		tAxisPanel.add(axisLabel);
		
		AxisSlider axisSlider = new AxisSlider(axis);
		mAxisSliders.add(axisSlider);
		axisSlider.setBackground(tAxisPanel.getBackground());
		tAxisPanel.add(axisSlider);
		axisPanel.add(tAxisPanel);
	}
	
	public void add(String name, Button button) {
		ButtonCheckbox checkbox = new ButtonCheckbox(name, button);
		mButtonCheckboxes.add(checkbox);
		buttonPanel.add(checkbox);
	}

	public void add(Directional directional) {
		DirectionalLabel directionalLabel = new DirectionalLabel(directional);
		mDirectionalLabels.add(directionalLabel);
		directionalPanel.add(directionalLabel);
	}
	
	public void update() {
		
		Iterator<?> it = mAxisSliders.iterator();
		while (it.hasNext()) {
			((AxisSlider) it.next()).update();
		}
		it = mButtonCheckboxes.iterator();
		while (it.hasNext()) {
			((ButtonCheckbox) it.next()).update();
		}
		it = mDirectionalLabels.iterator();
		while (it.hasNext()) {
			((DirectionalLabel) it.next()).update();
		}
	}
}
