package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.CelestialHand;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.RoleGamemodeEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class MarkEvans extends ArenaRole {

    public MarkEvans(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.MARK_EVANS;
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        addPower(new CelestialHand(getArenaUHC(), this, main));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(player);
        if (crystaliaTarget == crystaliaPlayer) return;
        if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(MarkEvans.this)) return;
        if (crystaliaTarget.getRole() instanceof ArenaRole) {
            ArenaRole arenaRole = (ArenaRole) crystaliaTarget.getRole();
            if (arenaRole.getArenaRolesEnum().getGamemodeEnum().equals(RoleGamemodeEnum.INAZUMA)) {
                removeMaxHealth(2);
                crystaliaPlayer.sendMessage("§cUn rôle de l'univers Inazuma Eleven est mort, vous perdez donc un coeur permanent.");
            }
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous perdez un coeur permanent à la mort des rôles de l'univers d'Inazuma Eleven.");
    }
}
