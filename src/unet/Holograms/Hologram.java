package unet.Holograms;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class Hologram {

    private Location location;
    private String text;
    private EntityArmorStand armorStand;

    public Hologram(Location location, String text){
        this.location = location;
        this.text = text;
    }

    public Location getLocation(){
        return location;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        armorStand.setCustomName(CraftChatMessage.fromStringOrNull(text));
        this.text = text;
    }

    public void display(Player player){
        PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(armorStand);
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(spawn);
        nmsPlayer.playerConnection.sendPacket(metadata);
    }

    public void reload(Player player){
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(metadata);
    }

    public void createArmorStand(){
        armorStand = new EntityArmorStand(EntityTypes.ARMOR_STAND, ((CraftWorld) location.getWorld()).getHandle());
        armorStand.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        armorStand.setArms(false);
        armorStand.setNoGravity(true);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);
        armorStand.setCustomName(CraftChatMessage.fromStringOrNull(text));
        armorStand.setCustomNameVisible(true);
    }

    public void kill(){
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(armorStand.getId());

        for(Player player : location.getWorld().getPlayers()){
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.playerConnection.sendPacket(destroy);
        }
    }
}
