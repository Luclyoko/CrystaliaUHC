package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Tengen extends Slayer {

    private boolean vitesse;
    private boolean fumigene;

    public Tengen(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.TENGEN;
        this.vitesse = true;
        this.fumigene = true;
        addPermEffect(PotionEffectType.SPEED, 0);
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            Player player = event.getPlayer();
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Tengen.this)) return;

            if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

            if (event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§bVitesse")) {

                event.setCancelled(true);

                if (!vitesse) {
                    crystaliaPlayer.sendMessage("§cVous ne pouvez pas utiliser la §bVitesse §cpour le moment.");
                    return;
                }

                Optional<Player> nearestPlayer = Optional.empty();
                double lowestDistance = 0;

                for (Entity entity : player.getNearbyEntities(60, 60, 60)) {
                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        if (target != player) {
                            if (main.getPlayerManager().getExactPlayer(target) != null) {
                                CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                                if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                                    Vector towardsEntity = target.getLocation().subtract(player.getLocation()).toVector().normalize();
                                    if (player.getLocation().getDirection().distance(towardsEntity) < 0.1) {
                                        double distance = player.getLocation().distance(target.getLocation());
                                        if (lowestDistance == 0 || distance < lowestDistance) {
                                            lowestDistance = distance;
                                            nearestPlayer = Optional.of(target);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (nearestPlayer.isPresent()) {
                    Player target = nearestPlayer.get();
                    player.teleport(target.getLocation());
                    crystaliaPlayer.sendMessage("§aVous avez été téléporté sur §e" + target.getName() + " §agrâce à votre §bVitesse§a.");
                    target.sendMessage("§eTengen §cs'est téléporté sur vous grâce à sa §bVitesse§c.");
                    vitesse = false;
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Tengen.this)) {
                            crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser votre §bVitesse§a.");
                            vitesse = true;
                        }
                    }, 90*20);
                } else {
                    crystaliaPlayer.sendMessage("§cAucun joueur sur lequel vous téléporter n'a été detecté.");
                }

            } else if (event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§6Fumigène")) {

                event.setCancelled(true);

                if (!fumigene) {
                    crystaliaPlayer.sendMessage("§cVous ne pouvez pas utiliser votre §6Fumigène §cpour le moment.");
                    return;
                }


                for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        if (target != player) {
                            if (main.getPlayerManager().getExactPlayer(target) != null) {
                                CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                                if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                                    crystaliaTarget.getRole().addTempEffect(PotionEffectType.BLINDNESS, 0, 10 * 20, false);
                                    crystaliaTarget.getRole().setInvincible(true);
                                    crystaliaTarget.sendMessage("§cVous avez été touché par le §6Fumigène §cde §eTengen§c.");
                                    Bukkit.getScheduler().runTaskLater(main, () -> crystaliaTarget.getRole().setInvincible(false), 10*20);
                                }
                            }
                        }
                    }
                }

                crystaliaPlayer.sendMessage("§aVous venez d'utiliser votre §6Fumigène.");

                fumigene = false;
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Tengen.this)) {
                        crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser votre §6Fumigène§a.");
                        fumigene = true;
                    }
                }, 120*20);
            }
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Arrays.asList(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§bVitesse").toItemStack(),
                new ItemBuilder(Material.NETHER_STAR).setDisplayName("§6Fumigène").toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Arrays.asList("§b§lVitesse §o(cooldown: 1min30) §8| §7Clic-droit \n§fVous permet de vous téléporter dans le dos d'un joueur que vous visez.",
                "§6§lFumigène §o(cooldown: 2min) §8| §7Clic-droit \n§fVous permet de donner l'effet §bBlindness §fet de rendre §binvincible §fpendant 10 secondes tous les joueurs dans un rayon de 20 blocs autour de vous.");
    }
}
