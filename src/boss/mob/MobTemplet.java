package boss.mob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import code.op.utils.Namer;
import boss.items.ItemObject;

/*
 *  Most of this class was created by ThaH3lper, check him out on the bukkit forums.�
 */

public class MobTemplet {

	private String configName;
	private String type;
	private String displayName;
	private int health;
	private int damage;
	private int penisLength;
	private boolean despawn;
	private boolean arrowImmune;
	
	private boolean deathBroadcast;
	
	private List<ItemObject> items;
	
	private List<Double> chances;
	
	private List<String> skills;
	private List<String> equipment;
	
	public MobTemplet(String configName, String type, String displayName, int health, int damage,
			boolean despawn, boolean arrowImmune, boolean deathBroadcast, List<String>equip, List<ItemObject> items,
			List<Double> chances, List<String> skills) {
		this.configName = configName;
		this.type = type;
		this.displayName = displayName;
		this.health = health;
		this.damage = damage;
		this.despawn = despawn;
		this.arrowImmune = arrowImmune;
		this.deathBroadcast = deathBroadcast;
		this.items = items;
		this.chances = chances;
		this.skills = skills;
		this.equipment = equip;
	}
	
	public Mob spawnMob(Location loc)
	{
		LivingEntity le = spawnMob(loc, type);
		le.setCustomName(Namer.addChatColor(displayName));
		le.setCustomNameVisible(true);
		le.setMaxHealth(100);
		le.setHealth(le.getMaxHealth());
		le.setRemoveWhenFarAway(false);
		setEquipment(le, equipment);
		return new Mob(le, configName, type, displayName, damage, health, despawn, arrowImmune, deathBroadcast, items, chances, skills); 
	}
	
	public List<ItemStack> createDropLoot() {
		if (items == null || chances == null) return null;
		List<ItemStack> list = new ArrayList<ItemStack>(); 
		for (int i = 0; i < items.size(); i++) {
			double chance = Math.random();
			if (chance <= chances.get(i)) {
				list.add(items.get(i).getIs());
			}
		}
		return list;

	}

