package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Kokushibo extends Demon {

    NichirinBlade nichirinBlade;

    public Kokushibo(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.KOKUSHIBO;
        addPermEffect(PotionEffectType.SPEED, 0);
        addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        this.nichirinBlade = NichirinBladeManager.chooseBlade(this);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Kokushibo.this)) return;
            if (maxHealth < 30) {
                addMaxHealth(1);
                crystaliaPlayer.sendMessage("§aVotre kill vous rapporte §c1/2❤ §apermanent.");
            }
        }
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

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous obtenez §c1/2❤ permanent §fà chaque kill (maximum: 15 coeurs).");
    }
}
