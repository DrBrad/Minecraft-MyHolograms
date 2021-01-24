package unet.Holograms;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static unet.Holograms.Main.*;
import static unet.Holograms.LoadHolograms.*;

public class HologramCommands implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args){
        if(commandSender instanceof Player){
            if(args.length > 0){
                String cmd = args[0].toLowerCase();
                switch(cmd){
                    case "help":
                        return help(((Player) commandSender), args);

                    case "?":
                        return help(((Player) commandSender), args);

                    case "create":
                        return create(((Player) commandSender), args);

                    case "text":
                        return text(((Player) commandSender), args);

                    case "remove":
                        return remove(((Player) commandSender), args);

                    case "version":
                        commandSender.sendMessage("§7MyFaction version §c"+plugin.getDescription().getVersion()+"§7 by DrBrad.");
                        return true;
                }
            }else{
                commandSender.sendMessage("§7Type §c/h help§7 to see a list of commands.");
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args){
        if(commandSender instanceof Player){
            if(args.length > 0){
                String cmd = args[0].toLowerCase();
                ArrayList<String> tabComplete = new ArrayList<>();

                if(args.length > 1){
                    switch(cmd){
                        case "create":
                            if(args.length > 2){
                                tabComplete.add("DISPLAY NAME");
                            }else{
                                tabComplete.add("KEY");
                            }
                            break;

                        case "text":
                            if(args.length > 2){
                                tabComplete.add("DISPLAY NAME");
                                Hologram hologram = getHologram(args[1]);
                                if(hologram != null){
                                    tabComplete.add(hologram.getText());
                                }
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalHolograms() > 0){
                                    tabComplete.addAll(getHolograms());
                                }
                            }
                            break;

                        case "remove":
                            tabComplete.add("KEY");
                            if(getTotalHolograms() > 0){
                                tabComplete.addAll(getHolograms());
                            }
                            break;
                    }
                }else{
                    tabComplete.add("help");
                    tabComplete.add("?");
                    tabComplete.add("create");
                    tabComplete.add("text");
                    tabComplete.add("remove");
                    tabComplete.add("version");
                }

                return tabComplete;
            }
        }

        return null;
    }

    //PERFECT
    private boolean help(Player player, String[] args){
        if(args.length > 1){
            if(args[1].equals("2")){
                player.sendMessage("§c------- §fHologram commands (1/1) §c-------");
                player.sendMessage("§c/h leggings: §7Set the leggings for the FakeMob.");
                player.sendMessage("§c/h boots: §7Set the boots for the FakeMob.");
                player.sendMessage("§c/h mainhand: §7Set the main-hand for the FakeMob.");
                player.sendMessage("§c/h offhand: §7Set the off-hand for the FakeMob..");
                player.sendMessage("§c/fm cmd: §7Set FakeMob to exicute command on interaction.");
                player.sendMessage("§c/fm profession: §7Set FakeMob Villager type.");
                player.sendMessage("§c/fm version: §7Get the version of this plugin.");
                return true;
            }
        }

        player.sendMessage("§c------- §fHologram commands (1/1) §c-------");
        player.sendMessage("§c/h create: §7Create a hologram.");
        player.sendMessage("§c/h text: §7Remove a hologram.");
        player.sendMessage("§c/h remove: §7Remove a hologram.");
        player.sendMessage("§c/h version: §7Get the version of this plugin.");
        player.sendMessage("§c/fm create: §7Create FakeMob.");
        player.sendMessage("§c/fm remove: §7Create FakeMob.");
        player.sendMessage("§c/fm name: §7Set the display name for the FakeMob.");
        player.sendMessage("§c/fm helmet: §7Set the helmet for the FakeMob.");
        player.sendMessage("§c/fm chestplate: §7Set the chestplate for the FakeMob.");
        return true;
    }

    private boolean create(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 2){
                String key = args[1];
                if(key.length() < 13 && key.length() > 1){
                    if(getHologram(key) == null){
                        String builder = "";
                        for(int i = 2; i < args.length; i++){
                            builder += args[i]+" ";
                        }

                        builder = builder.replaceAll("&", "§");
                        Location location = player.getLocation();
                        location.setY(location.getY()+2);
                        if(setHologram(args[1], new Hologram(location, builder.substring(0, builder.length()-1)))){
                            player.sendMessage("§7You have created a hologram!");
                        }else{
                            player.sendMessage("§cFailed to save hologram.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cHologram with that name already exists.");
                    }
                }else{
                    player.sendMessage("§cHologram identifier exceeds character limit.");
                }
            }else{
                player.sendMessage("§cPlease specify the identifier and text for your hologram.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to create a Hologram!");
        }
        return false;
    }

    private boolean text(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 2){
                String key = args[1];
                Hologram hologram = getHologram(key);
                if(hologram != null){
                    String builder = "";
                    for(int i = 2; i < args.length; i++){
                        builder += args[i]+" ";
                    }

                    builder = builder.replaceAll("&", "§");
                    hologram.setText(builder.substring(0, builder.length()-1));
                    if(setHologram(key, hologram)){
                        hologram.reload(player);
                        player.sendMessage("§7You have changed hologram text!");
                    }else{
                        player.sendMessage("§cFailed to save hologram.");
                    }

                    return true;
                }else{
                    player.sendMessage("§cHologram specified doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the identifier and text for your hologram.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to change Hologram text!");
        }
        return false;
    }

    private boolean remove(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                if(getHologram(key) != null){
                    removeHologram(key);
                    player.sendMessage("§7You have removed the hologram §c"+key+"§7.");
                }else{
                    player.sendMessage("§cThe hologram you wish to remove doesn't exist.");
                }
                return true;
            }else{
                player.sendMessage("§cPlease specify the hologram you wish to remove.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to remove a Hologram!");
        }
        return false;
    }
}
