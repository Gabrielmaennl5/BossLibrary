package boss.mob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
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
import org.bukkit.entity.Player;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import boss.mob.MobHandler;
import boss.items.ItemObject;
import boss.skills.Dragin;
import boss.skills.Grab;
import boss.skills.HealthDepend;
import boss.skills.Ignite;
import boss.skills.LightningStorm;
import boss.skills.Parry;
import boss.skills.Potion;
import boss.skills.Shuffle;
import boss.skills.Skill;
import boss.skills.SpawnAdd;
import boss.skills.Teleport;
import boss.skills.Tnt;
import boss.skills.Toss;
import boss.skills.UsableOnce;
import boss.spawners.MobSpawner;
import boss.utils.Namer;
import boss.utils.Parse;

/*
 *  Most of this class was created by ThaH3lper, check him out on the bukkit forums.ö
 */

public class Mob {

	private String configName;
	private String type;
	private String displayName;
	
	private double maxhealth;
	
	private int health;
	private int damage;
	
	private boolean despawn;
	private boolean arrowImmune;
	
	private boolean deathBroadcast;
	private boolean hasSkills;
	private boolean parrying;
	
	private List<ItemObject> items;
	private List<Double> chances;
	
	private List<Skill> skills;
	
	private Entity entity = null;
	
	private MobSpawner spawner = null;
	
	private Mob addon;
	
	public Mob(LivingEntity entity, String configName, String type,
			String displayName, int damage, int maxHealth, boolean despawn, boolean arrowImmune, boolean deathBroadcast
			, List<ItemObject> items, List<Double> chances, List<String> skills)
	{
		this.entity = entity;
		this.configName = configName;
		this.type = type;
		this.displayName = displayName;
		this.damage = damage;
		this.maxhealth = maxHealth;
		this.setHealth(maxHealth);
		this.despawn = despawn;
		this.arrowImmune = arrowImmune;
		this.deathBroadcast = deathBroadcast;
		this.items = items;
		this.chances = chances;
		this.hasSkills = false;
		this.parrying = false;
		this.skills = new ArrayList<>();
		//Loading skills
		Iterator<String> itr = skills.iterator();
		while(itr.hasNext()) {
			String s = itr.next();
			String[] split = s.split(",");
			if (split[0].equalsIgnoreCase("parry")) {
				if (split.length != 2) continue;
				double chance = Parse.parseDouble(split[1]);
				this.skills.add(new Parry(chance));
			} else if (split[0].equalsIgnoreCase("shuffle")) {
				if (split.length != 3) continue;
				this.skills.add(new Shuffle(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("spawnadd")) {
				if (split.length != 5) continue;
				this.skills.add(new SpawnAdd(Parse.parseDouble(split[1]), split[2], Parse.parseInteger(split[3]), Parse.parseInteger(split[4])));
			} else if (split[0].equalsIgnoreCase("toss")) {
				if (split.length != 4) continue;
				this.skills.add(new Toss(Parse.parseDouble(split[1]), Parse.parseInteger(split[2]), Parse.parseInteger(split[3])));
			} else if (split[0].equalsIgnoreCase("lightningstorm")) {
				if (split.length != 3) continue;
				this.skills.add(new LightningStorm(Parse.parseDouble(split[1]), Parse.parseDouble(split[2])));
			} else if (split[0].equalsIgnoreCase("teleport")) {
				if (split.length != 3) continue;
				this.skills.add(new Teleport(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("tnt")) {
				if (split.length != 3) continue;
				this.skills.add(new Tnt(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("dragin")) {
				if (split.length != 3) continue;
				this.skills.add(new Dragin(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("ignite")) {
			    if (split.length != 4) continue;
			    this.skills.add(new Ignite(Parse.parseDouble(split[1]), Parse.parseInteger(split[2]), Parse.parseInteger(split[3])));
		    }
			else if (split[0].equalsIgnoreCase("potion")) {
				if(split.length != 3) continue;
				String[] data = split[1].split(":");
				if(data.length != 4) continue;
				int radius = Integer.valueOf(data[3]);
				double chance = Double.valueOf(split[2]);
				PotionEffect potion = new PotionEffect(PotionEffectType.getByName(data[0]), Parse.parseInteger(data[1]) * 20, Parse.parseInteger(data[2]) - 1);
				this.skills.add(new Potion(chance, radius, potion));
			} else if (split[0].equalsIgnoreCase("grab")) {
				if (split.length != 2) continue;
				this.skills.add(new Grab(Parse.parseDouble(split[1])));
			}
		}
		if (!this.skills.isEmpty()) hasSkills = true;
	}
	
	/*public void spawn(Location l, MobSpawner spawner) {
		LivingEntity le = this.spawnMob(l, this.type);
		le.setCustomNameVisible(true);
		le.setCustomName(Namer.addChatColor(displayName));
		le.setMaxHealth(health);
		le.setHealth(health);
		entity = le;
		MobHandler.livingMobs.add(entity);
		this.spawner = spawner;
	}*/

	public void execute() {
		Iterator<Skill> itr = skills.iterator();
		
		while(itr.hasNext()) {
			Skill temp = itr.next();
			if (temp instanceof UsableOnce) { 
				if (((UsableOnce)temp).hasUsed()) continue;
			}
			if (temp instanceof HealthDepend) {
				if (((HealthDepend) temp).getHealthNeedToCast() < (int)this.getHealth()) continue;
			}
			if(Math.random() > temp.getChance()) continue;
			
			try {
				temp.run((LivingEntity) entity);
			} catch (Exception e) {
			}
		}
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
	
	public Vector getTargetVector(Location shooter, Location target) {
		Location first_location = shooter.add(0, 1, 0);
		Location second_location = target.add(0, 1, 0);
		Vector vector = second_location.toVector().subtract(first_location.toVector());
		return vector;
	}
	
	public void message(Entity ent, String message) {
		List<Player> list = new ArrayList<Player>();
		List<Entity> near = ent.getNearbyEntities(30, 30, 30);
		for(Entity check : near)
		{
			if(check instanceof Player)
			{
				list.add((Player) check);
			}
		}
		Mob mob = MobHandler.getMob((LivingEntity) ent);
		Iterator<Player> itr = list.iterator();
		while(itr.hasNext())
		{
			Player temp = itr.next();
			temp.sendMessage(Namer.addChatColor("<" + mob.getDisplayName() + ChatColor.RESET + "> " + message));
		}
	}
	
	public String getConfigName() {
		return configName;
	}

	public String getType() {
		return type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public double getMaxHealth() {
		return maxhealth;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isCanDespawn() {
		return despawn;
	}

	public boolean isArrowImmune() {
		return arrowImmune;
	}
	
	public boolean hasDeathBroadcast() {
		return deathBroadcast;
	}

	public boolean hasSkills() {
		return hasSkills;
	}

	public boolean isParrying() {
		return parrying;
	}

	public void setParrying(boolean parrying) {
		this.parrying = parrying;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setSpawner(MobSpawner spawner) {
		this.spawner = spawner;
	}
	
	public MobSpawner getSpawner()
	{
		return spawner;
	}

	public Mob getAddon() {
		return addon;
	}

	public void setAddon(Mob addon) {
		this.addon = addon;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}
