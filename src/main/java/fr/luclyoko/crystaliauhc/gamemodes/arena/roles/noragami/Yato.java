package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.noragami;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Yato extends ArenaRole {
    Random random;

    public Yato(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.YATO;
        this.random = new Random(System.currentTimeMillis());
        addPermEffect(PotionEffectType.SPEED, 0);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        CrystaliaPlayer crystaliaDamager = main.getPlayerManager().getExactPlayer(player);
        if (crystaliaDamager != crystaliaPlayer) return;

        if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(Yato.this)) return;

        if (!player.getItemInHand().equals(getSword())) return;

        int i = random.nextInt(99);

        Player target = (Player) event.getEntity();
        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
        if (i < 5) {
            if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                crystaliaTarget.getRole().addTempEffect(PotionEffectType.SLOW, 1, 5 * 20, false);
                crystaliaTarget.sendMessage("§eYato §cvous inflige 5 secondes de §bSlowness 2§c.");
                crystaliaPlayer.sendMessage("§aVous infligez 5 secondes de §bSlowness 2 §aà §e" + crystaliaTarget.getPlayerName() + "§a.");
            }
        }
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplayName("§5§lYuki")
                .addEnchant(Enchantment.DAMAGE_ALL, 3)
                .setLore(Collections.singletonList("§8» §7Possède 5% de chances d'infliger 5 secondes de §bSlowness 2 §7à votre adversaire."))
                .setUnbreakable(true)
                .toItemStack();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Votre épée, §5Yuki§f, possède 5% de chances d'infliger 5 secondes de §bSlowness 2 §fà votre adversaire.");
    }
}
