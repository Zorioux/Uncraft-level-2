package uncraft.mythicmc.zorioux.uncraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ReloadConfig implements CommandExecutor {


    private Plugin plugin = Uncraft.getPlugin(Uncraft.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



            if (command.getName().equalsIgnoreCase("uncraft-reload")){


                if (sender instanceof  Player){

                    Player p = (Player) sender;

                    if (!(p.hasPermission("mythic.uncraft.reload.use"))) {
                    p.sendMessage(ChatColor.DARK_RED + "you don't have permission to use this Command");
                    return false;
                        }

                    plugin.reloadConfig();
                    p.sendMessage(ChatColor.DARK_GREEN + "successfully reloaded config");

                    return false;
                }


                plugin.reloadConfig();
                plugin.getServer().getConsoleSender().sendMessage
                        (ChatColor.DARK_GREEN + "successfully reloaded config");
                return false;
            }





        return false;
    }
}
