package boss.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import boss.mob.MobTemplet;
import boss.BossPlugin;
import boss.items.ItemHandler;
import boss.items.ItemObject;
import boss.spawners.MobSpawner;
import boss.utils.Parse;

public class MobHandler {

	public static List<Mob> mobs = new ArrayList<Mob>();
	public static List<MobTemplet> templets = new ArrayList<MobTemplet>();
	public static List<String> configNames = new ArrayList<String>();
	
	public static List<Entity> livingMobs = new ArrayList<>();
	
	public static boolean loadMobs(FileConfiguration fc) {
		ConfigurationSection cs = fc.getConfigurationSection("Mobs");
		for (String name : cs.getKeys(false)) {
			if (checkMob(cs, name)) {
				String type = cs.getString(name + ".Type");
				String displayName = cs.getString(name + ".DisplayName");
				int health = cs.getInt(name + ".Health");
				int damage = cs.getInt(name + ".Damage");
				boolean despawn = cs.getBoolean(name + ".Despawn");
				boolean arrowImmune = cs.getBoolean(name + ".ArrowImmune");
				boolean deathBroadcast = cs.getBoolean(name + ".DeathBroadcast");
				List<String> equip = cs.getStringList(name + ".Equipment");
				
				List<ItemObject> items = new ArrayList<ItemObject>();
				List<Double> chances = new ArrayList<Double>();
				List<String> itemNames = cs.getStringList(name + ".Items");
				
				if (itemNames != null) {
					for (String s : itemNames) {
						String[] sl = s.split(",");
						for (ItemObject io : ItemHandler.items) {
							if (io.getConfigName().equals(sl[0])) {
								if (Parse.parseDouble(sl[1]) != null) {
									items.add(io);
									chances.add(Parse.parseDouble(sl[1]));
								}
							}
						}
					}
				}
				
				List<String> skills = cs.getStringList(name + ".Skills");
				
				MobTemplet mob = new MobTemplet(name, type, displayName, health, damage, despawn, arrowImmune, deathBroadcast, equip, items, chances, skills);
				
				templets.add(mob);
				
				configNames.add(mob.getConfigName());

			} else {
				BossPlugin.logger.log(Level.SEVERE, name + " could not be loaded!");
				return false;
			}
		}
		return true;
	}
	
	private static boolean checkMob(ConfigurationSection cs, String name) {
		if (cs.getString(name + ".Type") == null) {
			return false;
		}
		if (cs.getString(name + ".DisplayName") == null) {
			return false;
		}
		if (cs.getInt(name + ".Health") == -1) {
			return false;
		}
		if (cs.getInt(name + ".Damage") == -1) {
			return false;
		}
		Boolean dS = cs.getBoolean(name + ".Despawn");
		if (dS == null) {
			return false;
		}
		Boolean aI = cs.getBoolean(name + ".ArrowImmune");
		if (aI == null) {
			return false;
		}
		Boolean dB = cs.getBoolean(name + ".DeathBroadcast");
		if (dB == null) {
			return false;
		}
		return true;
	}
	
	public static Mob getMob(LivingEntity l) {
		for (Mob m : mobs) {
			if (m.getDisplayName().equals(l.getCustomName().replace('§', '&'))) {
				return m;
			}
		}
		return null;
	}
	
	public static Mob getMob(String configName) {
		for (Mob m : mobs) {
			if (m.getConfigName().equals(configName)) {
				return m;
			}
		}
		return null;
	}
	
	public static MobTemplet getTemplet(String configName)
	{
		for(MobTemplet mt : templets)
		{
			if(mt.getConfigName().contains(configName))
			{
				return mt;
			}
		}
		return null;
	}
	
	public static Mob SpawnAPI(String configName, Location loc, MobSpawner spawner)
	{
		if(getTemplet(configName) == null)
		{
			return null;
		}
		MobTemplet mt = getTemplet(configName);
		Mob mob = mt.spawnMob(loc);
		if(spawner != null)
		{
			mob.setSpawner(spawner);
		}
		mobs.add(mob);
		return mob;
	}
	
}
