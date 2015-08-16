package com.huston.panels.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.events.DroneEvent;
import quadx.drone.controller.utils.events.DroneEventListener;

import com.huston.HustonComponent;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = -1216832031261843819L;
	private Drone drone = HustonComponent.getDrone();
	
	private int width, height;
	private int numOfRows, numOfColumn;
	private int rowHeight, columnWdth;
	private int numOfData = 0;
	private JTable table;
	private Map<String,String> dataMap = new LinkedHashMap<String,String>();
	
	/**
	 * Create the panel.
	 */
	public StatusPanel() {
		super();
		width = 680;
		height = 170;
		numOfRows = 10;
		numOfColumn = 6;
		rowHeight = height/numOfRows;
		columnWdth = width/numOfColumn;
		initilize();
	}

	public StatusPanel(int width, int height, int numOfRows, int tNumOfColumn) {
		super();
		this.width = width;
		this.height = height;
		this.numOfRows = numOfRows;
		this.numOfColumn = 2*tNumOfColumn;
		rowHeight = height/numOfRows;
		columnWdth = width/numOfColumn;
		initilize();		
	}

	private void initilize() {
		createGUI();
		drone.getEventHandler().addDroneEventListener(new StatusPanelDroneEventListener());
	}
	
	private void createGUI() {
		setBounds(0, 0, width, height);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(width,height));
		setLayout(new BorderLayout(0, 0));	
		//--------------------------------------------------------------
		table = new JTable();
		table.setEnabled(false);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setModel(new DefaultTableModel(numOfRows,numOfColumn) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[numOfColumn];
			public boolean isCellEditable(int row, int column) {
				for(int i=0;i<numOfColumn;i++) {
					columnEditables[i] = false;
				}
				return columnEditables[column];
			}
		});
		table.setRowHeight(rowHeight);
		DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
		centreRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		for(int i=0;i<numOfColumn;i++) {
			table.getColumnModel().getColumn(i).setResizable(false);
			if(i%2==0) {
				table.getColumnModel().getColumn(i).setMaxWidth(columnWdth+10);
				table.getColumnModel().getColumn(i).setMinWidth(columnWdth+10);
				table.getColumnModel().getColumn(i).setPreferredWidth(columnWdth+10);
				table.getColumnModel().getColumn(i).setCellRenderer(centreRenderer);
			} else {
				table.getColumnModel().getColumn(i).setMaxWidth(columnWdth-10);
				table.getColumnModel().getColumn(i).setMinWidth(columnWdth-10);
				table.getColumnModel().getColumn(i).setPreferredWidth(columnWdth-10);
				table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
			}
		}
		table.setBackground(getBackground());
		table.setForeground(Color.LIGHT_GRAY);
		add(table, BorderLayout.WEST);
	}
	
	public void addData(String title, String value) {
		TableModel model = table.getModel();
		int column = numOfData/numOfRows;
		int row = numOfData-column*numOfRows;
		model.setValueAt(title, row, 2*column);
		model.setValueAt("   "+value, row, 2*column+1);
		dataMap.put(title,value);
		numOfData++;
	}
	
	public void updateDataMap(Drone drone) {
		if(drone!=null) {
			dataMap.put("Pitch",String.format("%.3f",drone.getNav().pitch));
			dataMap.put("Roll",String.format("%.3f",drone.getNav().roll));
			dataMap.put("Yaw",String.format("%.3f",drone.getNav().yaw));
			dataMap.put("Altitude",String.format("%.3f",drone.getNav().altitude));
			dataMap.put("Latitude",String.format("%.3f",drone.getNav().latitude));
			dataMap.put("Longitude",String.format("%.3f",drone.getNav().longitude));
			dataMap.put("Speed",String.format("%.3f",drone.getNav().speed));
			dataMap.put("Climb Speed",String.format("%.3f",drone.getNav().climbRate));
			dataMap.put("Pitch Rate",String.format("%.3f",drone.getNav().pitchRate));
			dataMap.put("Roll Rate",String.format("%.3f",drone.getNav().rollRate));
			dataMap.put("Yaw Rate",String.format("%.3f",drone.getNav().yawRate));
			dataMap.put("Target Pitch",String.format("%.3f",drone.getGuidence().targetPitch));
			dataMap.put("Target Roll",String.format("%.3f",drone.getGuidence().targetRoll));
			dataMap.put("Target Yaw",String.format("%.3f",drone.getGuidence().targetYaw));
			dataMap.put("Avg. Propellers Speed",String.format("%.3f",drone.getPropellers().avgRotorsSpeed));
			dataMap.put("Propeller1",String.format("%.3f",drone.getPropellers().frontLeftRotorSpeed));
			dataMap.put("Propeller2",String.format("%.3f",drone.getPropellers().frontRightRotorSpeed));
			dataMap.put("Propeller3",String.format("%.3f",drone.getPropellers().rearRightRotorSpeed));
			dataMap.put("Propeller4",String.format("%.3f",drone.getPropellers().rearLeftRotorSpeed));
			dataMap.put("Wind Speed",String.format("%.3f",0.0f));
			dataMap.put("Wind Direction",String.format("%.3f",0.0f));
		}
	}
	
	public void updateGUI() {
		TableModel model = table.getModel();
		Iterator<Entry<String,String>> it = dataMap.entrySet().iterator();
		int i = 0;
		while(it.hasNext()) {
			Entry<String,String> entry = it.next();
			int column = i/numOfRows;
			int row = i-column*numOfRows;
			model.setValueAt("  "+entry.getKey(), row, 2*column);
			model.setValueAt("   "+entry.getValue(), row, 2*column+1);
			i++;
		}
	}
	
	private class StatusPanelDroneEventListener implements DroneEventListener {

		public void actionPerformed(DroneEvent event) {
			updateDataMap(event.getDrone());
			updateGUI();
		}	
	}
}