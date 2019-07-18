package uncraft.mythicmc.zorioux.uncraft;



import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Uncraft extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("uncraft").setExecutor(new Command());
        this.getCommand("uncraft-reload").setExecutor(new ReloadConfig());
        this.getCommand("uncraftlist").setExecutor(new ListCommand());
        this.getCommand("uncraftinfo").setExecutor(new UncraftInfo());


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        fileCreator();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "uncrafter launched successfully !");
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void fileCreator(){

        final String SEPARATOR = System.getProperty("file.separator");

        File file = new File("plugins" + SEPARATOR + "Uncraft" + SEPARATOR + "Logs.log");

        try
        {
            if (file.createNewFile()) getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Uncrafter output: Logs.log File has been successfully created");
            else getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "uncrafter output: Could not create Logs.log File \"file already exist\"");

        }catch (IOException e){
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "uncrafter output: Error occurred while creating Logs.txt");
        }


    }


    }

