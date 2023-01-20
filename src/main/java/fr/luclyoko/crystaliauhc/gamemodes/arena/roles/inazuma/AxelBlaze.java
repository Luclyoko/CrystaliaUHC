package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.FireTornado;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AxelBlaze extends ArenaRole {

    Random random;

    public AxelBlaze(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.AXEL_BLAZE;
        this.random = new Random(System.currentTimeMillis());
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addPower(new FireTornado(getArenaUHC(), this, main));
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        CrystaliaPlayer crystaliaDamager = main.getPlayerManager().getExactPlayer((Player) event.getDamager());
        if (crystaliaDamager != crystaliaPlayer) return;

        if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(AxelBlaze.this)) return;

        int i = random.nextInt(99);

        Player target = (Player) event.getEntity();
        if (i < 10) {
            target.setFireTicks(100);
            target.sendMessage("§cVous avez été enflammé par §eAxel Blaze§c.");
            crystaliaPlayer.sendMessage("§aVous §benflammez §e" + target.getName() + "§a.");
        }
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous avez 10% de chance d'§benflammer §fvos adversaires lorsque vous les frappez.");
    }
}
