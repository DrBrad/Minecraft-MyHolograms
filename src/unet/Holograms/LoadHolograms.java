package unet.Holograms;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static unet.Holograms.Config.*;
import static unet.Holograms.Main.*;

public class LoadHolograms {

    private static HashMap<String, Hologram> hologramsList = new HashMap<>();
    private static HashMap<Player, ArrayList<Hologram>> renderedHolograms = new HashMap<>();

    public LoadHolograms(){
        File holograms = new File(plugin.getDataFolder()+File.separator+"holograms");
        if(holograms.exists() && holograms.listFiles().length > 0){
            for(File hologram : holograms.listFiles()){
                FileConfiguration config = YamlConfiguration.loadConfiguration(hologram);
                Location location = new Location(plugin.getServer().getWorld(config.getString("world")),
                        config.getDouble("x"),
                        config.getDouble("y"),
                        config.getDouble("z"));
                String text = config.getString("text");

                hologramsList.put(hologram.getName().substring(0, hologram.getName().length()-4), new Hologram(location, text));
            }
        }

        generateHolograms();
    }


    public static void checkDistanceHolograms(Player player, Location location){
        if(!renderedHolograms.containsKey(player)){
            renderedHolograms.put(player, new ArrayList<>());
        }

        for(Hologram hologram : hologramsList.values()){
            if(hologram.getLocation().getWorld().equals(location.getWorld())){
                int distance = (int) hologram.getLocation().distance(location);
                if(distance > renderDistance && renderedHolograms.get(player).contains(hologram)){
                    renderedHolograms.get(player).remove(hologram);

                }else if(distance < renderDistance && !renderedHolograms.get(player).contains(hologram)){
                    renderedHolograms.get(player).add(hologram);
                    hologram.display(player);
                }
            }else if(renderedHolograms.get(player).contains(hologram)){
                renderedHolograms.get(player).remove(hologram);
            }
        }
    }

    public static void stopRenderingHolograms(Player player){
        if(renderedHolograms.containsKey(player)){
            renderedHolograms.remove(player);
        }
    }

    public static void generateHolograms(){
        if(hologramsList.size() > 0){
            for(Hologram hologram : hologramsList.values()){
                hologram.createArmorStand();
            }
        }
    }

    public static Hologram getHologram(String key){
        if(hologramsList.containsKey(key)){
            return hologramsList.get(key);
        }
        return null;
    }

    public static boolean setHologram(String key, Hologram hologram){
        File holograms = new File(plugin.getDataFolder()+File.separator+"holograms");
        if(!holograms.exists()){
            holograms.mkdirs();
        }

        try{
            File hologramFile = new File(plugin.getDataFolder()+File.separator+"holograms"+File.separator+key+".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(hologramFile);
            config.set("world", hologram.getLocation().getWorld().getName());
            config.set("x", hologram.getLocation().getX());
            config.set("y", hologram.getLocation().getY());
            config.set("z", hologram.getLocation().getZ());
            config.set("text", hologram.getText());
            config.save(hologramFile);

            if(!hologramsList.containsKey(key)){
                hologram.createArmorStand();
                hologramsList.put(key, hologram);
            }

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static int getTotalHolograms(){
        return hologramsList.size();
    }

    public static ArrayList<String> getHolograms(){
        return new ArrayList<>(hologramsList.keySet());
    }

    public static void removeHologram(String key){
        File hologramFile = new File(plugin.getDataFolder()+File.separator+"holograms"+File.separator+key+".yml");
        if(hologramFile.exists()){
            hologramFile.delete();
            if(hologramsList.containsKey(key)){
                hologramsList.get(key).kill();
                hologramsList.remove(key);
            }
        }
    }

    public static void clearHolograms(){
        if(hologramsList.size() > 0){
            for(Hologram hologram : hologramsList.values()){
                hologram.kill();
            }
        }

        hologramsList.clear();
        renderedHolograms.clear();
    }
}
