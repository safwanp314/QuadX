package com.huston.panels.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class MainTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	
	private Map<String,Object[]> listOfTabs = new TreeMap<String, Object[]>();
	private MouseListener tabButtonListener = new MouseListener() {
		
		public void mouseReleased(MouseEvent e) {
		}	
		public void mousePressed(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseClicked(MouseEvent e) {
			String buttonName = e.getComponent().getName();
			selectTab(buttonName);
		}
	};
	/**
	 * Create the panel.
	 */
	public MainTabPanel() {
		super();
		this.width = 1200;
		this.height = 85;
		initilize();
	}
	
	public MainTabPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initilize();
	}
	
	private void initilize() {
		createGUI();
	}
	
	private void createGUI() {
		//--------------------------------------------------------------
		setBounds(0, 0, width, height);
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.DARK_GRAY);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		//--------------------------------------------------------------
	}
	
	public void selectTab(String tabButtonName) {
		if(tabButtonName!=null) {
			Iterator<Entry<String, Object[]>>  it = listOfTabs.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Object[]> entry = it.next();
				Object[] tab = entry.getValue();
				if(tabButtonName.equals(entry.getKey())) {
					((MainTabButton)tab[0]).setPressed(true);
					((Component)tab[1]).setVisible(true);
				} else {
					((MainTabButton)tab[0]).setPressed(false);
					((Component)tab[1]).setVisible(false);
				}
			}
		}
	}
	
	public void addTab(String name, Image icon, Component component) {
		if(name!=null && component!=null) {
			Object[] tab = new Object[2];
			MainTabButton button = new MainTabButton(110, 75,name,icon);
			button.setName(name);
			button.setBackground(getBackground());
			button.addMouseListener(tabButtonListener);
			button.setPressed(false);
			tab[0] = button;
			tab[1] = component;
			listOfTabs.put(name, tab);
			add(button);
		}
	}
}