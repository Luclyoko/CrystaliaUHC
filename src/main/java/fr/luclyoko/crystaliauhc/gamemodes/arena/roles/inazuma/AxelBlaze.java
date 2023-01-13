package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AxelBlaze extends ArenaRole {

    boolean availablePower;

    Random random;

    public AxelBlaze(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.AXEL_BLAZE;
        this.availablePower = true;
        this.random = new Random(System.currentTimeMillis());
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || crystaliaPlayer.getRole() != AxelBlaze.this) return;

            if (!(event.getPlayer().getItemInHand().getType().equals(Material.MAGMA_CREAM) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§6Tornade de Feu"))) return;

            if (!availablePower) {
                crystaliaPlayer.sendMessage("§cVous ne pouvez pas encore utiliser la §6Tornade de Feu §cpour le moment.");
                return;
            }

            addTempEffect(PotionEffectType.FIRE_RESISTANCE, 0, 2*60*20, false);
            crystaliaPlayer.getPlayer().setAllowFlight(true);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(AxelBlaze.this) && crystaliaPlayer.isOnline() && crystaliaPlayer.getPlayer().getAllowFlight()) crystaliaPlayer.getPlayer().setAllowFlight(false);
            }, 20*2*60);
            availablePower = false;

            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(AxelBlaze.this) && crystaliaPlayer.isOnline()) {
                    crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser la §6Tornade de Feu§a.");
                    availablePower = true;
                }
            }, 20*5*60);
        }

        @EventHandler
        public void onPlayerHit(EntityDamageByEntityEvent event) {
            if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
            CrystaliaPlayer crystaliaDamager = main.getPlayerManager().getExactPlayer((Player)event.getDamager());
            if (crystaliaDamager != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(AxelBlaze.this)) return;

            int i = random.nextInt(99);

            Player target = (Player) event.getEntity();
            if (i < 15) {
                target.setFireTicks(100);
                target.sendMessage("§cVous avez été enflammé par §eAxel Blaze§c.");
            }
        }

        @EventHandler
        public void onPlayerDamage(EntityDamageEvent event) {
            if (!(event.getEntity() instanceof Player)) return;
            CrystaliaPlayer crystaliaEntity = main.getPlayerManager().getExactPlayer((Player) event.getEntity());
            if (crystaliaEntity != crystaliaPlayer) return;

            if (crystaliaPlayer.isOnline() && crystaliaPlayer.getPlayer().getAllowFlight()) {
                crystaliaPlayer.getPlayer().setAllowFlight(false);
                if (crystaliaPlayer.getRole() != null) crystaliaPlayer.getRole().setNoFall(true);
                Bukkit.getScheduler().runTaskLater(main, () -> {if (crystaliaPlayer.getRole() != null) crystaliaPlayer.getRole().setNoFall(false);}, 80);
                crystaliaPlayer.sendMessage("§cVous avez reçu un dégât, vous ne pouvez donc plus voler.");
            }
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§6Tornade de Feu").addEnchant(Enchantment.DURABILITY, 10).addItemFlags(ItemFlag.HIDE_ENCHANTS).toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Arrays.asList("§6§lTornade de Feu §o(cooldown: 3 minutes) §8| §7Clic-droit \n§fVous permet d'obtenir l'effet §bFire Resistance §fpendant 2 minutes ainsi que de §bvoler §ftant que vous n'avez pas reçu de coup d'épée.",
                "Vous avez 10% de chance d'§benflammer §fvos adversaires lorsque vous les frappez.");
    }
}
