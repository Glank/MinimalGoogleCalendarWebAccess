package glank.app.examples.google.calendar;

import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import java.util.Scanner;
import java.io.File;

public class GCGlobals{
	private static final XMLPropertiesConfiguration configs = getConfigs();
	
	private static XMLPropertiesConfiguration getConfigs(){
		try{
			return new XMLPropertiesConfiguration(
				new File("config.xml")
			);
		}
		catch(ConfigurationException ce){
			throw new RuntimeException("Error initializing config file.");
		}
	}
	
	public static final String clientId = getProperty("clientId", configs);
	public static final String clientSecret = getProperty("clientSecret", configs);
	public static final String redirectUri = getProperty("redirectUri", configs);
    public static final String appName = getProperty("appName", configs);
    
    private static String getProperty(String name, XMLPropertiesConfiguration configs){
    	if(configs.containsKey(name))
    		return (String)configs.getProperty(name);
    	else{
    		Scanner in = new Scanner(System.in);
    		System.out.println("Set property '"+name+"':");
    		String value = in.nextLine();
    		configs.addProperty(name, value);
    		try{
    			configs.save();
    		}
    		catch(ConfigurationException ce){
    			throw new RuntimeException("Could not save config property: " + name);
    		}
    		return value;
    	}
    }
}
