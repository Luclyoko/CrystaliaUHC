package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public abstract class Demon extends ArenaRole {

    Random random;

    boolean isAssassin;

    public Demon(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        if (gameManager.getGamemodeUhc() instanceof ArenaUHC && !((ArenaUHC) gameManager.getGamemodeUhc()).getArenaRolesManager().isAliveAssassin()) {
            random = new Random();
            int i = random.nextInt(99);
            int rate = 10;
            if (((ArenaUHC) gameManager.getGamemodeUhc()).getArenaRolesManager().isPlayingRole(ArenaRolesEnum.TANJIRO))
                rate = 40;
            if (i < rate) {
                setAssassin(true);
                addMaxHealth(4);
                heal();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(target);
        if (crystaliaPlayer1 != crystaliaPlayer) return;
        if (crystaliaPlayer.getRole() == null || !(crystaliaPlayer.getRole() instanceof Demon)) return;
        if (!((Demon) crystaliaPlayer.getRole()).isAssassin()) return;
        ((ArenaUHC) gameManager.getGamemodeUhc()).getArenaRolesManager().setAliveAssassin(false);
    }

    @Override
    public String getName() {
        if (arenaRolesEnum != null) {
            return arenaRolesEnum.getName() + (isAssassin ? " §c§o(assassin)" : "");
        }
        return "Nom introuvable";
    }

    public boolean isAssassin() {
        return isAssassin;
    }

    public void setAssassin(boolean assassin) {
        if (assassin) ((ArenaUHC) gameManager.getGamemodeUhc()).getArenaRolesManager().setAliveAssassin(true);
        isAssassin = assassin;
    }
}
