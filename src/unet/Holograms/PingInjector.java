package unet.Holograms;

import io.netty.channel.*;

import java.lang.reflect.*;
import java.util.*;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.event.*;

public class PingInjector implements Listener {

    private MinecraftServer server;
    private List<?> networkManagers;

    public PingInjector(){
        try{
            CraftServer craftserver = (CraftServer) Bukkit.getServer();
            Field console = craftserver.getClass().getDeclaredField("console");
            console.setAccessible(true);
            server = (MinecraftServer) console.get(craftserver);
            ServerConnection conn = server.getServerConnection();
            networkManagers = Collections.synchronizedList((List<?>) getNetworkManagerList(conn));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void injectOpenConnection(){
        if(networkManagers.size() > 0){
            for(Object manager : networkManagers){
                if(manager instanceof NetworkManager){
                    Channel channel = ((NetworkManager) manager).channel;
                    if(channel.pipeline().context("ping_handler") == null && (channel.pipeline().context("packet_handler") != null)){
                        channel.pipeline().addBefore("packet_handler", "ping_handler", new MyDuplexPingHandler());
                    }
                }
            }
        }
    }

    public Object getNetworkManagerList(ServerConnection conn){
        try{
            for(Method method : conn.getClass().getDeclaredMethods()){
                method.setAccessible(true);
                if(method.getReturnType() == List.class){
                    Object object = method.invoke(null, conn);
                    return object;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
