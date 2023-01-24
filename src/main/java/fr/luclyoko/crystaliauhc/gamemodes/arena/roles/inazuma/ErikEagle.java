package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.HelicopterShot;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class ErikEagle extends ArenaRole {
    public ErikEagle(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.ERIK_EAGLE;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPower(new HelicopterShot(getArenaUHC(), this, main));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGappleEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (crystaliaPlayer.isOnline() && crystaliaPlayer.isAlive()) {
            if (player.equals(crystaliaPlayer.getPlayer())) {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(this)) {
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 1, false, true));
                }
            }
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Les pommes en or vous donnent §bAbsorption 2§f.");
    }
}
