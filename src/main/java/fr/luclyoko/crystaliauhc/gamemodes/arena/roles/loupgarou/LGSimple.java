package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.loupgarou;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class LGSimple extends ArenaRole {
    public LGSimple(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.LG_SIMPLE;
        addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addPermEffect(PotionEffectType.NIGHT_VISION, 0);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(LGSimple.this)) return;
            addTempEffect(PotionEffectType.SPEED, 0, 2 * 20 * 60, false);
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous obtenez 2 minutes de §bSpeed 1 §fà chaque kill.");
    }
}
