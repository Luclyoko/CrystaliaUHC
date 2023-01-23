package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class FireTornado extends ArenaPower {
    public FireTornado(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.TORNADE_DE_FEU;
        this.duration = 120;
        this.cooldown = 240;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.FIRE_RESISTANCE, 0, duration*20, false);
        user.getPlayer().setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (user.getRole() != null && user.getRole().equals(arenaRole) && user.isOnline() && user.getPlayer().getAllowFlight()) user.getPlayer().setAllowFlight(false);
        }, 20L*duration);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        CrystaliaPlayer crystaliaEntity = main.getPlayerManager().getExactPlayer((Player) event.getEntity());
        if (crystaliaEntity != arenaRole.getCrystaliaPlayer()) return;
        if (crystaliaEntity.getRole() == null || crystaliaEntity.getRole() != arenaRole) return;

        if (crystaliaEntity.isOnline() && crystaliaEntity.getPlayer().getAllowFlight()) {
            crystaliaEntity.getPlayer().setAllowFlight(false);
            crystaliaEntity.getRole().setNoFall(true);
            Bukkit.getScheduler().runTaskLater(main, () -> {if (crystaliaEntity.getRole() != null) crystaliaEntity.getRole().setNoFall(false);}, 80);
            crystaliaEntity.sendMessage("§cVous avez reçu un dégât, vous ne pouvez donc plus voler.");
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bFire Resistance §fpendant 2 minutes ainsi que de §bvoler §ftant que vous n'avez pas reçu de coup d'épée.";
    }
}
