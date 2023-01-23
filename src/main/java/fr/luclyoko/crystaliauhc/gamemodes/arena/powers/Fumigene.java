package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class Fumigene extends ArenaPower {
    public Fumigene(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.FUMIGENE;
        this.cooldown = 120;
        this.duration = 10;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (target != player) {
                    if (main.getPlayerManager().getExactPlayer(target) != null) {
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                            crystaliaTarget.getRole().addTempEffect(PotionEffectType.BLINDNESS, 0, duration * 20, false);
                            crystaliaTarget.getRole().setInvincible(true);
                            crystaliaTarget.sendMessage("§cVous avez été touché par le " + getColoredName() + "§cde §e" + user.getRole().getName() + "§c.");
                            Bukkit.getScheduler().runTaskLater(main, () -> crystaliaTarget.getRole().setInvincible(false), duration * 20L);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet de donner l'effet §bBlindness §fet de rendre §binvincible §fpendant 10 secondes à tous les joueurs dans un rayon de 20 blocs autour de vous.";
    }
}
