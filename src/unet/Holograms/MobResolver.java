package unet.Holograms;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class MobResolver {

    public static Entity fromName(String type, Location location){
        Entity entity = null;
        switch(type){
            case "Bat":
                entity = new EntityBat(EntityTypes.BAT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Cat":
                entity = new EntityCat(EntityTypes.CAT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Chicken":
                entity = new EntityChicken(EntityTypes.CHICKEN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Cod":
                entity = new EntityCod(EntityTypes.COD, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Cow":
                entity = new EntityCow(EntityTypes.COW, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Donkey":
                entity = new EntityHorseDonkey(EntityTypes.DONKEY, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Fox":
                entity = new EntityFox(EntityTypes.FOX, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Horse":
                entity = new EntityHorse(EntityTypes.HORSE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Mooshroom":
                entity = new EntityMushroomCow(EntityTypes.MOOSHROOM, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Mule":
                entity = new EntityHorseMule(EntityTypes.MULE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Ocelot":
                entity = new EntityOcelot(EntityTypes.OCELOT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Parrot":
                entity = new EntityParrot(EntityTypes.PARROT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Pig":
                entity = new EntityPig(EntityTypes.PIG, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Piglin":
                entity = new EntityPiglin(EntityTypes.PIGLIN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Polar_Bear":
                entity = new EntityPolarBear(EntityTypes.POLAR_BEAR, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Rabbit":
                entity = new EntityRabbit(EntityTypes.RABBIT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Salmon":
                entity = new EntitySalmon(EntityTypes.SALMON, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Sheep":
                entity = new EntitySheep(EntityTypes.SHEEP, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Skeleton_Horse":
                entity = new EntityHorseSkeleton(EntityTypes.SKELETON_HORSE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Snow_Golem":
                entity = new EntitySnowman(EntityTypes.SNOW_GOLEM, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Squid":
                entity = new EntitySquid(EntityTypes.SQUID, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Strider":
                entity = new EntityStrider(EntityTypes.STRIDER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Tropical_Fish":
                entity = new EntityTropicalFish(EntityTypes.TROPICAL_FISH, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Turtle":
                entity = new EntityTurtle(EntityTypes.TURTLE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Villager":
                entity = new EntityVillager(EntityTypes.VILLAGER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Wandering_Trader":
                //entity = new EntityChicken(EntityTypes.CHICKEN, ((CraftWorld) location.getWorld()).getHandle());
                break;

            case "Bee":
                entity = new EntityBee(EntityTypes.BEE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Cave_Spider":
                entity = new EntityCaveSpider(EntityTypes.CAVE_SPIDER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Dolphin":
                entity = new EntityDolphin(EntityTypes.DOLPHIN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Enderman":
                entity = new EntityEnderman(EntityTypes.ENDERMAN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Iron_Golem":
                entity = new EntityIronGolem(EntityTypes.IRON_GOLEM, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Llama":
                entity = new EntityLlama(EntityTypes.LLAMA, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Panda":
                entity = new EntityPanda(EntityTypes.PANDA, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Pufferfish":
                entity = new EntityPufferFish(EntityTypes.PUFFERFISH, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Spider":
                entity = new EntityStrider(EntityTypes.STRIDER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Wolf":
                entity = new EntityWolf(EntityTypes.WOLF, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Zombified_Piglin":
                //entity = new EntityZombi(EntityTypes.CHICKEN, ((CraftWorld) location.getWorld()).getHandle());
                break;

            case "Blaze":
                entity = new EntityBlaze(EntityTypes.BLAZE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Creeper":
                entity = new EntityCreeper(EntityTypes.CREEPER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Drowned":
                entity = new EntityDrowned(EntityTypes.DROWNED, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Elder_Guardian":
                entity = new EntityGuardianElder(EntityTypes.ELDER_GUARDIAN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Endermite":
                entity = new EntityEndermite(EntityTypes.ENDERMITE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Evoker":
                entity = new EntityEvoker(EntityTypes.EVOKER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Ghast":
                entity = new EntityGhast(EntityTypes.GHAST, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Guardian":
                entity = new EntityGuardian(EntityTypes.GUARDIAN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Hoglin":
                entity = new EntityHoglin(EntityTypes.HOGLIN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Husk":
                entity = new EntityZombieHusk(EntityTypes.HUSK, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Magma_Cube":
                entity = new EntityMagmaCube(EntityTypes.MAGMA_CUBE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Phantom":
                entity = new EntityPhantom(EntityTypes.PHANTOM, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Piglin_Brute":
                entity = new EntityPiglinBrute(EntityTypes.PIGLIN_BRUTE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Pillager":
                entity = new EntityPillager(EntityTypes.PILLAGER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Ravager":
                entity = new EntityRavager(EntityTypes.RAVAGER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Shulker":
                entity = new EntityShulker(EntityTypes.SHULKER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Silverfish":
                entity = new EntitySilverfish(EntityTypes.SILVERFISH, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Skeleton":
                entity = new EntitySkeleton(EntityTypes.SKELETON, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Slime":
                entity = new EntitySlime(EntityTypes.SLIME, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Stray":
                entity = new EntitySkeletonStray(EntityTypes.STRAY, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Vindicator":
                entity = new EntityVindicator(EntityTypes.VINDICATOR, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Witch":
                entity = new EntityWitch(EntityTypes.WITCH, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Wither_Skeleton":
                entity = new EntitySkeletonWither(EntityTypes.WITHER_SKELETON, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Zoglin":
                entity = new EntityZoglin(EntityTypes.ZOGLIN, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Zombie":
                entity = new EntityZombie(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Zombie_Villager":
                entity = new EntityZombieVillager(EntityTypes.ZOMBIE_VILLAGER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Ender_Dragon":
                entity = new EntityEnderDragon(EntityTypes.ENDER_DRAGON, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Wither":
                entity = new EntityWither(EntityTypes.WITHER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Zombie_Horse":
                entity = new EntityHorseZombie(EntityTypes.ZOMBIE_HORSE, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Giant":
                entity = new EntityGiantZombie(EntityTypes.GIANT, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Illusioner":
                entity = new EntityIllagerIllusioner(EntityTypes.ILLUSIONER, ((CraftWorld) location.getWorld()).getHandle());
                break;
            case "Armor_Stand":
                entity = new EntityArmorStand(EntityTypes.ARMOR_STAND, ((CraftWorld) location.getWorld()).getHandle());
                break;
        }
        return entity;
    }

    public static String[] getMobs(){
        return new String[]{
                "Bat",
                "Cat",
                "Chicken",
                "Cod",
                "Cow",
                "Donkey",
                "Fox",
                "Horse",
                "Mooshroom",
                "Mule",
                "Ocelot",
                "Parrot",
                "Pig",
                "Piglin",
                "Polar_Bear",
                "Rabbit",
                "Salmon",
                "Sheep",
                "Skeleton_Horse",
                "Snow_Golem",
                "Squid",
                "Strider",
                "Tropical_Fish",
                "Turtle",
                "Villager",
                "Wandering_Trader",   ////////

                "Bee",
                "Cave_Spider",
                "Dolphin",
                "Enderman",
                "Iron_Golem",
                "Llama",
                "Panda",
                "Pufferfish",
                "Spider",
                "Wolf",
                "Zombified_Piglin",   //////

                "Blaze",
                "Creeper",
                "Drowned",
                "Elder_Guardian",
                "Endermite",
                "Evoker",
                "Ghast",
                "Guardian",
                "Hoglin",
                "Husk",
                "Magma_Cube",
                "Phantom",
                "Piglin_Brute",
                "Pillager",
                "Ravager",
                "Shulker",
                "Silverfish",
                "Skeleton",
                "Slime",
                "Stray",
                "Vindicator",
                "Witch",
                "Wither_Skeleton",
                "Zoglin",
                "Zombie",
                "Zombie_Villager",
                "Ender_Dragon",
                "Wither",
                "Zombie_Horse",
                "Giant",
                "Illusioner",
                "Armor_Stand"
        };
    }
}
