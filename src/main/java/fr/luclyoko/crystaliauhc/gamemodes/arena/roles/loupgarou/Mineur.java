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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mineur extends ArenaRole {

    public Mineur(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.MINEUR;
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {
        @EventHandler
        public void onPlayerKill(PlayerDeathEvent event) {
            Player target = event.getEntity();
            if (target.getKiller() != null) {
                Player killer = target.getKiller();
                CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
                if (crystaliaKiller != crystaliaPlayer) return;
                if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Mineur.this)) return;
                int gaps = 0;
                for (ItemStack itemStack : target.getInventory().getContents()) {
                    if (itemStack != null && itemStack.getType().equals(Material.GOLDEN_APPLE)) gaps += itemStack.getAmount();
                }
                if (gaps > 3) crystaliaPlayer.giveItem(new ItemBuilder(Material.GOLDEN_APPLE).setAmount(gaps - 3).toItemStack());
            }
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayName("§6§lPioche du Mineur").addEnchant(Enchantment.DIG_SPEED, 4).addEnchant(Enchantment.DURABILITY, 3).toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Arrays.asList("Vous disposez d'une pioche en diamant enchantée §dEfficiency 4 §fet §dUnbreaking 3§f.",
                "Lorsque vous tuez un joueur, vous récupérez l'intégralité de ses pommes en or (minimum 3 pommes en or).");
    }
}
