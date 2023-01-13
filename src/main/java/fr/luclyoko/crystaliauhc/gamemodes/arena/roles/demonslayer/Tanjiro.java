package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tanjiro extends Slayer {

    private boolean availablePower;
    private boolean activeDance;

    private boolean assassinKilled;

    public Tanjiro(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.TANJIRO;
        this.availablePower = true;
        this.activeDance = false;
        this.assassinKilled = false;
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {
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
                        crystaliaPlayer.sendMessage("§aVous venez de tuer l'assassin de votre famille, vous gagnez donc l'effet §lStrength 1 §apermanent.");
                    }
                }
            }
        }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Tanjiro.this)) return;

            if (!(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§6Danse du Dieu du Feu"))) return;

            if (!availablePower) {
                crystaliaPlayer.sendMessage("§cVous ne pouvez pas utiliser la §6Danse du Dieu du Feu §cpour le moment.");
                return;
            }

            crystaliaPlayer.sendMessage("§aVous venez d'utiliser la §6Danse du Dieu du Feu§a.");
            addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 120*20, false);
            addTempEffect(PotionEffectType.SPEED, 0, 120*20, false);
            activeDance = true;
            availablePower = false;
            Bukkit.getScheduler().runTaskLater(main, () -> activeDance = false, 120*20);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Tanjiro.this)) {
                    crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser votre §6Danse du Dieu du Feu§a.");
                    availablePower = true;
                }
            }, 500*20);
        }

        @EventHandler
        public void onPlayerHit(EntityDamageByEntityEvent event) {
            if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
            CrystaliaPlayer crystaliaDamager = main.getPlayerManager().getExactPlayer((Player)event.getDamager());
            if (crystaliaDamager != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Tanjiro.this)) return;
            if (activeDance) {
                Player target = (Player) event.getEntity();
                target.setFireTicks(100);
            }
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§6Danse du Dieu du Feu").toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Arrays.asList("§6§lDanse du Dieu du Feu §o(cooldown: 3min) §8| §7Clic-droit : §fVous permet d'obtenir les effets §bSpeed 1 §fet §bResistance 1 §fpendant 2 minutes.",
                "Si vous parvenez à tuer le démon §c§oassassin§f, vous gagnerez l'effet §bStrength 1 §fpermanent.");
    }
}
