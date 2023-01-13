package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.RoleGamemodeEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarkEvans extends ArenaRole {

    private boolean availablePower;

    public MarkEvans(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.MARK_EVANS;
        this.availablePower = true;
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        main.getServer().getPluginManager().registerEvents(this.listener, main);
    }

    Listener listener = new Listener() {
        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerDeath(PlayerDeathEvent event) {
            Player player = event.getEntity();
            CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(player);
            if (crystaliaTarget == crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(MarkEvans.this)) return;
            if (crystaliaTarget.getRole() instanceof ArenaRole) {
                ArenaRole arenaRole = (ArenaRole) crystaliaTarget.getRole();
                if (arenaRole.getArenaRolesEnum().getGamemodeEnum().equals(RoleGamemodeEnum.INAZUMA)) {
                    removeMaxHealth(2);
                    crystaliaPlayer.sendMessage("§cUn rôle de l'univers Inazuma Eleven est mort, vous perdez donc un coeur permanent.");
                }
            }
        }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            CrystaliaPlayer crystaliaPlayer1 = main.getPlayerManager().getExactPlayer(event.getPlayer());
            if (crystaliaPlayer1 != crystaliaPlayer) return;

            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(MarkEvans.this)) return;

            if (!(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR) && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§eMain Céleste"))) return;

            if (!availablePower) {
                crystaliaPlayer.sendMessage("§cVous ne pouvez pas utiliser la §eMain Céleste §cpour le moment.");
                return;
            }

            crystaliaPlayer.sendMessage("§aVous venez d'utiliser la §eMain Céleste§a.");
            addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 1, 60*20, false);
            availablePower = false;
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(MarkEvans.this)) {
                    crystaliaPlayer.sendMessage("§aVous pouvez de nouveau utiliser votre §eMain Céleste§a.");
                    availablePower = true;
                }
            }, 300*20);
        }
    };

    @Override
    public List<ItemStack> getRoleItems() {
        return Collections.singletonList(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§eMain Céleste").toItemStack());
    }

    @Override
    public List<String> getPowersDescriptionList() {
        return Arrays.asList("§e§lMain Céleste §o(cooldown: 4min) §8| §7Clic-droit : §fVous permet d'obtenir l'effet §bResistance 2 §fpendant 1 minute.",
                "Vous perdez un coeur permanent à la mort des rôles de l'univers d'Inazuma Eleven.");
    }
}
