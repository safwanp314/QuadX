package com.huston.panels.gauges;


public class GaugesUtil {

	public static final double DEG2RAD = Math.PI/180.0f;
	
	public static void circularRectagles(double cenX, double cenY, double radius, double angle, double len, double width, int[] xPoints, int[] yPoints)
    {
        double deg2Rad = (angle/2) * GaugesUtil.DEG2RAD;
        
        xPoints[0] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) + (width / 2) * Math.sin(deg2Rad));
        yPoints[0] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) - (width / 2) * Math.cos(deg2Rad));
        
        xPoints[1] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) + (width / 2) * Math.sin(deg2Rad));
        yPoints[1] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) - (width / 2) * Math.cos(deg2Rad));
        
        xPoints[2] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) - (width / 2) * Math.sin(deg2Rad));
        yPoints[2] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) + (width / 2) * Math.cos(deg2Rad));
        
        xPoints[3] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) - (width / 2) * Math.sin(deg2Rad));
        yPoints[3] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) + (width / 2) * Math.cos(deg2Rad));
    }
	/*
	public static void circularRectagles(double cenX, double cenY, double radius, double angle, double len, double width, int[] xPoints, int[] yPoints)
    {
        double deg2Rad = (angle/2) * GraphicsUtil.DEG2RAD;
        double delta = 0.1*width;
        if(delta<1) {
        	delta = 1.0; 
        }
        
        xPoints[0] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) + delta * Math.sin(deg2Rad));
        yPoints[0] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) - delta * Math.cos(deg2Rad));
        
        xPoints[1] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) + (width / 2) * Math.sin(deg2Rad));
        yPoints[1] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) - (width / 2) * Math.cos(deg2Rad));
        
        xPoints[2] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) + (width / 2) * Math.sin(deg2Rad));
        yPoints[2] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) - (width / 2) * Math.cos(deg2Rad));
        
        xPoints[3] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) + delta * Math.sin(deg2Rad));
        yPoints[3] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) - delta * Math.cos(deg2Rad));
        
        xPoints[4] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) - delta * Math.sin(deg2Rad));
        yPoints[4] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) + delta * Math.cos(deg2Rad));
        
        xPoints[5] = (int)(cenX + (radius + len / 2) * Math.cos(deg2Rad) - (width / 2) * Math.sin(deg2Rad));
        yPoints[5] = (int)(cenY + (radius + len / 2) * Math.sin(deg2Rad) + (width / 2) * Math.cos(deg2Rad));
        
        xPoints[6] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) - (width / 2) * Math.sin(deg2Rad));
        yPoints[6] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) + (width / 2) * Math.cos(deg2Rad));
    
        xPoints[7] = (int)(cenX + (radius - len / 2) * Math.cos(deg2Rad) - delta * Math.sin(deg2Rad));
        yPoints[7] = (int)(cenY + (radius - len / 2) * Math.sin(deg2Rad) + delta * Math.cos(deg2Rad));
    }*/
}
