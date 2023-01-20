package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.Flash;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Kaigaku extends Demon {

    NichirinBlade nichirinBlade;

    public Kaigaku(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.KAIGAKU;
        addPermEffect(PotionEffectType.SPEED, 0);
        addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        this.nichirinBlade = NichirinBladeManager.chooseBlade(this);
        addPower(new Flash(getArenaUHC(), this, main));
    }

    @Override
    public String getDescription() {
        return "§7Votre rôle: §6§l" + getName() + (nichirinBlade != null ? " " + nichirinBlade.getColor() + nichirinBlade.getName() + " §8§o(" + nichirinBlade.getBonus() + ")" : "") + getEffectsDescription() + "\n \n§3§lPouvoirs:" + getPowersDescription();
    }

    @Override
    public void setNoFall(boolean noFall) {
        if (nichirinBlade != null && nichirinBlade.equals(NichirinBlade.GREEN)) {
            this.noFall = true;
            return;
        }
        this.noFall = noFall;
    }

    @Override
    public void setCanSeeLife(boolean canSeeLife) {
        if (nichirinBlade != null && nichirinBlade.equals(NichirinBlade.RED)) {
            this.canSeeLife = true;
            updateCanSeeLife();
            return;
        }
        this.canSeeLife = canSeeLife;
        updateCanSeeLife();
    }
}
