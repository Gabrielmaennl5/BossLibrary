package boss.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import boss.mob.Mob;
import boss.mob.MobHandler;

/*
 *  Credit to Frodenkvist for most of this class.
 */

public class EntityDamageByEntityListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		try {
		if (e.getDamager() instanceof TNTPrimed) return;
		
		Entity damaged = e.getEntity();
		
		if (e.getDamager() instanceof Arrow) {
			if (damaged == null) return;
			if (!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			
			if (mob == null) return;
			
			if(mob.isArrowImmune()) {
				e.getDamager().remove();
				e.setCancelled(true);
				return;
			}
			
			if(!((mob.getHealth() - e.getDamage()) == 0)) {
				mob.setHealth(mob.getHealth() - (int) e.getDamage()); 
				e.setDamage(0);
			}
			
			if (mob.hasSkills()) {
				mob.execute();
			}
		}
		
		else if (e.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball)e.getDamager();
			
			LivingEntity shooter = (LivingEntity)snowball.getShooter();
			
			if(shooter == null) return;
			Mob mDamager = MobHandler.getMob(shooter);
			
			if(mDamager == null) return;
			e.setDamage(mDamager.getDamage());
			
			if(damaged == null) return;
			if(!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			if(mob == null) return;
			
			if(!((mob.getHealth() - e.getDamage()) == 0)) {
				mob.setHealth(mob.getHealth() - (int) e.getDamage()); 
				e.setDamage(0);
			}
			
			if(mob.hasSkills()) {
				mob.execute();
			}
		}
		
		else if (e.getDamager() instanceof Fireball) {
			Fireball fireball = (Fireball)e.getDamager();
			
			LivingEntity shooter = (LivingEntity)fireball.getShooter();
			
			if(shooter == null) return;
			Mob mDamager = MobHandler.getMob(shooter);
			
			if(mDamager == null) return;
			e.setDamage(mDamager.getDamage());
			
			if(damaged == null) return;
			if(!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			if(mob == null) return;
			
			if(!((mob.getHealth() - e.getDamage()) == 0)) {
				mob.setHealth(mob.getHealth() - (int) e.getDamage()); 
				e.setDamage(0);
			}
			
			if(mob.hasSkills()) {
				mob.execute();
			}
		}
		
		else if (e.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity)e.getDamager();
			
			if (damaged == null) return;
			if (!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity) damaged);
			
			if (mob != null) {
				if (mob.isParrying()) {
					if (e.getDamager() instanceof Player) {
						Player target = (Player) e.getDamager();
						target.damage(mob.getDamage());
						Vector v = mob.getTargetVector(mob.getEntity().getLocation(), target.getLocation());
						target.setVelocity(v.add(new Vector(0,1,0)));
						mob.message(mob.getEntity(), ChatColor.RED + "parried " + target.getDisplayName());
						e.setCancelled(true);
						return;
					}
				}
				
				if(!((mob.getHealth() - e.getDamage()) == 0)) {
					mob.setHealth(mob.getHealth() - (int) e.getDamage()); 
					e.setDamage(0);
				}
			}
			
			if (mob.hasSkills()) {
				mob.execute();
			}
			
			Mob mDamager = MobHandler.getMob(damager);
			if (mDamager == null) return;
			e.setDamage(mDamager.getDamage()/15);
		}
		
		} catch (NullPointerException ex) {
		}
	}
	
}
