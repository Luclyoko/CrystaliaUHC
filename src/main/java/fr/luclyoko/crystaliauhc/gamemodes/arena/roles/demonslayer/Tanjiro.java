package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.FireGodDance;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Tanjiro extends Slayer {

    private boolean assassinKilled;

    public Tanjiro(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.TANJIRO;
        this.assassinKilled = false;
        addPower(new FireGodDance(getArenaUHC(), this, main));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerKill(PlayerDeathEvent event) {
        if (assassinKilled) return;
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Tanjiro.this)) return;
            CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
            if (crystaliaTarget.getRole() instanceof Demon) {
                Demon deadRole = (Demon) crystaliaTarget.getRole();
                if (deadRole.isAssassin()) {
                    addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
                    assassinKilled = true;
                    crystaliaPlayer.sendMessage("§aVous venez de tuer l'assassin de votre famille, vous gagnez donc l'effet §bStrength 1 §apermanent.");
                }
            }
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Si vous parvenez à tuer le démon §c§oassassin§f, vous gagnerez l'effet §bStrength 1 §fpermanent.");
    }
}
