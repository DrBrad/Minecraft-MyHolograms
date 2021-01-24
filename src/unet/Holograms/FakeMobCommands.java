package unet.Holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static unet.Holograms.LoadFakeMobs.*;
import static unet.Holograms.Main.*;
import static unet.Holograms.MobResolver.*;

public class FakeMobCommands implements CommandExecutor, TabExecutor {

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

                    case "remove":
                        return remove(((Player) commandSender), args);

                    case "name":
                        return setName(((Player) commandSender), args);

                    case "helmet":
                        return setHelmet(((Player) commandSender), args);

                    case "chestplate":
                        return setChestplate(((Player) commandSender), args);

                    case "leggings":
                        return setLeggings(((Player) commandSender), args);

                    case "boots":
                        return setBoots(((Player) commandSender), args);

                    case "mainhand":
                        return setRightHand(((Player) commandSender), args);

                    case "offhand":
                        return setLeftHand(((Player) commandSender), args);

                    case "cmd":
                        return setCommand(((Player) commandSender), args);

                    case "profession":
                        return setProfession(((Player) commandSender), args);

                    case "version":
                        commandSender.sendMessage("§7MyFaction version §c"+plugin.getDescription().getVersion()+"§7 by DrBrad.");
                        return true;
                }
            }else{
                commandSender.sendMessage("§7Type §c/fm help§7 to see a list of commands.");
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
                        case "create": //FAKEMOB
                            if(args.length > 2){
                                Collections.addAll(tabComplete, getMobs());

                            }else{
                                tabComplete.add("KEY");
                            }
                            break;

                        case "name": //FAKEMOB
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add("DISPLAY NAME");

                                FakeMob fakeMob = getFakeMob(args[1]);
                                if(fakeMob != null){
                                    tabComplete.add(fakeMob.getName());
                                }

                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "helmet": //FAKEMOB
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add(Material.LEATHER_HELMET.name());
                                tabComplete.add(Material.IRON_HELMET.name());
                                tabComplete.add(Material.GOLDEN_HELMET.name());
                                tabComplete.add(Material.DIAMOND_HELMET.name());
                                tabComplete.add(Material.NETHERITE_HELMET.name());
                                tabComplete.add(Material.TURTLE_HELMET.name());
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "chestplate": //FAKEMOB
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add(Material.LEATHER_CHESTPLATE.name());
                                tabComplete.add(Material.IRON_CHESTPLATE.name());
                                tabComplete.add(Material.GOLDEN_CHESTPLATE.name());
                                tabComplete.add(Material.DIAMOND_CHESTPLATE.name());
                                tabComplete.add(Material.NETHERITE_CHESTPLATE.name());
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "leggings": //FAKEMOB
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add(Material.LEATHER_LEGGINGS.name());
                                tabComplete.add(Material.IRON_LEGGINGS.name());
                                tabComplete.add(Material.GOLDEN_LEGGINGS.name());
                                tabComplete.add(Material.DIAMOND_LEGGINGS.name());
                                tabComplete.add(Material.NETHERITE_LEGGINGS.name());
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "boots": //FAKEMOB
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add(Material.LEATHER_BOOTS.name());
                                tabComplete.add(Material.IRON_BOOTS.name());
                                tabComplete.add(Material.GOLDEN_BOOTS.name());
                                tabComplete.add(Material.DIAMOND_BOOTS.name());
                                tabComplete.add(Material.NETHERITE_BOOTS.name());
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "mainhand": //FAKEMOB
                            if(args.length > 2){
                                for(Material material : Material.values()){
                                    tabComplete.add(material.name());
                                }
                                tabComplete.add("~");
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "offhand": //FAKEMOB
                            if(args.length > 2){
                                for(Material material : Material.values()){
                                    tabComplete.add(material.name());
                                }
                                tabComplete.add("~");
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "cmd":
                            if(args.length > 2){
                                tabComplete.add("~");
                                tabComplete.add("gamemode creative");
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "profession":
                            if(args.length > 2){
                                tabComplete.add("~");
                                for(Villager.Profession profession : Villager.Profession.values()){
                                    tabComplete.add(profession.name());
                                }
                            }else{
                                tabComplete.add("KEY");
                                if(getTotalFakeMobs() > 0){
                                    tabComplete.addAll(getFakeMobs());
                                }
                            }
                            break;

                        case "remove": //FAKEMOB
                            tabComplete.add("KEY");
                            if(getTotalFakeMobs() > 0){
                                tabComplete.addAll(getFakeMobs());
                            }
                            break;
                    }
                }else{
                    tabComplete.add("help");
                    tabComplete.add("?");
                    tabComplete.add("create");
                    tabComplete.add("remove");
                    tabComplete.add("name");
                    tabComplete.add("helmet");
                    tabComplete.add("chestplate");
                    tabComplete.add("leggings");
                    tabComplete.add("boots");
                    tabComplete.add("mainhand");
                    tabComplete.add("offhand");
                    tabComplete.add("cmd");
                    tabComplete.add("profession");
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
                    if(getFakeMob(key) == null){
                        String[] mobs = getMobs();
                        ArrayList<String> mobList = new ArrayList<>();
                        Collections.addAll(mobList, mobs);

                        String mob = args[2];

                        if(mobList.contains(mob)){
                            if(setFakeMob(new FakeMob(key, player.getLocation(), mob))){
                                player.sendMessage("§7You have created a FakeMob!");
                            }else{
                                player.sendMessage("§cFailed to create FakeMob.");
                            }

                            return true;
                        }else{
                            player.sendMessage("§cMob name not recognized.");
                        }
                    }else{
                        player.sendMessage("§cFakeMob with this key already exists.");
                    }
                }else{
                    player.sendMessage("§cFakeMob key exceeds character limit.");
                }
            }else{
                player.sendMessage("§cPlease enter a key and mob type for FakeMob!");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to create a FakeMob!");
        }
        return false;
    }

    private boolean remove(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                if(getFakeMob(key) != null){
                    removeFakeMob(key);
                    player.sendMessage("§7You have removed the FakeMob §c"+key+"§7.");
                }else{
                    player.sendMessage("§cThe FakeMob you wish to remove doesn't exist.");
                }
                return true;
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to remove.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to remove a FakeMob!");
        }
        return false;
    }

    private boolean setName(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String name = "";
                        for(int i = 2; i < args.length; i++){
                            name += args[i]+" ";
                        }

                        name = name.replaceAll("&", "§");
                        name = name.substring(0, name.length()-1);
                        if(name.equals("~")){
                            fakeMob.setName(null);
                        }else{
                            fakeMob.setName(name);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob name for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to name FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a name you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set name.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob name!");
        }
        return false;
    }

    private boolean setHelmet(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setHelmet(null);
                        }else{
                            Material helmet = Material.valueOf(mat);
                            fakeMob.setHelmet(helmet);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the helmet for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the helmet FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a helmet you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the helmet for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob helmet!");
        }
        return false;
    }

    private boolean setChestplate(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setChestplate(null);
                        }else{
                            Material chestplate = Material.valueOf(mat);
                            fakeMob.setChestplate(chestplate);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the chestplate for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the chestplate FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a chestplate you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the chestplate for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob chestplate!");
        }
        return false;
    }

    private boolean setLeggings(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setLeggings(null);
                        }else{
                            Material leggings = Material.valueOf(mat);
                            fakeMob.setLeggings(leggings);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the leggings for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the leggings FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a leggings you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the leggings for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob leggings!");
        }
        return false;
    }

    private boolean setBoots(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setBoots(null);
                        }else{
                            Material boots = Material.valueOf(mat);
                            fakeMob.setBoots(boots);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the boots for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the boots FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a boots you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the boots for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob boots!");
        }
        return false;
    }

    private boolean setRightHand(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setRightHand(null);
                        }else{
                            Material rightHand = Material.valueOf(mat);
                            fakeMob.setRightHand(rightHand);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the right hand for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the right hand FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a right hand you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the right hand for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob right hand!");
        }
        return false;
    }

    private boolean setLeftHand(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String mat = args[2];
                        if(mat.equals("~")){
                            fakeMob.setLeftHand(null);
                        }else{
                            Material leftHand = Material.valueOf(mat);
                            fakeMob.setLeftHand(leftHand);
                        }

                        if(setFakeMob(fakeMob)){
                            fakeMob.reload(player);
                            player.sendMessage("§7You have set FakeMob the left hand for §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set the left hand FakeMob.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a left hand you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set the left hand for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob left hand!");
        }
        return false;
    }

    private boolean setCommand(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        String cmd = "";
                        for(int i = 2; i < args.length; i++){
                            cmd += args[i]+" ";
                        }

                        cmd = cmd.substring(0, cmd.length()-1);
                        if(cmd.equals("~")){
                            fakeMob.setCommand(null);
                        }else{
                            fakeMob.setCommand(cmd);
                        }

                        if(setFakeMob(fakeMob)){
                            player.sendMessage("§7You have set a command  for FakeMob §c"+key+"§7!");
                        }else{
                            player.sendMessage("§cFailed to set FakeMob command.");
                        }
                        return true;
                    }else{
                        player.sendMessage("§cPlease specify a command you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set a command for.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob command!");
        }
        return false;
    }

    private boolean setProfession(Player player, String args[]){
        if(player.isOp()){
            if(args.length > 1){
                String key = args[1];
                FakeMob fakeMob = getFakeMob(key);
                if(fakeMob != null){
                    if(args.length > 2){
                        if(fakeMob.getType().equalsIgnoreCase("Villager")){
                            String profession = args[2];
                            if(profession.equals("~")){
                                fakeMob.setProfession(null);
                            }else{
                                fakeMob.setProfession(Villager.Profession.valueOf(profession));
                            }

                            if(setFakeMob(fakeMob)){
                                fakeMob.reload(player);
                                player.sendMessage("§7You have set a command  for FakeMob §c"+key+"§7!");
                            }else{
                                player.sendMessage("§cFailed to set FakeMob command.");
                            }
                            return true;

                        }else{
                            player.sendMessage("§cThe FakeMob is not a villager.");
                        }
                    }else{
                        player.sendMessage("§cPlease specify a villager profession you want.");
                    }
                }else{
                    player.sendMessage("§cThe FakeMob from key doesn't exist.");
                }
            }else{
                player.sendMessage("§cPlease specify the FakeMob you wish to set villager profession.");
            }
        }else{
            player.sendMessage("§cYou must be a server admin to set a FakeMob villager profession!");
        }
        return false;
    }
}
