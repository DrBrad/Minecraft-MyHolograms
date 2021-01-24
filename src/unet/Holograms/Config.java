package unet.Holograms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

import static unet.Holograms.Main.*;

public class Config {

    public static int renderDistance = 48;
    public static String maxPlayers = "20";
    public static boolean enableCustomServerList = false;
    public static ArrayList<String> hoverServerList = new ArrayList<>(), multiMotds = new ArrayList<>();

    public Config(){
        try{
            File data = new File(plugin.getDataFolder().getPath()+File.separator+"config.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(data);

            if(data.exists()){
                renderDistance = config.getInt("entity-render-distance");
                enableCustomServerList = config.getBoolean("enable-custom-server-list");
                hoverServerList.addAll(config.getStringList("hover-server-list"));
                maxPlayers = config.getString("server-list-players-max");
                multiMotds.addAll(config.getStringList("multi-motd"));

            }else{
                String[] hoverList = { "&lLINE 1", "&6LINE 2", "&cLINE 3" };
                String[] motds = { "MOTD 1 LINE 1 \n LINE 2", "MOTD 2 LINE 1 \n LINE 2", "MOTD 3 LINE 1 \n LINE 2" };
                config.set("entity-render-distance", 48);
                config.set("enable-custom-server-list", false);
                config.set("hover-server-list", hoverList);
                config.set("server-list-players-max", "20");
                config.set("multi-motd", motds);

                config.save(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
