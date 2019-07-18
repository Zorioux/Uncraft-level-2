package uncraft.mythicmc.zorioux.uncraft;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;






public class Command implements CommandExecutor {
        private Plugin plugin = Uncraft.getPlugin(Uncraft.class);





    //un-craft Command
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {


        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = time.format(myFormatObj);


        if (command.getName().equalsIgnoreCase("uncraft")){

            if(args.length == 0) {


          if (!(sender instanceof  Player)) {


              plugin.getServer().getConsoleSender().
                      sendMessage(ChatColor.RED + "only players may execute this Command you dum dum!");
              return false;
          }

              Player p = (Player) sender;


                if (!(p.hasPermission("mythic.uncraft.use"))){
                    p.sendMessage(ChatColor.DARK_RED + "you don't have permission to use this Command");
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
                            if (p.getInventory().getItemInMainHand().getDurability() != 0){
                                p.sendMessage(ChatColor.BOLD + "the item in your hand has been used, uncrafting " +
                                        "cancelled" + ChatColor.AQUA + "you can't trick me Mwuahahaha");
                                return false;
                            }
                              //removes item
                            p.getInventory().removeItem(new ItemStack(Material.matchMaterial(items[0]), 1));
                            p.updateInventory();

                                //needed for logs
                              ArrayList<String> all = new ArrayList<>();

                    //gives items
                  PlayerInventory inventory = p.getInventory();
                    for (String i : dropsAndQuantities.keySet()) {



                       int quantity = Integer.parseInt(dropsAndQuantities.get(i));
                 inventory.addItem(new ItemStack(Material.valueOf(i.toUpperCase()), quantity ));
                        all.add(i + " x" + quantity);

                    }
                        String world = p.getWorld().getName();
                        int cx = p.getLocation().getBlockX();
                        int cy = p.getLocation().getBlockY();
                        int cz = p.getLocation().getBlockZ();

                  final String SEPARATOR = System.getProperty("file.separator");

                     try{ FileWriter myWriter = new FileWriter("plugins" + SEPARATOR + "Uncraft" + SEPARATOR + "Logs.log",true);
                         myWriter.write(  "\r\n" + formattedDate + "  ::  "+ "Player " + p.getName() + " Uncrafted " + items[0] + " to "
                              + all + " :: at " + cx + " " + cy + " " + cz + " in " + world + " Using main hand");

                              myWriter.close();

                             }catch (IOException e) {
                               plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED +
                                         "Uncrafter output: Could not write into Logs.txt");

                                  }
                              plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN +
                                   "Player " + p.getName() + " Uncrafted " + items[0] + " to "
                                       + all + " :: at " + cx + " " + cy + " " + cz + " in " + world + " Using main hand");


                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");








                  break;
              }






                     //check item off main hand and if there is space in player's inventory
              else if(p.getInventory().getItemInOffHand().getType().equals(Material.valueOf(items[0].toUpperCase())) &&
                      dropsQuantity <= (slots +1)) {

                  //removes item
                  if (p.getInventory().getItemInOffHand().getAmount() > 1 && !(dropsQuantity <= slots)){
                      p.sendMessage(ChatColor.BOLD + "you don't have enough space in your inventory!!!");
                      return false;
                  }

                  if (p.getInventory().getItemInOffHand().getDurability() != 0){
                      p.sendMessage(ChatColor.BOLD + "the item in your hand has been used, uncrafting " +
                              "cancelled" + ChatColor.AQUA + "you can't trick me Mwuahahaha");
                      return false;
                  }
                  //removes item
                  p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
                  p.updateInventory();

                  //gives items

                  ArrayList<String> all = new ArrayList<>();
                  PlayerInventory inventory = p.getInventory();
                  for (String i : dropsAndQuantities.keySet()) {


                      int quantity = Integer.parseInt(dropsAndQuantities.get(i));
                      inventory.addItem(new ItemStack(Material.valueOf(i.toUpperCase()), quantity ));

                      all.add(i + " x" + quantity);




                  }

                  String world = p.getWorld().getName();
                  int cx = p.getLocation().getBlockX();
                  int cy = p.getLocation().getBlockY();
                  int cz = p.getLocation().getBlockZ();

                  final String SEPARATOR = System.getProperty("file.separator");

                  try{ FileWriter myWriter = new FileWriter("plugins" + SEPARATOR + "Uncraft" + SEPARATOR + "Logs.log",true);
                      myWriter.write("\r\n" + formattedDate + "  ::  " + "Player " + p.getName() + " Uncrafted " + items[0] + " to "
                              + all + " :: at " + cx + " " + cy + " " + cz + " in " + world + " Using off hand");

                      myWriter.close();

                  }catch (IOException e) {
                      plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED +
                              "Uncrafter output: Could not write into Logs.txt");

                  }
                  plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN +
                          "Player " + p.getName() + " Uncrafted " + items[0] + " to "
                          + all + " :: at " + cx + " " + cy + " " + cz + " in " + world + " Using off hand");


                  p.sendMessage(ChatColor.DARK_GREEN+ "uncrafted successfully");
                  break;



                  //send msg if player's inventory is full
              }else if (dropsQuantity > (slots +1)){ p.sendMessage(ChatColor.BOLD +
                      "you don't have enough space in your inventory!!!");
                  break;

              } else {p.sendMessage(ChatColor.BOLD + "Error , please report this Error to Zorioux");
                  break;
              }}} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                sender.sendMessage(
                        ChatColor.DARK_GREEN + "/uncraft or /uc: " + ChatColor.AQUA + "used to uncraft item in your hand or off hand , main hand will always override off hand" + "\n"

                                + ChatColor.DARK_GREEN + "/uncraftlist or /ucl: " + ChatColor.AQUA + "used to list all items that you can uncraft" + "\n"

                                + ChatColor.DARK_GREEN + "/Uncraftinfo <item id> or /uci <item id> " + ChatColor.AQUA + "used to give what item can be uncrafted to" + "\n"

                                + ChatColor.DARK_GREEN + "/uncraft-reload or /ucr: " + ChatColor.AQUA + "staff based command , reloads config" + "\n"

                                + ChatColor.DARK_GREEN + "/uncraft help or /uc h: " + ChatColor.AQUA + "Do I need to explain this ?"




                );

            } else if (!(args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help"))){
                sender.sendMessage(ChatColor.DARK_RED + "Usage /uc h or /uncraft help");
            }
        }
                    return false;}}













