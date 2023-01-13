package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public abstract class Slayer extends ArenaRole {

    NichirinBlade nichirinBlade;

    public Slayer(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.nichirinBlade = NichirinBladeManager.chooseBlade(this);
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

    @Override
    public String getDescription() {
        return "§7Votre rôle: §6§l" + getName() + (nichirinBlade != null ? " " + nichirinBlade.getColor() + nichirinBlade.getName() + " §8§o(" + nichirinBlade.getBonus() + ")" : "") + getEffectsDescription() + "\n \n§3§lPouvoirs:\n    §7• §f" + getPowersDescription();
    }
}
