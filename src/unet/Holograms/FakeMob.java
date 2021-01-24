package unet.Holograms;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import net.minecraft.server.v1_16_R3.Entity;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static unet.Holograms.MobResolver.*;
import static unet.Holograms.Main.*;

public class FakeMob {

    private Location location;
    private String key, type, name, command;
    private Material helmet, chestplate, leggings, boots, rightHand, leftHand;
    private Villager.Profession profession;
    private Entity entity;
    private List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList = new ArrayList<>();

    public FakeMob(String key, Location location, String type){
        this.key = key;
        this.location = location;
        this.type = type;
    }

    public FakeMob(String key,
                   Location location,
                   String type,
                   String name,
                   String command,
                   Material helmet,
                   Material chestplate,
                   Material leggings,
                   Material boots,
                   Material rightHand,
                   Material leftHand,
                   Villager.Profession profession){
        this.key = key;
        this.location = location;
        this.type = type;
        this.name = name;
        this.command = command;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.rightHand = rightHand;
        this.leftHand = leftHand;
        this.profession = profession;
    }

    public String getKey(){
        return key;
    }

    public Location getLocation(){
        return location;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;

        if(name != null){
            entity.setCustomName(CraftChatMessage.fromStringOrNull(name));
            entity.setCustomNameVisible(true);
        }
    }

    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public Material getHelmet(){
        return helmet;
    }

    public void setHelmet(Material helmet){
        if(helmet == null){
            equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(helmet))));
        }
        this.helmet = helmet;
    }

    public Material getChestplate(){
        return chestplate;
    }

    public void setChestplate(Material chestplate){
        if(chestplate == null){
            equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(chestplate))));
        }
        this.chestplate = chestplate;
    }

    public Material getLeggings(){
        return leggings;
    }

    public void setLeggings(Material leggings){
        if(leggings == null){
            equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(leggings))));
        }
        this.leggings = leggings;
    }

    public Material getBoots(){
        return boots;
    }

    public void setBoots(Material boots){
        if(boots == null){
            equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(boots))));
        }
        this.boots = boots;
    }

    public Material getRightHand(){
        return rightHand;
    }

    public void setRightHand(Material rightHand){
        if(rightHand == null){
            equipmentList.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(rightHand))));
        }
        this.rightHand = rightHand;
    }

    public Material getLeftHand(){
        return leftHand;
    }

    public void setLeftHand(Material leftHand){
        if(leftHand == null){
            equipmentList.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))));
        }else{
            equipmentList.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(new ItemStack(leftHand))));
        }
        this.leftHand = leftHand;
    }

    public Villager.Profession getProfession(){
        return profession;
    }

    public void setProfession(Villager.Profession profession){
        if(entity.getEntityType() == EntityTypes.VILLAGER){
            if(profession == null){
                ((Villager) entity.getBukkitEntity()).setProfession(Villager.Profession.NONE);
            }else{
                ((Villager) entity.getBukkitEntity()).setProfession(profession);
            }
        }
        this.profession = profession;
    }

    public int getEntityId(){
        if(entity == null){
            return -1;
        }
        return entity.getId();
    }

    public void display(Player player){
        PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving((EntityLiving) entity);
        PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(entity, (byte) (location.getYaw() * 255F / 360F));
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(spawn);
        nmsPlayer.playerConnection.sendPacket(rotation);
        nmsPlayer.playerConnection.sendPacket(metadata);
        if(equipmentList.size() > 0){
            PacketPlayOutEntityEquipment equipment = new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);
            nmsPlayer.playerConnection.sendPacket(equipment);
        }
    }

    public void reload(Player player){
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(metadata);
        if(equipmentList.size() > 0){
            PacketPlayOutEntityEquipment equipment = new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);
            nmsPlayer.playerConnection.sendPacket(equipment);
        }
    }

    public int createEntity(){
        if(entity != null){
            return entity.getId();
        }

        entity = fromName(type, location);
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        if(helmet != null){
            equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(helmet))));
        }
        if(chestplate != null){
            equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(chestplate))));
        }
        if(leggings != null){
            equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(leggings))));
        }
        if(boots != null){
            equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(boots))));
        }
        if(rightHand != null){
            equipmentList.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(rightHand))));
        }
        if(leftHand != null){
            equipmentList.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(new ItemStack(leftHand))));
        }

        if(name != null){
            entity.setCustomName(CraftChatMessage.fromStringOrNull(name));
            entity.setCustomNameVisible(true);
        }

        if(profession != null && entity.getEntityType() == EntityTypes.VILLAGER){
            ((Villager) entity.getBukkitEntity()).setProfession(profession);
        }

        return entity.getId();
    }

    private ArrayList<Player> delayInteract = new ArrayList<>();

    public void interact(Player player){
        if(command != null){
            if(!delayInteract.contains(player)){
                delayInteract.add(player);
                try{
                    Bukkit.getScheduler().callSyncMethod(plugin, new Callable<Boolean>(){
                        @Override
                        public Boolean call(){
                            return Bukkit.dispatchCommand(player, command);
                        }
                    }).get();
                }catch(Exception e){
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
                    @Override
                    public void run(){
                        delayInteract.remove(player);
                    }
                }, 20);
            }
        }
    }

    public void kill(){
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entity.getId());

        for(Player player : location.getWorld().getPlayers()){
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.playerConnection.sendPacket(destroy);
        }
    }
}
