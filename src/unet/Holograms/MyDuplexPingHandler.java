package unet.Holograms;

import com.mojang.authlib.GameProfile;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_16_R3.*;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import static unet.Holograms.Config.*;

public class MyDuplexPingHandler extends ChannelDuplexHandler {

    private static Field serverPingField;

    public MyDuplexPingHandler(){
        try{
            serverPingField = PacketStatusOutServerInfo.class.getDeclaredField("b");
            serverPingField.setAccessible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void write(ChannelHandlerContext context, Object object, ChannelPromise promise)throws Exception {
        if(object instanceof PacketStatusOutServerInfo){
            PacketStatusOutServerInfo packet = (PacketStatusOutServerInfo) object;

            ServerPing ping = (ServerPing) serverPingField.get(packet);

            ServerPing.ServerPingPlayerSample serverPingPlayerSample = ping.b();
            int players = serverPingPlayerSample.b();
            int max = players;
            if(maxPlayers.equals("+1")){
                max++;
            }else{
                max = Integer.parseInt(maxPlayers);
            }

            serverPingPlayerSample = new ServerPing.ServerPingPlayerSample(max, players);

            GameProfile[] sample = new GameProfile[hoverServerList.size()];
            for(int i = 0; i < sample.length; i++){
                sample[i] = new GameProfile(UUID.randomUUID(), hoverServerList.get(i).replaceAll("&", "§"));
            }

            serverPingPlayerSample.a(sample);

            ping.setMOTD(new ChatComponentText(multiMotds.get(new Random().nextInt(multiMotds.size())).replaceAll("&", "§")));
            ping.setPlayerSample(serverPingPlayerSample);

            packet = new PacketStatusOutServerInfo(ping);

            super.write(context, packet, promise);
            return;
        }

        super.write(context, object, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object object)throws Exception {
        super.channelRead(context, object);
    }
}
