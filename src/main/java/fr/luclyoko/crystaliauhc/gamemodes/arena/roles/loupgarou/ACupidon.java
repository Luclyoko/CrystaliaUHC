package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.loupgarou;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ACupidon extends ArenaRole {

    private boolean hasUpgradedBow;

    public ACupidon(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.CUPIDON;
        this.hasUpgradedBow = false;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            Player killer = target.getKiller();
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(ACupidon.this)) return;
            if (hasUpgradedBow) return;
            if (kills >= 5) {
                if (!killer.getInventory().contains(Material.BOW)) return;
                int slot = killer.getInventory().first(Material.BOW);
                killer.getInventory().setItem(slot, new ItemBuilder(Material.BOW).setDisplayName("§6§lArc du Cupidon (amélioré)").addEnchant(Enchantment.ARROW_DAMAGE, 3).addEnchant(Enchantment.ARROW_KNOCKBACK, 2).toItemStack());
                hasUpgradedBow = true;
            }
        }
    }

    @Override
    public ItemStack getBow() {
        return new ItemBuilder(Material.BOW).setDisplayName("§6§lArc du Cupidon").addEnchant(Enchantment.ARROW_DAMAGE, 3).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).toItemStack();
    }

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.ARROW).setAmount(32).toItemStack());
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Arrays.asList("Vous disposez d'un arc enchanté §dPower 3 §fet §dPunch 1§f ainsi que de §d32 flèches §fsupplémentaires.",
                "Au bout de 5 kills, votre arc sera amélioré avec l'enchantement §dPunch 2§f.");
    }
}
