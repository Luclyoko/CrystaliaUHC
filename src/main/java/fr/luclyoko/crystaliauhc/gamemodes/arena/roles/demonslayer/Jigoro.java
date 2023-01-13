package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Jigoro extends Slayer {

    private boolean availablePower;

    public Jigoro(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.JIGORO;
        this.availablePower = true;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Jigoro.this)) return;

            if (!(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§eSouffle de la Foudre"))) return;

            if (!availablePower) {
                crystaliaPlayer.sendMessage("§cVous ne pouvez pas utiliser le §eSouffle de la Foudre §cpour le moment.");
                return;
            }

            crystaliaPlayer.sendMessage("§aVous venez d'activer votre §eSouffle de la Foudre§a.");
            addTempEffect(PotionEffectType.SPEED, 2, 60*20, false);
            availablePower = false;

            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Jigoro.this) && crystaliaPlayer.isOnline()) {
                    crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser le §eSouffle de la Foudre§a.");
                    availablePower = true;
                }
            }, 20*5*60);
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§eSouffle de la Foudre").addEnchant(Enchantment.DURABILITY, 10).addItemFlags(ItemFlag.HIDE_ENCHANTS).toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Collections.singletonList("§e§lSouffle de la Foudre §o(cooldown: 4 minutes) §8| §7Clic-droit : §fVous permet d'obtenir l'effet §bSpeed 3 §fpendant 1 minute.");
    }
}
