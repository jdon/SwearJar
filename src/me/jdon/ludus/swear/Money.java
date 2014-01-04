package me.jdon.ludus.swear;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class Money {
	
	public static Economy economy = null;
	public static String tag = ChatColor.BLUE + "[" + ChatColor.AQUA + "Swearjar" + ChatColor.BLUE + "]" + ChatColor.YELLOW + " ";
	
	  // Setup Economy (Vault)
		private static boolean setupEconomy() {
			final RegisteredServiceProvider<Economy> economyProvider = LudusSwear.getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			if (economyProvider != null) {
				economy = economyProvider.getProvider();
			}

			return (economy != null);
		}
		
		 public static boolean takemoney(final Player p, final String m){
			 if(economy == null){
				 setupEconomy();
			 }
				final Double amount = Config.getfine();
					final double bal = economy.getBalance(p.getName());
					if (bal < amount) {
						p.sendMessage(tag+"You cant afford to say '"+ m+"'.");
						return false;
					} else {
						if(Config.swearisdisabled()){
						economy.withdrawPlayer(p.getName(), amount);
						 Config.therewasaswear();
						p.sendMessage(tag+"You have been charged "+amount+" "+economy.currencyNamePlural()+ " for saying '"+m+"'.");
						return false;
						}else{
						economy.withdrawPlayer(p.getName(), amount);
						 Config.therewasaswear();
						p.sendMessage(tag+"You have been charged "+amount+" "+economy.currencyNamePlural()+ " for saying '"+m+"'.");
						return true;
						}
					}
			 }

}
