package com.huston.panels.gauges;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SpeedGaugePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Image image;
	private Image needle;
	private int size;
	
	public SpeedGaugePanel() {
		super();
		createGUI(800);
		setPreferredSize(new Dimension(size, size));	
	}
	
	public SpeedGaugePanel(int size) {
		super();
		this.size = size;	
		createGUI(size);
		setPreferredSize(new Dimension(size, size));	
	}
	
	private void createGUI(int size) {
		try {
			Image tImage = ImageIO.read(getClass().getResource("/images/gauge_frame01.png"));
			image = tImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
			
			Image tNeedle = ImageIO.read(getClass().getResource("/images/needle.png"));
			int needleHeight = (int) (0.52*size);
			int needleWidth = needleHeight*tNeedle.getWidth(null)/tNeedle.getHeight(null);
			needle = tNeedle.getScaledInstance(needleWidth, needleHeight, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(image, 0, 0, size, size, null, null);
		g.drawImage(needle, (size-needle.getWidth(null))/2, 
							size/2-needle.getHeight(null)+20, 
							needle.getWidth(null), 
							needle.getHeight(null), null, null);
	}
}