package me.jdon.ludus.swear;

import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class LudusSwear extends JavaPlugin implements Listener {
    
    // Variables
    Logger log = Logger.getLogger("Minecraft.LudusCraft");
    public static Economy economy = null;
    public static String tag = ChatColor.BLUE + "[" + ChatColor.AQUA + "Swearjar" + ChatColor.BLUE + "]" + ChatColor.YELLOW + " ";
    public static String linetag = ChatColor.YELLOW + "==================" + tag + "==================";
    public static String line = ChatColor.YELLOW + "==============================================";
    
    public void onEnable() {
        // register events in this class
        getServer().getPluginManager().registerEvents(this, this);
        // set up vault economy
        setupEconomy();
        // set the config
        this.getConfig().options().copyDefaults(true);
        // save the config
        this.saveConfig();
        // set swear command to the class luduscommands
        getCommand("swearjar").setExecutor(this);
        // print to the console that the plugin in enabled
        log.info("[Swearjar] has been Enabled!");
    }
    
    // lowest event priority to stop lagg
    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(final AsyncPlayerChatEvent ev) {
        if (ev.isCancelled())
            return;
        if (ev.getPlayer().hasPermission("sj.exempt"))
            return;
        List<String> swears = getConfig().getStringList("swears");
        List<String> exception = getConfig().getStringList("exception");
        String message = ev.getMessage().toLowerCase();
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
        String me14 = me13.replace("@", "o");
        String me15 = me13.replace("@", "a");
        String final1 = removeDups(me14);
        String final2 = removeDups(me15);
        String final1space = final1.replaceAll(" ", "");
        String me15space = me15.replaceAll(" ", "");
        String final2space = final2.replaceAll(" ", "");
        String me14space = me14.replaceAll(" ", "");
        for (String s : swears) {
            for (String e : exception) {
                if (final1.contains(e))
                    return;
                if (me15.contains(e))
                    return;
                if (me14.contains(e))
                    return;
                if (final2.contains(e))
                    return;
            }
            if (this.getConfig().getBoolean("aggressive")) {
                if (final1space.contains(s)) {
                    if (swearjar(ev.getPlayer(), s) == "poor") {
                        ev.setCancelled(true);
                    }
                }
                // leave duplicatios and remove numbers and special characters
                if (!(final1space.contains(s))) {
                    if (me15space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                // leave dulipcates
                if (!(final1space.contains(s) || me15space.contains(s))) {
                    if (me14space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                if (!(final1space.contains(s) || me15space.contains(s) || me14space.contains(s))) {
                    if (final2space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
            } else {
                if (final1.contains(s)) {
                    if (swearjar(ev.getPlayer(), s) == "poor") {
                        ev.setCancelled(true);
                    }
                }
                // leave duplicatios and remove numbers and special characters
                if (!(final1.contains(s))) {
                    if (me15.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                // leave dulipcates
                if (!(final1.contains(s) || me15.contains(s))) {
                    if (me14.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                if (!(final1.contains(s) || me15.contains(s) || me14.contains(s))) {
                    if (final2.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    
    public String swearjar(final Player p, final String m) {
        final Double amount = this.getConfig().getDouble("Fine");
        final double bal = economy.getBalance(p.getName());
        if (bal < amount) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                
                public void run() {
                    p.sendMessage(tag + "You cant afford to say '" + m + "'.");
                }
            }, 1L);
            return "poor";
        } else {
            if (this.getConfig().getBoolean("DisableSwearing")) {
                economy.withdrawPlayer(p.getName(), amount);
                this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    
                    public void run() {
                        p.sendMessage(tag + "You have been charged " + amount + " " + economy.currencyNamePlural() + " for saying '" + m + "'.");
                    }
                }, 1L);
                return "poor";
            } else {
                economy.withdrawPlayer(p.getName(), amount);
                this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    
                    public void run() {
                        p.sendMessage(tag + "You have been charged " + amount + " " + economy.currencyNamePlural() + " for saying '" + m + "'.");
                    }
                }, 1L);
                return "rich";
            }
        }
    }
    
    // Setup Economy (Vault)
    private boolean setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        
        return (economy != null);
    }
    
    public static String getFinalArg(final String[] args, final int start)
    {
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
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("swearjar")) {
            if (args.length == 0) {
                sender.sendMessage(linetag);
                if (sender.hasPermission("sj.admin")) {
                    sender.sendMessage(ChatColor.YELLOW + "/sj add [String] - " + ChatColor.WHITE + "adds a word to the swear list.");
                    sender.sendMessage(ChatColor.YELLOW + "/sj remove [String] - " + ChatColor.WHITE + " removes a word from the swear list.");
                    sender.sendMessage(ChatColor.YELLOW + "/sj exempt [String] - " + ChatColor.WHITE + "adds a word to the exemption list.");
                    sender.sendMessage(ChatColor.YELLOW + "/sj rexempt [String] - " + ChatColor.WHITE + "removes a word from the exemption list.");
                    sender.sendMessage(ChatColor.YELLOW + "/sj list - " + ChatColor.WHITE + "shows the swear list.");
                    sender.sendMessage(ChatColor.YELLOW + "/sj elist - " + ChatColor.WHITE + "shows the exception list.");
                }
                sender.sendMessage(tag + "Created by luduscraft.co.uk - (nd567)");
                sender.sendMessage(line);
                return true;
            }
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (args.length == 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("swears");
                            sender.sendMessage(linetag);
                            for (String s : swears) {
                                sender.sendMessage("-" + s);
                            }
                            sender.sendMessage(line);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar list");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("elist")) {
                    if (args.length == 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("exception");
                            sender.sendMessage(linetag);
                            for (String s : swears) {
                                sender.sendMessage("-" + s);
                            }
                            sender.sendMessage(line);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar elist");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length > 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("swears");
                            String word = LudusSwear.getFinalArg(args, 1).toLowerCase();
                            for (String s : swears) {
                                if (word.equalsIgnoreCase(s)) {
                                    sender.sendMessage(tag + "'" + word + "' is already on the swear list.");
                                    return true;
                                }
                            }
                            swears.add(word);
                            this.getConfig().set("swears", swears);
                            this.saveConfig();
                            sender.sendMessage(tag + "You added '" + word + "' to the swear list.");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar add [String]");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("exempt")) {
                    if (args.length > 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("exception");
                            String word = LudusSwear.getFinalArg(args, 1).toLowerCase();
                            for (String s : swears) {
                                if (word.equalsIgnoreCase(s)) {
                                    sender.sendMessage(tag + "'" + word + "' is already on the exception list.");
                                    return true;
                                }
                            }
                            swears.add(word);
                            this.getConfig().set("exception", swears);
                            this.saveConfig();
                            sender.sendMessage(tag + "You added '" + word + "' to the exception list.");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar except [String]");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length > 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("swears");
                            String word = LudusSwear.getFinalArg(args, 1).toLowerCase();
                            for (String s : swears) {
                                if (word.equalsIgnoreCase(s)) {
                                    swears.remove(word);
                                    this.getConfig().set("swears", swears);
                                    this.saveConfig();
                                    sender.sendMessage(tag + "You removed '" + word + "' from the swear list.");
                                    return true;
                                }
                            }
                            sender.sendMessage(tag + "'" + word + "' is not on the swear list.");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar remove [String]");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("rexempt")) {
                    if (args.length > 1) {
                        if (sender.hasPermission("sj.admin")) {
                            List<String> swears = this.getConfig().getStringList("exception");
                            String word = LudusSwear.getFinalArg(args, 1).toLowerCase();
                            for (String s : swears) {
                                if (word.equalsIgnoreCase(s)) {
                                    swears.remove(word);
                                    this.getConfig().set("exception", swears);
                                    this.saveConfig();
                                    sender.sendMessage(tag + "You removed '" + word + "' from the exception list.");
                                    return true;
                                }
                            }
                            sender.sendMessage(tag + "'" + word + "' is not on the exception list.");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have permission");
                            return true;
                        }
                    } else {
                        sender.sendMessage("/Swearjar exceptremove [String]");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static String removeDups(String s)
    {
        if (s.length() <= 1)
            return s;
        if (s.substring(1, 2).equals(s.substring(0, 1)))
            return removeDups(s.substring(1));
        else
            return s.substring(0, 1) + removeDups(s.substring(1));
    }
    
    @EventHandler
    public void commands(PlayerCommandPreprocessEvent ev) {
        if (this.getConfig().getBoolean("commandChecking") == false)
            return;
        if (ev.isCancelled())
            return;
        if (ev.getPlayer().hasPermission("sj.exempt"))
            return;
        List<String> swears = getConfig().getStringList("swears");
        List<String> exception = getConfig().getStringList("exception");
        String message = ev.getMessage().toLowerCase();
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
        String me14 = me13.replace("@", "o");
        String me15 = me13.replace("@", "a");
        String final1 = removeDups(me14);
        String final2 = removeDups(me15);
        String final1space = final1.replaceAll(" ", "");
        String me15space = me15.replaceAll(" ", "");
        String final2space = final2.replaceAll(" ", "");
        String me14space = me14.replaceAll(" ", "");
        for (String s : swears) {
            for (String e : exception) {
                if (final1.contains(e))
                    return;
                if (me15.contains(e))
                    return;
                if (me14.contains(e))
                    return;
                if (final2.contains(e))
                    return;
            }
            if (this.getConfig().getBoolean("aggressive")) {
                if (final1space.contains(s)) {
                    if (swearjar(ev.getPlayer(), s) == "poor") {
                        ev.setCancelled(true);
                    }
                }
                // leave duplicatios and remove numbers and special characters
                if (!(final1space.contains(s))) {
                    if (me15space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                // leave dulipcates
                if (!(final1space.contains(s) || me15space.contains(s))) {
                    if (me14space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                if (!(final1space.contains(s) || me15space.contains(s) || me14space.contains(s))) {
                    if (final2space.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
            } else {
                if (final1.contains(s)) {
                    if (swearjar(ev.getPlayer(), s) == "poor") {
                        ev.setCancelled(true);
                    }
                }
                // leave duplicatios and remove numbers and special characters
                if (!(final1.contains(s))) {
                    if (me15.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                // leave dulipcates
                if (!(final1.contains(s) || me15.contains(s))) {
                    if (me14.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
                if (!(final1.contains(s) || me15.contains(s) || me14.contains(s))) {
                    if (final2.contains(s)) {
                        if (swearjar(ev.getPlayer(), s) == "poor") {
                            ev.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    
}