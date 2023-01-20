package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Muzan extends Demon {

    private boolean nezukoKilled;

    public Muzan(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.MUZAN;
        this.nezukoKilled = false;
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerKill(PlayerDeathEvent event) {
        if (nezukoKilled) return;
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Muzan.this)) return;
            CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
            if (crystaliaTarget.getRole() instanceof Nezuko) {
                removeNightEffect(PotionEffectType.INCREASE_DAMAGE);
                addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
                addNightEffect(PotionEffectType.SPEED, 0);
                nezukoKilled = true;
                crystaliaPlayer.sendMessage("§aVous venez de tuer §eNezuko§a, vous gagnez donc l'effet §bSpeed 1 §ade nuit et votre effet de §bStrength 1 §adevient permanent.");
            }
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Si vous parvenez à tuer §eNezuko§f, vous obtiendrez les effets §bStrength 1 §fde jour et §bSpeed 1 §fde nuit.");
    }
}
