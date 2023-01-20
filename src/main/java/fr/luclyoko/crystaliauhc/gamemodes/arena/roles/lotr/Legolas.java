package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Legolas extends ArenaRole {

    public Legolas(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.LEGOLAS;
        addPermEffect(PotionEffectType.SPEED, 0);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            Player killer = target.getKiller();
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Legolas.this)) return;
            crystaliaPlayer.giveItem(new ItemBuilder(Material.ARROW).setAmount(16).toItemStack());
        }
    }

    @Override
    public ItemStack getBow() {
        return new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4).setDisplayName("§6§lArc de Legolas").setUnbreakable(true).toItemStack();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Arrays.asList("Vous disposez d'un arc §dPower 4§f.",
                "Chaque kill vous rapporte §d16 flèches§f.");
    }
}
