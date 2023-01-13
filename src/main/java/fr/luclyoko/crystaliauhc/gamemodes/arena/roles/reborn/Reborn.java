package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Reborn extends ArenaRole {
    public Reborn(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.REBORN;
        removeMaxHealth(4);
        addPermEffect(PotionEffectType.SPEED, 1);
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
    }
}
