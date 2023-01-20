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
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class AAssassin extends ArenaRole {
    public AAssassin(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.ASSASSIN;
        addDayEffect(PotionEffectType.INCREASE_DAMAGE, 0);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(AAssassin.this)) return;
            addTempEffect(PotionEffectType.SPEED, 0, 2 * 20 * 60, false);
        }
    }

    @Override
    public ItemStack getChestplate() {
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItemStack();
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 4).setUnbreakable(true).toItemStack();
    }

    @Override
    public ItemStack getBow() {
        return new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).setUnbreakable(true).toItemStack();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Arrays.asList("Vous disposez d'un plastron en diamant §dProtection 3§f, une épée en diamant §dSharpness 4 §fet d'un arc §dPower 3§f.",
                "Vous obtenez 2 minutes de §bSpeed 1 §fà chaque kill.");
    }
}
