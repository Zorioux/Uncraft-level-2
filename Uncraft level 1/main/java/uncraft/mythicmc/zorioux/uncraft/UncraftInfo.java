package uncraft.mythicmc.zorioux.uncraft;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;
import java.util.HashMap;

public class UncraftInfo implements CommandExecutor {


    private Plugin plugin = Uncraft.getPlugin(Uncraft.class);


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (!(command.getName().equalsIgnoreCase("uncraftinfo"))) return false;

        if(sender instanceof Player){
            Player p = (Player) sender;
            if (!(p.hasPermission("mythic.uncraft.info.use")))
            { p.sendMessage(ChatColor.BOLD + "you do not have permission to execute this command!");
             return false;
        }}

        if (args.length == 0) {
            sender.sendMessage(ChatColor.BOLD + "Usage: /UncraftInfo <item id>");
            return false;
        }

        //gets the size of un-craft path
        int toUncraft = plugin.getConfig().getStringList("un-craft").size();

        for (int f = 0 ; f < toUncraft ; f++) {

            String[] items = plugin.getConfig().getStringList("un-craft").get(f).split("=");


            // gets full string of drops with their quantities
            String allDrops = items[1];
            // splits drops from each other
            String[ ] dropsArray = allDrops.split("&");
            // gets how many drops are there to be dropped
            int dropsQuantity = dropsArray.length;
            // splits drops from their quantities

            HashMap<String, String> dropsAndQuantities = new HashMap<>();


            for (int w = 0 ; w < dropsQuantity  ; w++) {
                String[ ] dropQ = dropsArray[w].split("#");

                dropsAndQuantities.put(dropQ[0], dropQ[1]);
            }

                    if (args[0].equalsIgnoreCase(items[0])) {
                        ArrayList<String> all = new ArrayList<>();

                        for (String i : dropsAndQuantities.keySet()) {
                            int quantity = Integer.parseInt(dropsAndQuantities.get(i));
                            all.add(i + " x" + quantity);

                        }

                        sender.sendMessage(ChatColor.DARK_GREEN + "you can uncraft " + items[0] + " to " + all);
                        break;
                    } else if (f + 1 == toUncraft) {
                        sender.sendMessage(ChatColor.DARK_RED + "False id , does not exist in the config!");
                        break;
                    }


        }




        return false;
    }
}
