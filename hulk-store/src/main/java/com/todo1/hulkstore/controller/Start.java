package main.java.com.todo1.hulkstore.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.com.todo1.hulkstore.view.PrincipalUI;

public class Start {
	private static Logger logger = Logger.getLogger(Start.class);

	public static void main(String[] args) {
		String log4jConfPath = "resources\\log.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		logger.info("Starting application");
		
		PrincipalUI principalUI = new PrincipalUI();
		principalUI.setVisible(true);
	}

}
