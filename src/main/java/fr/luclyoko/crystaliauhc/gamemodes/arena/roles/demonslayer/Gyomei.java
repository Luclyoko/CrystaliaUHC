package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Gyomei extends Slayer {

    public Gyomei(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.GYOMEI;
        addMaxHealth(4);
        addDayEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addNightEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        heal();
    }
}
