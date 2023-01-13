package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.TeleportationTask;
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

public class Sanemi extends Slayer {

    private boolean availablePower;

    public Sanemi(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.SANEMI;
        this.availablePower = true;
        addDayEffect(PotionEffectType.SPEED, 0);
        addNightEffect(PotionEffectType.SPEED, 1);
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Sanemi.this)) return;

            if (!(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§eDispersion"))) return;

            if (!availablePower) {
                crystaliaPlayer.sendMessage("§cVous ne pouvez plus utiliser la §eDispersion§c.");
                return;
            }

            Bukkit.broadcastMessage("§eSanemi §ca décidé de disperser tous les joueurs sur la carte.");
            removeMaxHealth(4);
            main.getPlayerManager().getOnlineAlivePlayers().stream().filter(crystaliaPlayer2 -> crystaliaPlayer2 != crystaliaPlayer).forEach(target -> {
                target.getRole().setNoFall(true);
                target.getPlayer().teleport(TeleportationTask.generateRandomLocation());
                Bukkit.getScheduler().runTaskLater(main, () -> target.getRole().setNoFall(false), 60);
            });
            availablePower = false;
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§eDispersion").addEnchant(Enchantment.DURABILITY, 10).addItemFlags(ItemFlag.HIDE_ENCHANTS).toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Collections.singletonList("§e§lDispersion §o(usage unique) §8| §7Clic-droit : §fVous permet de téléporter aléatoirement sur la carte l'ensemble des joueurs en échange de §c2❤ permanents§f.");
    }
}
