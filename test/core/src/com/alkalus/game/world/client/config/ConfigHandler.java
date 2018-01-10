package com.alkalus.game.world.client.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigHandler {



	public ConfigHandler() {

	}

	public static boolean saveConfig(){
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("config.properties");
			// set the properties value
			prop.setProperty("fullscreen", "false");
			//prop.setProperty("dbuser", "mkyong");
			//prop.setProperty("dbpassword", "password");
			// save properties to project root folder
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static Properties loadConfig(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			return prop;
			// get the property value and print it out
			//System.out.println(prop.getProperty("database"));
			//System.out.println(prop.getProperty("dbuser"));
			//System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}