	private LivingEntity spawnMob(Location loc, String s) {
		if(s.equals("zombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			return m;
		}
		else if(s.equals("babyzombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setBaby(true);
			return m;
		}
		else if(s.equals("villagezombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setVillager(true);
			return m;
		}
		else if(s.equals("babyvillagezombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setBaby(true);
			m.setVillager(true);
			return m;
		}
		//spawnSkeleton
		else if(s.equals("skeleton"))
		{
			Skeleton m = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			return m;
		}
		else if(s.equals("skeletonwither"))
		{
			Skeleton m = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			m.setSkeletonType(SkeletonType.WITHER);
			return m;
		}
		//none editables
		else if(s.equals("spider"))
		{
			Spider m = (Spider) loc.getWorld().spawnEntity(loc, EntityType.SPIDER);
			return m;
		}
		else if(s.equals("cavespider"))
		{
			CaveSpider m = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
			return m;
		}
		else if(s.equals("slime"))
		{
			Slime m = (Slime) loc.getWorld().spawnEntity(loc, EntityType.SLIME);
			m.setSize(1);
			return m;
		}
		else if(s.equals("magmacube"))
		{
			MagmaCube m = (MagmaCube) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
			m.setSize(1);
			return m;
		}
		else if(s.equals("pigmen"))
		{
			PigZombie m = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("babypigmen"))
		{
			PigZombie m = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			m.setBaby(true);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("blaze"))
		{
			Blaze m = (Blaze) loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
			return m;
		}
		else if(s.equals("bat"))
		{
			Bat m = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
			return m;
		}
		else if(s.equals("witch"))
		{
			Witch m = (Witch) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
			return m;
		}
		else if(s.equals("wolf"))
		{
			Wolf m = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("creeper"))
		{
			Creeper m = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
			return m;
		}
		else if(s.equals("ghast"))
		{
			Ghast m = (Ghast) loc.getWorld().spawnEntity(loc, EntityType.GHAST);
			return m;
		}
		else if(s.equals("enderman"))
		{
			Enderman m = (Enderman) loc.getWorld().spawnEntity(loc, EntityType.ENDERMAN);
			return m;
		}
		else if(s.equals("enderdragon"))
		{
			EnderDragon m = (EnderDragon) loc.getWorld().spawnEntity(loc, EntityType.ENDER_DRAGON);
			return m;
		}
		else if(s.equals("wither"))
		{
			Wither m = (Wither) loc.getWorld().spawnEntity(loc, EntityType.WITHER);
			return m;
		}
		else if(s.equals("ocelot"))
		{
			Ocelot m = (Ocelot) loc.getWorld().spawnEntity(loc, EntityType.OCELOT);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyocelot"))
		{
			Ocelot m = (Ocelot) loc.getWorld().spawnEntity(loc, EntityType.OCELOT);
			m.setBaby();
			return m;
		}
		else if(s.equals("horse"))
		{
			Horse m = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyhorse"))
		{
			Horse m = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			m.setBaby();
			return m;
		}
		else if(s.equals("chicken"))
		{
			Chicken m = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
			m.setAdult();
			return m;
		}
		else if(s.equals("babychicken"))
		{
			Chicken m = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
			m.setBaby();
			return m;
		}	
		else if(s.equals("cow"))
		{
			Cow m = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babycow"))
		{
			Cow m = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
			m.setBaby();
			return m;
		}	
		else if(s.equals("mushroomcow"))
		{
			MushroomCow m = (MushroomCow) loc.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babymushroomcow"))
		{
			MushroomCow m = (MushroomCow) loc.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
			m.setBaby();
			return m;
		}
		else if(s.equals("sheep"))
		{
			Sheep m = (Sheep) loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babysheep"))
		{
			Sheep m = (Sheep) loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
			m.setBaby();
			return m;
		}
		else if(s.equals("squid"))
		{
			Squid m = (Squid) loc.getWorld().spawnEntity(loc, EntityType.SQUID);
			m.setRemainingAir(1000);
			return m;
		}
		else if(s.equals("giant"))
		{
			Giant m = (Giant) loc.getWorld().spawnEntity(loc, EntityType.GIANT);
			return m;
		}
		else if(s.equals("irongolem"))
		{
			IronGolem m = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
			return m;
		}
		else if(s.equals("pig"))
		{
			Pig m = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
			return m;
		}
		else if(s.equals("villager"))
		{
			Villager m = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyvillager"))
		{
			Villager m = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			m.setBaby();
			return m;
		}
		else if(s.equals("snowman"))
		{
			Snowman m = (Snowman) loc.getWorld().spawnEntity(loc, EntityType.SNOWMAN);
			return m;
		}
		else if(s.equals("silverfish"))
		{
			Silverfish m = (Silverfish) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
			return m;
		}
		else if(s.equals("endermite"))
		{
			Endermite m = (Endermite) loc.getWorld().spawnEntity(loc, EntityType.ENDERMITE);
			return m;
		}
		else if(s.equals("gaurdian"))
		{
			Guardian m = (Guardian) loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
			return m;
		}
		return null;
	}
	
	private void setEquipment(LivingEntity l, List<String> items)
	{
		Iterator<String> itr = items.iterator();
		while(itr.hasNext())
		{
			ItemStack is = null;
			String s = itr.next();
			String[] split = s.split(" ");
			if(s.contains(":"))
			{
				String[] parts = split[0].split(":");
				is = new ItemStack(Integer.valueOf(parts[0]),0,Short.valueOf(parts[1]));
			}
			if(split[1].equals("hand"))
			{
				l.getEquipment().setItemInHand(is);
				l.getEquipment().setItemInHandDropChance(0f);
			}
			else if(split[1].equals("helmet"))
			{
				l.getEquipment().setHelmet(is);
				l.getEquipment().setHelmetDropChance(0f);
			}
			else if(split[1].equals("chestplate"))
			{
				l.getEquipment().setChestplate(is);
				l.getEquipment().setChestplateDropChance(0f);
			}
			else if(split[1].equals("leggings"))
			{
				l.getEquipment().setLeggings(is);
				l.getEquipment().setLeggingsDropChance(0f);
			}
			else if(split[1].equals("boots"))
			{
				l.getEquipment().setBoots(is);
				l.getEquipment().setBootsDropChance(0f);
			}
		}
	}
	
	public String getConfigName()
	{
		return configName;
	}
	
}