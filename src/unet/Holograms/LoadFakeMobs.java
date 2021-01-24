package unet.Holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static unet.Holograms.Config.*;
import static unet.Holograms.Main.*;

public class LoadFakeMobs {

    private static HashMap<String, FakeMob> fakemobsList = new HashMap<>();
    private static HashMap<Integer, String> fakemobsById = new HashMap<>();

    private static HashMap<Player, ArrayList<FakeMob>> renderedMobs = new HashMap<>();

    public LoadFakeMobs(){
        File fakemobs = new File(plugin.getDataFolder()+File.separator+"fakemobs");
        if(fakemobs.exists() && fakemobs.listFiles().length > 0){
            for(File hologram : fakemobs.listFiles()){
                FileConfiguration config = YamlConfiguration.loadConfiguration(hologram);

                String key = hologram.getName().substring(0, hologram.getName().length()-4);

                Location location = new Location(plugin.getServer().getWorld(config.getString("world")),
                        config.getDouble("x"),
                        config.getDouble("y"),
                        config.getDouble("z"),
                        (float)config.getDouble("yaw"),
                        (float)config.getDouble("pitch"));

                String type = config.getString("type"), name = config.getString("name"), command = config.getString("command");
                Material helmet = Material.getMaterial(config.getString("helmet")),
                        chestplate = Material.getMaterial(config.getString("chestplate")),
                        leggings = Material.getMaterial(config.getString("leggings")),
                        boots = Material.getMaterial(config.getString("boots")),
                        rightHand = Material.getMaterial(config.getString("rightHand")),
                        leftHand = Material.getMaterial(config.getString("leftHand"));

                Villager.Profession profession = null;
                if(config.contains("profession")){
                    profession = Villager.Profession.valueOf(config.getString("profession"));
                }

                FakeMob fakeMob = new FakeMob(key, location, type, name, command, helmet, chestplate, leggings, boots, rightHand, leftHand, profession);
                int entityId = fakeMob.createEntity();

                fakemobsList.put(key, fakeMob);
                fakemobsById.put(entityId, key);
            }
        }
    }

    public static void checkDistanceFakeMobs(Player player, Location location){
        if(!renderedMobs.containsKey(player)){
            renderedMobs.put(player, new ArrayList<>());
        }

        for(FakeMob fakeMob : fakemobsList.values()){
            if(fakeMob.getLocation().getWorld().equals(location.getWorld())){
                int distance = (int) fakeMob.getLocation().distance(location);
                if(distance > renderDistance && renderedMobs.get(player).contains(fakeMob)){
                    renderedMobs.get(player).remove(fakeMob);

                }else if(distance < renderDistance && !renderedMobs.get(player).contains(fakeMob)){
                    renderedMobs.get(player).add(fakeMob);
                    fakeMob.display(player);
                }
            }else if(renderedMobs.get(player).contains(fakeMob)){
                renderedMobs.get(player).remove(fakeMob);
            }
        }
    }

    public static void stopRenderingFakeMobs(Player player){
        if(renderedMobs.containsKey(player)){
            renderedMobs.remove(player);
        }
    }

    public static FakeMob getFakeMob(String key){
        if(fakemobsList.containsKey(key)){
            return fakemobsList.get(key);
        }
        return null;
    }

    public static boolean setFakeMob(FakeMob fakeMob){
        File fakemobs = new File(plugin.getDataFolder()+File.separator+"fakemobs");
        if(!fakemobs.exists()){
            fakemobs.mkdirs();
        }

        try{
            File fakeMobFile = new File(plugin.getDataFolder()+File.separator+"fakemobs"+File.separator+fakeMob.getKey()+".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(fakeMobFile);
            config.set("world", fakeMob.getLocation().getWorld().getName());
            config.set("x", fakeMob.getLocation().getX());
            config.set("y", fakeMob.getLocation().getY());
            config.set("z", fakeMob.getLocation().getZ());
            config.set("yaw", fakeMob.getLocation().getYaw());
            config.set("pitch", fakeMob.getLocation().getPitch());
            config.set("type", fakeMob.getType());
            config.set("helmet", (fakeMob.getHelmet() == null) ? null : fakeMob.getHelmet().name());
            config.set("chestplate", (fakeMob.getChestplate() == null) ? null : fakeMob.getChestplate().name());
            config.set("leggings", (fakeMob.getLeggings() == null) ? null : fakeMob.getLeggings().name());
            config.set("boots", (fakeMob.getBoots() == null) ? null : fakeMob.getBoots().name());
            config.set("rightHand", (fakeMob.getRightHand() == null) ? null : fakeMob.getRightHand().name());
            config.set("leftHand", (fakeMob.getLeftHand() == null) ? null : fakeMob.getLeftHand().name());
            config.set("name", fakeMob.getName());
            config.set("command", fakeMob.getCommand());
            config.set("profession", (fakeMob.getProfession() == null) ? null : fakeMob.getProfession().name());
            config.save(fakeMobFile);

            if(!fakemobsList.containsKey(fakeMob.getKey())){
                fakemobsList.put(fakeMob.getKey(), fakeMob);
            }

            if(fakeMob.getEntityId() == -1){
                if(!fakemobsById.containsKey(fakeMob.getEntityId())){
                    fakemobsById.put(fakeMob.createEntity(), fakeMob.getKey());
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<String> getFakeMobs(){
        return new ArrayList<>(fakemobsList.keySet());
    }

    public static int getTotalFakeMobs(){
        return fakemobsList.size();
    }

    public static FakeMob getFakeMobById(int id){
        if(fakemobsById.containsKey(id)){
            if(fakemobsList.containsKey(fakemobsById.get(id))){
                return fakemobsList.get(fakemobsById.get(id));
            }
        }
        return null;
    }

    public static void removeFakeMob(String key){
        File fakeMobFile = new File(plugin.getDataFolder()+File.separator+"fakemobs"+File.separator+key+".yml");
        if(fakeMobFile.exists()){
            fakeMobFile.delete();
            if(fakemobsList.containsKey(key)){
                fakemobsList.get(key).kill();
                if(fakemobsById.containsKey(fakemobsList.get(key).getEntityId())){
                    fakemobsById.remove(fakemobsList.get(key).getEntityId());
                }
                fakemobsList.remove(key);
            }
        }
    }

    public static void clearFakeMobs(){
        if(fakemobsList.size() > 0){
            for(FakeMob fakeMob : fakemobsList.values()){
                fakeMob.kill();
            }
        }

        fakemobsList.clear();
        fakemobsById.clear();
        renderedMobs.clear();
    }
}
