package com.huston.panels.ui;

import static javafx.concurrent.Worker.State.FAILED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LocationGaugePanel extends JPanel {

	private static final long serialVersionUID = 3998882957710554491L;

	private int width, height;
	
	private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    private final JPanel panel = new JPanel(new BorderLayout());
    
	/**
	 * Create the panel.
	 */
    public LocationGaugePanel() {
		super();
		this.width = 700;
		this.height = 450;
		initComponents();
	}
	public LocationGaugePanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initComponents();
	}
	
	private void initComponents() {
		createGUI();
		initEngine();
	}
	
	private void createGUI() {
		
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(width-10, height-10));
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		add(panel);
	}
	
	private void initEngine() {
		Platform.runLater(new Runnable() {
			
			public void run() {
				
				WebView view = new WebView();
				//view.setMaxSize(width-10, height-10);
				engine = view.getEngine();
				
				engine.getLoadWorker()
						.exceptionProperty()
						.addListener(new ChangeListener<Throwable>() {
							public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
								if (engine.getLoadWorker().getState() == FAILED) {
									SwingUtilities.invokeLater(new Runnable() {
											public void run() {
											JOptionPane.showMessageDialog(
												panel,
												(value != null) ?
												engine.getLocation() + "\n" + value.getMessage() :
												engine.getLocation() + "\nUnexpected error.",
												"Loading error...",
												JOptionPane.ERROR_MESSAGE);
										}
									});
								}
							}
						});
				jfxPanel.setPreferredSize(new Dimension(width-10, height-30));
				jfxPanel.setScene(new Scene(view));
			}
		});
	}
	
	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			public void run() {
				String tmp = toURL(url);
				if (tmp == null) {
					tmp = toURL("http://" + url);
				}
				engine.load(tmp);
			}
		});
	}
	
	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
}
