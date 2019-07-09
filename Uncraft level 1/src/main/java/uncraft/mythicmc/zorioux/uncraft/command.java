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



import java.util.HashMap;






public class command implements CommandExecutor {
        private Plugin plugin = Uncraft.getPlugin(Uncraft.class);



    //un-craft command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase("uncraft")){

          if (!(sender instanceof  Player)) {


              plugin.getServer().getConsoleSender().
                      sendMessage(ChatColor.RED + "only players may execute this command you dum dum!");
              return false;
          }

              Player p = (Player) sender;
                if (!(p.hasPermission("mythic.uncraft.use"))){
                    p.sendMessage(ChatColor.DARK_RED + "you don't have permission to use this command");
                    return false;
                }

                  //gets the size of un-craft path in config
                  int toUncraft = plugin.getConfig().getStringList("un-craft").size();


                // if config has no set in it stops code here
                if (toUncraft == 0) {p.sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");
                  plugin.getServer().getConsoleSender().sendMessage(ChatColor.BOLD + " there is no items in config to be un-crafted");
                   return false;
              }


                            // makes sure that player has an item in hand so less memory usage
                            // instead of wasting it scanning air item
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.getMaterial("AIR")) &&
                            p.getInventory().getItemInOffHand().getType().equals(Material.getMaterial("AIR")))
                    {       p.sendMessage(ChatColor.BOLD + "you don't have item in your hand or off hand");
                        return false;
                    }






                        // loop to check if any of the items in the list is in player's hand

                    for (int f = 0 ; f < toUncraft ; f++) {

                        // splits item to be un-crafted from drops
                        String[ ] items = plugin.getConfig().getStringList("un-craft").get(f).split("=");



                    if (!(p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(items[0].toUpperCase()))

                            )
                    ) {
                        if (!(p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase())))){

                        if (f + 1 == toUncraft ){ p.sendMessage(ChatColor.BOLD + "the item in your hand " +
                                "can not be uncrafted");}

                          continue;

                    }}




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
                    /* by this each key is item to be dropped and it's value is how many to be dropped */



                //gets how many free slots in player inventory
              int slots = 0;
                for (int i = 0 ; i <36 ; i++) {

                    if (p.getInventory().getItem(i) == null) {

                        slots++;
                    }
                }




                    //check item in main hand and if there is space in player's inventory
              if(p.getInventory().getItemInMainHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      dropsQuantity <= (slots +1)) {

                            if (p.getInventory().getItemInMainHand().getAmount() > 1 && !(dropsQuantity <= slots)){
                                p.sendMessage(ChatColor.BOLD + "you don't have enough space in your inventory!!!");
                                return false;
                            }
                              //removes item
                            p.getInventory().removeItem(new ItemStack(Material.matchMaterial(items[0]), 1));
                            p.updateInventory();

                    //gives items
                  PlayerInventory inventory = p.getInventory();
                    for (String i : dropsAndQuantities.keySet()) {



                       int quantity = Integer.parseInt(dropsAndQuantities.get(i));
                 inventory.addItem(new ItemStack(Material.valueOf(i.toUpperCase()), quantity ));


                    }
                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");
                  break;
              }          //check item off main hand and if there is space in player's inventory
              else if(p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      dropsQuantity <= (slots +1)) {

                  //removes item
                  if (p.getInventory().getItemInOffHand().getAmount() > 1 && !(dropsQuantity <= slots)){
                      p.sendMessage(ChatColor.BOLD + "you don't have enough space in your inventory!!!");
                      return false;
                  }
                  //removes item
                  p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
                  p.updateInventory();

                  //gives items


                  PlayerInventory inventory = p.getInventory();
                  for (String i : dropsAndQuantities.keySet()) {


                      int quantity = Integer.parseInt(dropsAndQuantities.get(i));
                      inventory.addItem(new ItemStack(Material.valueOf(i.toUpperCase()), quantity ));


                  }

                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");
                  break;
                  //send msg if player's inventory is full
              }else if (dropsQuantity > (slots +1)){ p.sendMessage(ChatColor.BOLD +
                      "you don't have enough space in your inventory!!!");
                  break;

              } else {p.sendMessage(ChatColor.BOLD + "Error , please report this Error to Zorioux");
                  break;
              }}}
                    return false;}}













