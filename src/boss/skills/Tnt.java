package boss.skills;

import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import boss.utils.CustomEntityFirework;

public class Tnt extends Skill
{
	private int radius;
	private final FireworkEffect.Builder builder = FireworkEffect.builder(); 
	private final FireworkEffect fe = builder.withColor(Color.PURPLE).flicker(true).withFade(Color.RED).with(Type.STAR).build();
	private final FireworkEffect ge = builder.withColor(Color.YELLOW).flicker(true).with(Type.BURST).build();

	public Tnt(double chance, int radius)
	{
		super(chance);
		this.radius = radius;
	}

	public void run(LivingEntity entity)
	{
        List<Player> list = this.getPlayers(radius, entity);
		
		if (!list.isEmpty()) {
			Random ran = new Random();
			Player p = list.get(ran.nextInt(list.size()));
			p.getWorld().spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
			CustomEntityFirework.spawn(p.getLocation(), fe);
			CustomEntityFirework.spawn(entity.getLocation(), ge);
		}
	}
}
