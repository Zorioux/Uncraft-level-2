package uncraft.mythicmc.zorioux.uncraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;


public class ListCommand implements CommandExecutor {

    private Plugin plugin = Uncraft.getPlugin(Uncraft.class);
        @Override
         public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("uncraftlist")){
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!(p.hasPermission("mythic.uncraft.list.use"))){
                    p.sendMessage(ChatColor.DARK_RED + "you don't have permission to use this Command!");
                    return false;
                }
            }

            //gets the size of un-craft path in config
            int toUncraft = plugin.getConfig().getStringList("un-craft").size();


            // if config has no set in it stops code here
            if (toUncraft == 0) { sender.sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");
                return false;
            }

            ArrayList<String> list = new ArrayList<>();

            for (int f = 0 ; f < toUncraft ; f++){

                // splits item to be un-crafted from drops
                String[] items = plugin.getConfig().getStringList("un-craft").get(f).split("=");
                        list.add(items[0]);
                 }

                    sender.sendMessage(ChatColor.AQUA + "items can be uncrafted: " + list);

        }

        return false;
    }
}
