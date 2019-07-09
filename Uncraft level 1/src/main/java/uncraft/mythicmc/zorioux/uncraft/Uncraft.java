package uncraft.mythicmc.zorioux.uncraft;



import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Uncraft extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("uncraft").setExecutor(new command());
        this.getCommand("uncraft-reload").setExecutor(new ReloadConfig());


        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "uncrafter launched successfully !");
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    }

