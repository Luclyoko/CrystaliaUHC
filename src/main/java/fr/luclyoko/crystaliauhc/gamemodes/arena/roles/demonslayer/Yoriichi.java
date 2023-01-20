package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Yoriichi extends Slayer {

    public Yoriichi(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.YORIICHI;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addDayEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer((Player) event.getDamager());
        if (crystaliaPlayer1 != crystaliaPlayer) return;

        if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Yoriichi.this)) return;

        Player target = (Player) event.getEntity();
        if (target.getActivePotionEffects().stream().anyMatch(potionEffect -> potionEffect.getType().equals(PotionEffectType.ABSORPTION))) {
            target.removePotionEffect(PotionEffectType.ABSORPTION);
            target.sendMessage("§eYoriichi §cvous retire votre absorption.");
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous retirez l'absorption des joueurs que vous frappez.");
    }
}
