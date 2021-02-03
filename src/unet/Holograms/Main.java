package unet.Holograms;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

import static unet.Holograms.Config.*;
import static unet.Holograms.LoadFakeMobs.*;
import static unet.Holograms.LoadHolograms.*;

public class Main extends JavaPlugin implements Listener {

    public static Plugin plugin;
    private PingInjector pingInjector;

    @Override
    public void onEnable(){
        plugin = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("h").setExecutor(new HologramCommands());
        getCommand("fm").setExecutor(new FakeMobCommands());

        new Config();
        new LoadHolograms();
        new LoadFakeMobs();
        if(enableCustomServerList){
            pingInjector = new PingInjector();
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            injectPlayer(player);
        }
    }

    @Override
    public void onDisable(){
        clearHolograms();
        clearFakeMobs();

        for(Player player : Bukkit.getOnlinePlayers()){
            removePlayer(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(pingInjector != null){
            pingInjector.injectOpenConnection();
        }

        checkDistanceHolograms(event.getPlayer(), event.getPlayer().getLocation());
        checkDistanceFakeMobs(event.getPlayer(), event.getPlayer().getLocation());

        injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        removePlayer(event.getPlayer());
        stopRenderingHolograms(event.getPlayer());
        stopRenderingFakeMobs(event.getPlayer());
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        if(pingInjector != null){
            pingInjector.injectOpenConnection();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        checkDistanceHolograms(event.getPlayer(), event.getPlayer().getLocation());
        checkDistanceFakeMobs(event.getPlayer(), event.getPlayer().getLocation());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        checkDistanceHolograms(event.getPlayer(), event.getPlayer().getLocation());
        checkDistanceFakeMobs(event.getPlayer(), event.getPlayer().getLocation());
    }

    private void removePlayer(Player player){
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(new Runnable(){
            @Override
            public void run(){
                channel.pipeline().remove(player.getName());
            }
        });
    }

    public void injectPlayer(Player player){
        ChannelDuplexHandler duplexHandler = new ChannelDuplexHandler(){
            @Override
            public void channelRead(ChannelHandlerContext context, Object object)throws Exception {
                if(object instanceof PacketPlayInUseEntity){
                    PacketPlayInUseEntity packet = (PacketPlayInUseEntity) object;

                    Field f = packet.getClass().getDeclaredField("a");
                    f.setAccessible(true);

                    FakeMob fakeMob = getFakeMobById((int)f.get(packet));
                    if(fakeMob != null){
                        fakeMob.interact(player);
                        return;
                    }
                }

                super.channelRead(context, object);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), duplexHandler);
    }
}
