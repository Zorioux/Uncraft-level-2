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
    private Plugin plugin = Uncraft.getPlugin(Uncraft.class);



    //un-craft command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase("uncraft")){
          if (sender instanceof  Player) {
              Player p = (Player) sender;

                        int z = plugin.getConfig().getStringList("Drops").size();

              int x = 0;
                for (int i = 0 ; i <36 ; i++) {

                    if (p.getInventory().getItem(i) == null) {

                        x++;
                    }
                }




                    //check saddle in main hand and if there is space in player's inventory
              if(p.getInventory().getItemInMainHand().getType().equals(SADDLE) &&  z <= (x)) {
                  //removes saddle
                 p.getInventory().setItemInMainHand(null);


                    //gives items
                  PlayerInventory inventory = p.getInventory();
                    for (int y = 0; y < z ; y++) {

                        String drop = plugin.getConfig().getStringList("Drops").get(y);
                        int quantity = plugin.getConfig().getIntegerList("Quantity").get(y);

                 inventory.addItem(new ItemStack(Material.valueOf(drop.toUpperCase()), quantity));
                    }
                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

              }          //check saddle off main hand and if there is space in player's inventory
              else if( p.getInventory().getItemInOffHand().getType().equals(SADDLE) &&  z <= (x)) {
                  //removes saddle
                  p.getInventory().setItemInOffHand(null);


                  //gives items
                  PlayerInventory inventory = p.getInventory();

                  for (int y = 0; y < z ; y++) {

                      String drop = plugin.getConfig().getStringList("Drops").get(y);
                      int quantity = plugin.getConfig().getIntegerList("Quantity").get(y);

                      inventory.addItem(new ItemStack(Material.valueOf(drop.toUpperCase()), quantity));
                  }

                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

                  //send msg if player's inventory is full
              }else if (z > (x)){ p.sendMessage(ChatColor.BOLD +
                      "you don't have enough space in your inventory!!!");

                  //if all failed , there is no saddle in player hands outputs msg
              } else if(!p.getInventory().getItemInOffHand().getType().equals(SADDLE) &&
                      ! p.getInventory().getItemInMainHand().getType().equals(SADDLE))
              {p.sendMessage(ChatColor.BOLD  + "you don't have saddle in your hand");}
              else {p.sendMessage(ChatColor.BOLD + "Error");}


          } else {plugin.getServer().getConsoleSender().
                  sendMessage(ChatColor.RED + "only players can execute this command");}

        }



        return false;}}













