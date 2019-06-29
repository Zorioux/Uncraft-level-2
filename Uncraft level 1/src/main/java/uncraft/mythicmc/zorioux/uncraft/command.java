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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





public class command implements CommandExecutor {
        private Plugin plugin = Uncraft.getPlugin(Uncraft.class);



    //un-craft command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase("uncraft")){
          if (sender instanceof  Player) {
              Player p = (Player) sender;

                    if (!(p.getInventory().getItemInMainHand() == null) &&
                            !(p.getInventory().getItemInOffHand() == null))
                    {
                            //gets the size of un-craft path in config
                        int toUncraft = plugin.getConfig().getList("un-craft").size();
                        p.sendMessage("uncraft list size is :  " + toUncraft);
                        //
                    if (toUncraft > 0) {
                        // loop to check if any of the items in the list is in player's hand
                                int o = 0;
                    for (int f = 0 ; f < toUncraft ; f++) {

                        // splits item to be un-crafted from drops
                        String[ ] items = plugin.getConfig().getStringList("un-craft").get(f).split("=");



                    if (p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(items[0].toUpperCase())) ||
                            p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase()))
                    )  {
                            o++;

                       // gets full string of drops with their quantities
                    String allDrops = items[1];
                       // splits drops from each other
                    String[ ] dropsArray = allDrops.split("&");
                      // gets how many drops are there to be dropped
                    int dropsQuantity = dropsArray.length;
                     // splits drops from their quantities

                List<String> dropsAndQuantities = new ArrayList<>();


                    for (int w = 0 ; w < dropsQuantity ; w++) {
                        String[ ] dropQ = dropsArray[w].split("#");
                        dropsAndQuantities.add(dropQ[w]);
                        dropsAndQuantities.add(dropQ[w+1]);

                    }
                    /* now by this every odd number 0 2 4 ... in dropz[#]  is an item to be dropped
                     * and every odd number 1 3 5 ... in dropz[#]  is item's quantity to drop */



                //gets how many free slots in player inventory
              int slots = 0;
                for (int i = 0 ; i <36 ; i++) {

                    if (p.getInventory().getItem(i) == null) {

                        slots++;
                    }
                }




                    //check saddle in main hand and if there is space in player's inventory
              if(p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      dropsQuantity <= (slots -1)) {
                  //removes saddle
                 p.getInventory().setItemInMainHand(null);


                    //gives items
                  PlayerInventory inventory = p.getInventory();
                    for (int y = 0; y < dropsQuantity ; y++) {

                        String drop = dropsAndQuantities.get(y + y);
                        String drop2 = dropsAndQuantities.get(y + y + 1);
                        int quantity = Integer.parseInt(drop2);

                 inventory.addItem(new ItemStack(Material.valueOf(drop.toUpperCase()), quantity));
                    }
                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

              }          //check saddle off main hand and if there is space in player's inventory
              else if(p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      dropsQuantity <= (slots -1)) {
                  //removes saddle
                  p.getInventory().setItemInOffHand(null);


                  //gives items
                  PlayerInventory inventory = p.getInventory();

                  for (int y = 0; y < dropsQuantity ; y++) {

                      String drop = dropsAndQuantities.get(y + y);
                      String drop2 = dropsAndQuantities.get(y + y + 1);
                      int quantity = Integer.parseInt(drop2);

                      inventory.addItem(new ItemStack(Material.valueOf(drop.toUpperCase()), quantity));
                  }

                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");

                  //send msg if player's inventory is full
              }else if (dropsQuantity > (slots -1)){ p.sendMessage(ChatColor.BOLD +
                      "you don't have enough space in your inventory!!!");

                  //if all failed , there is no saddle in player hands outputs msg
              } else if(!p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      !p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(items[0].toUpperCase())))
              {p.sendMessage(ChatColor.BOLD  + "you don't have saddle in your hand");}


              else {p.sendMessage(ChatColor.BOLD + "Error , please report this Error to Zorioux");}


          }else if (o == 0 && f == toUncraft){
                        p.sendMessage(ChatColor.BOLD + "this items in your hand or off hand can not be un-crafted");
                    }
                    }
                    }else if (toUncraft == 0) {p.sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");
                    plugin.getServer().getConsoleSender().sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");}
          } else if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) && p.getInventory().getItemInOffHand().getType().equals(Material.AIR)){
                        p.sendMessage(ChatColor.BOLD + " you don't have any item in your hand");
                    }

        } else {plugin.getServer().getConsoleSender().
                  sendMessage(ChatColor.RED + "only players may execute this command you dum dum!");}

        }



        return false;}}













