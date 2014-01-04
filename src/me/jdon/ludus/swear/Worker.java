package me.jdon.ludus.swear;

import java.util.ArrayList;
import java.util.List;

public class Worker {
	
	public static String getFinalArg(final String[] args, final int start){
		final StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++)
		{
			if (i != start)
			{
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
	
	public static List<String> aggresivemode(String swear){
		 String message = swear.toLowerCase();
		 List<String> finalwords = new ArrayList<String>();
		// replace common replacements with normal characters
    	 String mess = message.replace("*", "");
    	 String mes3 = mess.replace("()", "o");
    	 String mes2 = mes3.replace("(", "");
    	 String mes = mes2.replace(")", "");
    	 String me1 = mes.replace("/", "");
    	 String me2 = me1.replace(".", "");
    	 String me3 = me2.replace(",", "");
    	 String me4 = me3.replace("4", "a");
    	 String me5 = me4.replace(";", "");
    	 String me6 = me5.replace("'", "");
    	 String me7 = me6.replace("#", "");
    	 String me8 = me7.replace("~", "");
    	 String me9 = me8.replace("^", "");
    	 String me10 = me9.replace("-", "");
    	 String me11 = me10.replace("+", "");
    	 String me12 = me11.replace("1", "i");
    	 String me13 = me12.replace("0", "o");
    	 String messageo = me13.replace("@", "o");
    	 String messagea = me13.replace("@", "a");
    	 //remove spaces
    	 String removespaceso = messageo.replaceAll(" ", "");
		 String removespacesa = messagea.replaceAll(" ", "");
		 //remove duplicate words in the string
		 String Finalswearchecko = removeDups(removespaceso);
		 String Finalswearchecka = removeDups(removespacesa);
		 finalwords.add(Finalswearchecko);
		 finalwords.add(Finalswearchecka);
		 finalwords.add(message);
		 return finalwords;
	}
	
	public static String removeDups(String s){
	    if ( s.length() <= 1 ) return s;
	    if( s.substring(1,2).equals(s.substring(0,1)) ) return removeDups(s.substring(1));
	    else return s.substring(0,1) + removeDups(s.substring(1));
	}
	
	public static List<String> checkswear(List<String> finalwords){
		List<String> swearlist = new ArrayList<String>();
		for(String exception: Config.getexceptionlist()){
			for(String swear :finalwords){
				if(swear.contains(exception)){
					// return false, as word is in the exception list
					return null;
				}
			}
		}
		for(String swears: Config.getswearlist()){
			for(String swear :finalwords){
				if(swear.contains(swears)){
					// return true, as word is in the swear list
					if(!(swearlist.contains(swears))){
						swearlist.add(swears);	
					}
				}
			}
		}
		if(swearlist.isEmpty())return null;
		return swearlist;
	}
	
	public static List<String> isswear(String s){
		return checkswear(aggresivemode(s));
	}

	

}
