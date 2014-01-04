package me.jdon.ludus.swear;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	
	static Plugin ls = LudusSwear.getPlugin();
	static FileConfiguration fc = ls.getConfig();
	static List<String> swears = fc.getStringList("swears");
	static List<String> exceptions = fc.getStringList("exception");
	static Boolean disabled = fc.getBoolean("DisableSwearing");
	static Boolean command = fc.getBoolean("commandChecking");
	static double  fine = fc.getDouble("Fine");
			
	public static void enable(){
		// set the config
		fc.options().copyDefaults(true);
		// save the config
		ls.saveConfig();
	}
	
	public void get(){
		
	}
	
	public static boolean addswear(String swear){
		for (String s : swears){
        	 if(swear.equalsIgnoreCase(s)){
        		 return false;
        	 }
        }
		swears.add(swear);
		fc.set("swears", swears);
		ls.saveConfig();
		return true;
	}
	
	public static boolean addexempt(String exempt){
		for (String s : exceptions){
        	 if(exempt.equalsIgnoreCase(s)){
        		 return false;
        	 }
        }
		exceptions.add(exempt);
		fc.set("exception", exceptions);
		ls.saveConfig();
		return true;
	}
	
	public static boolean removeswear(String swear){
		for (String s : swears){
       	 if(swear.equalsIgnoreCase(s)){
       		 swears.remove(swear);
				fc.set("swears", swears);
				ls.saveConfig();
				return true;
       	 }
       }
		return false;
	}
	public static boolean removeexempt(String swear){
		for (String s : exceptions){
       	 if(swear.equalsIgnoreCase(s)){
       		exceptions.remove(swear);
				fc.set("exception", exceptions);
				ls.saveConfig();
				return true;
       	 }
       }
		return false;
	}
	
	public static void therewasaswear(){
		Double pot = ls.getConfig().getDouble("pot");
		Double newpot = pot+fine;
		fc.set("pot", newpot);
		ls.saveConfig();
	}
	
	public static double getfine(){
		return fine;
	}
	
	public static List<String> getswearlist(){
		return swears;
	}
	
	public static List<String> getexceptionlist(){
		return exceptions;
	}
	
	public static boolean swearisdisabled(){
		return disabled;
	}
	public static boolean commandenabled(){
		return command;
	}

}
