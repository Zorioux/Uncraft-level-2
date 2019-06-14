package uncraft.mythicmc.zorioux.uncraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;


import static org.bukkit.Material.SADDLE;





public class command implements CommandExecutor {


        private Plugin plugin;

    public command(Plugin plugin) {
        this.plugin = plugin;

    }


    //un-craft command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase("uncraft")){
          if (sender instanceof  Player) {
              Player p = (Player) sender;


                    //check saddle in main hand and if there is space in player's inventory
              if(p.getInventory().getItemInMainHand().getType().equals(SADDLE) &&  p.getInventory().firstEmpty() > -1) {
                  //removes saddle
                 p.getInventory().setItemInMainHand(null);


                    //gives items
                  PlayerInventory inventory = p.getInventory();

                 inventory.addItem(new ItemStack(Material.LEATHER, 3));
                  inventory.addItem(new ItemStack(Material.IRON_INGOT, 1));

                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

              }          //check saddle off main hand and if there is space in player's inventory
              else if( p.getInventory().getItemInOffHand().getType().equals(SADDLE) &&  p.getInventory().firstEmpty() > -1) {
                  //removes saddle
                  p.getInventory().setItemInOffHand(null);


                  //gives items
                  PlayerInventory inventory = p.getInventory();

                  inventory.addItem(new ItemStack(Material.LEATHER, 3));
                  inventory.addItem(new ItemStack(Material.IRON_INGOT, 1));

                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

                  //send msg if player's inventory is full
              }else if (p.getInventory().firstEmpty() == -1){ p.sendMessage(ChatColor.BOLD +
                      "you don't have enough space in your inventory!!!");

                  //if all failed , there is no saddle in player hands outputs msg
              } else if(!p.getInventory().getItemInOffHand().getType().equals(SADDLE) && ! p.getInventory().getItemInMainHand().getType().equals(SADDLE))
              {p.sendMessage(ChatColor.BOLD  + "you don't have saddle in your hand");}
              else {p.sendMessage(ChatColor.BOLD + "Error");}
          }
        }






        return false;
    }
}
