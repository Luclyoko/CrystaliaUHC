package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class FireGodDance extends ArenaPower {
    public FireGodDance(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.DANSE_DU_DIEU_DU_FEU;
        this.duration = 60;
        this.cooldown = 150;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.SPEED, 0, duration*20, false);
        user.getRole().addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, duration*20, false);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        CrystaliaPlayer crystaliaDamager = main.getPlayerManager().getExactPlayer((Player)event.getDamager());
        if (crystaliaDamager != arenaRole.getCrystaliaPlayer()) return;
        if (crystaliaDamager.getRole() != null && crystaliaDamager.getRole() != arenaRole) return;
        if (isOnUse()) {
            Player target = (Player) event.getEntity();
            target.setFireTicks(100);
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bSpeed 1 §fet d'§benflammer §fvos adversaires pendant 1 minute.";
    }
}
