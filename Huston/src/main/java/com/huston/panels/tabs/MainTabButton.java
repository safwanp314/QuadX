package com.huston.panels.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class MainTabButton extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private String label;
	private Image image;
	private int width,height;
	private boolean pressed;
	/**
	 * @wbp.parser.constructor
	 */
	public MainTabButton(String label, Image image) {
		super();
		this.width = 80;
		this.height = 75;
		this.label = label;
		this.image = image;
		initilize();
	}
	
	public MainTabButton(int width, int height, String label, Image image) {
		super();
		this.width = width;
		this.height = height;
		this.label = label;
		this.image = image;
		initilize();
	}
	
	private void initilize() {
		createGUI();
		setPressed(false);
	}
	
	private void createGUI() {
		setBounds(0, 0, width, height);
		setPreferredSize(new Dimension(width, height));	
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel(label);
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(4, 5, width-8, 20);
		add(lblNewLabel);
		
		JLabel lblMainBg = new JLabel();
		if(image!=null) {
		int delta = 5;
			ImageIcon icon = new ImageIcon(image);
			lblMainBg.setBounds((width-image.getWidth(null))/2 , height-image.getHeight(null)-delta, image.getWidth(null), image.getHeight(null));
			lblMainBg.setIcon(icon);
		}
		add(lblMainBg);			
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
		if(pressed) {
			setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		} else {
			setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		}
	}
}