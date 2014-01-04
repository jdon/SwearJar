package me.jdon.ludus.swear;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LudusSwear extends JavaPlugin implements Listener{

	//Variables

		Logger log = Logger.getLogger("Minecraft.swearjar");
		private static Plugin plugin;
	public void onEnable() {
        plugin = this;
		//register events in this class
		getServer().getPluginManager().registerEvents(this, this);
		Config.enable();
		//set swear command to the class luduscommands
		getCommand("swearjar").setExecutor(new Commands());
		// print to the console that the plugin in enabled
		log.info("[Swearjar] has been Enabled!");
	}
    
	//To access the plugin variable from other classes
    public static Plugin getPlugin() {
        return plugin;
    }
    
	// lowest event priority to stop lagg
	 @EventHandler(priority = EventPriority.LOWEST)
	 public void chat(final AsyncPlayerChatEvent  ev) {
		 if(ev.isCancelled())return;
		 if(ev.getPlayer().hasPermission("sj.exempt"))return;
			List<String> swearlist = Worker.isswear(ev.getMessage());
			if(swearlist != null){
			for(String swear : swearlist){
				// message contains a swear
				if(!(Money.takemoney(ev.getPlayer(),swear))){
					// Could not take money so cancel event
					ev.setCancelled(true);
				}
			}
			}
	 }
	 
		
		@EventHandler
		public void commands(PlayerCommandPreprocessEvent  ev){
			if(ev.isCancelled())return;
			 if(ev.getPlayer().hasPermission("sj.exempt"))return;
			if(!(Config.commandenabled()))return;
			String[] args = ev.getMessage().replace("/", "").split(" "); 
			String command = args[0];
			if(command.equalsIgnoreCase("swearjar") || command.equalsIgnoreCase("sj")){
				// dont run any checks on it
				return;
			}
			List<String> swearlist = Worker.isswear(ev.getMessage());
			if(swearlist != null){
			for(String swear : swearlist){
				// message contains a swear
				if(!(Money.takemoney(ev.getPlayer(),swear))){
					// Could not take money so cancel event
					ev.setCancelled(true);
				}
			}
			}
		}
}