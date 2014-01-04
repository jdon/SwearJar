package me.jdon.ludus.swear;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands  implements CommandExecutor {
	
	public static String tag = ChatColor.BLUE + "[" + ChatColor.AQUA + "Swearjar" + ChatColor.BLUE + "]" + ChatColor.YELLOW + " ";
	public static String linetag = ChatColor.YELLOW+"=================="+tag+"==================";
	public static String line = ChatColor.YELLOW+"==============================================";
	
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (command.getName().equalsIgnoreCase("swearjar")) {
			if(args.length == 0){
			sender.sendMessage(linetag);
			if(sender.hasPermission("sj.admin")){
			sender.sendMessage(ChatColor.YELLOW+"/sj add [String] - "+ChatColor.WHITE+"adds a word to the swear list.");
			sender.sendMessage(ChatColor.YELLOW+"/sj remove [String] - "+ChatColor.WHITE+" removes a word from the swear list.");
			sender.sendMessage(ChatColor.YELLOW+"/sj exempt [String] - "+ChatColor.WHITE+"adds a word to the exemption list.");
			sender.sendMessage(ChatColor.YELLOW+"/sj rexempt [String] - "+ChatColor.WHITE+"removes a word from the exemption list.");
			sender.sendMessage(ChatColor.YELLOW+"/sj list - "+ChatColor.WHITE+"shows the swear list.");
			sender.sendMessage(ChatColor.YELLOW+"/sj elist - "+ChatColor.WHITE+"shows the exception list.");
			}
			sender.sendMessage(line);
			return true;
			}
			if(args.length >= 1){
					if(args[0].equalsIgnoreCase("list")){
						if(args.length == 1){
						if(sender.hasPermission("sj.admin")){
						sender.sendMessage(linetag);
						for (String s : Config.getswearlist()){
							sender.sendMessage("-"+s);
						}
						sender.sendMessage(line);
						return true;
						}else{
							sender.sendMessage(ChatColor.RED+"You don't have permission");
							return true;
						}
					}else{
						sender.sendMessage("/Swearjar list");
						return true;
					}
					}
					if(args[0].equalsIgnoreCase("elist")){
						if(args.length == 1){
						if(sender.hasPermission("sj.admin")){
						sender.sendMessage(linetag);
						for (String s : Config.getexceptionlist()){
							sender.sendMessage("-"+s);
						}
						sender.sendMessage(line);
						return true;
						}else{
							sender.sendMessage(ChatColor.RED+"You don't have permission");
							return true;
						}
					}else{
						sender.sendMessage("/Swearjar elist");
						return true;
					}
					}
					
				if(args[0].equalsIgnoreCase("add")){
					if(args.length > 1){
					if(sender.hasPermission("sj.admin")){
					String word = Worker.getFinalArg(args, 1).toLowerCase();
					if(Config.addswear(word) == false){
						sender.sendMessage(tag+word+" is already in the swear list");
						return true;
					}
					sender.sendMessage(tag+"You added '"+word+"' to the swear list.");
					return true;
					}else{
						sender.sendMessage(ChatColor.RED+"You don't have permission");
						return true;
					}
					}else{
						sender.sendMessage("/Swearjar add [String]");
						return true;
					}
				    }
				
				if(args[0].equalsIgnoreCase("exempt")){
					if(args.length > 1){
					if(sender.hasPermission("sj.admin")){
					String word = Worker.getFinalArg(args, 1).toLowerCase();
					if(Config.addexempt(word) == false){
						sender.sendMessage(tag+word+" is already in exception list");
						return true;
					}
					sender.sendMessage(tag+"You added '"+word+"' to the exception list.");
					return true;
					}else{
						sender.sendMessage(ChatColor.RED+"You don't have permission");
						return true;
					}
					}else{
						sender.sendMessage("/Swearjar except [String]");
						return true;
					}
				    }
				
				
			if(args[0].equalsIgnoreCase("remove")){
				if(args.length > 1){
				if(sender.hasPermission("sj.admin")){
				String word = Worker.getFinalArg(args, 1).toLowerCase();
				if(Config.removeswear(word) == false){
		       		 sender.sendMessage(tag+"'"+word+"' is not on the swear list.");
		       		 return true;
				}
				sender.sendMessage(tag+"You removed '"+word+"' from the swear list.");
       		 return true;
			}else{
				sender.sendMessage(ChatColor.RED+"You don't have permission");
				return true;
			}
			}else{
				sender.sendMessage("/Swearjar remove [String]");
				return true;
			}
			}
			
			
			if(args[0].equalsIgnoreCase("rexempt")){
				if(args.length > 1){
				if(sender.hasPermission("sj.admin")){
				String word = Worker.getFinalArg(args, 1).toLowerCase();
				if(Config.removeexempt(word) == true){
					sender.sendMessage(tag+"You removed '"+word+"' from the exception list.");
					return true;
				}
				
       		 sender.sendMessage(tag+"'"+word+"' is not on the exception list.");
       		 return true;
			}else{
				sender.sendMessage(ChatColor.RED+"You don't have permission");
				return true;
			}
			}else{
				sender.sendMessage("/Swearjar exceptremove [String]");
				return true;
			}
			}
			
			}
		}
		return false;
		}


}
