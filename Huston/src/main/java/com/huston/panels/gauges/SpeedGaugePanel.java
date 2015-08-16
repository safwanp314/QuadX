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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(image, 0, 0, size, size, null, null);
		/*g.setColor(Color.LIGHT_GRAY);
		
		double radius = 0.40*size;
		double cenX = size/2;
		double cenY = size/2;
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		float[] fractions = {0.0f, 1.0f};
		Color[] colors = {Color.WHITE , new Color(0,33,33,33)};
		for(double i=22.5;i<345;i=i+15) {
			
			GaugesUtil.circularRectagles(cenX, cenY, radius-8, 2*(i-90), 0.067*size , 0.05*size, xPoints, yPoints);
			Point start = new Point( (xPoints[0]+xPoints[1]+xPoints[2]+xPoints[3])/4,
									 (yPoints[0]+yPoints[1]+yPoints[2]+yPoints[3])/4 );
			Point end = new Point( (xPoints[0]+xPoints[1])/2,
					 				(yPoints[0]+yPoints[1])/2 );
			LinearGradientPaint paint = new LinearGradientPaint(start, end, fractions, colors,CycleMethod.REFLECT);
			g.setPaint(paint);
			g.fillPolygon(xPoints, yPoints, 4);
		}*/

	}
}