package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.disney;

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

public class FlashMcQueen extends ArenaRole {

    public FlashMcQueen(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.FLASHMCQUEEN;
        addPermEffect(PotionEffectType.SPEED, 0);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(FlashMcQueen.this)) return;
            removeMaxHealth(1);
            addSelfSpeedPercent(5);
            crystaliaPlayer.sendMessage("§aVotre kill vous rapporte §b5% de Speed §a(total: " + getSelfSpeedPercent() + "%). Vous perdez également §c1/2❤ permanent§a.");
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Chaque kill vous donnera §b5% de Speed §fmais vous fera perdre §c1/2❤ permanent§f.");
    }
}
