package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class FieryClouds extends ArenaPower {
    public FieryClouds(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.NUEES_ARDENTES;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getPlayer().launchProjectile(Snowball.class);
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                if (!arenaRole.getCrystaliaPlayer().getPlayer().equals(player)) return;
                snowball.getWorld().createExplosion(snowball.getLocation(), 3F);
            }
        }
    }

    @EventHandler
    public void onSnowballThrow(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                if (!arenaRole.getCrystaliaPlayer().getPlayer().equals(player)) return;
                if (player.getItemInHand().getType().equals(Material.SNOW_BALL)) {
                    ItemStack ball = player.getItemInHand();
                    event.setCancelled(true);
                    player.getInventory().addItem(ball);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet de tirer une boule de neige créant une explosion à l'impact.";
    }
}
