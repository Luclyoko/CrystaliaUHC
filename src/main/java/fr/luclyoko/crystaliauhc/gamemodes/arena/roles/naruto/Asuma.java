package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.naruto;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.FieryClouds;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Asuma extends ArenaRole {
    public Asuma(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.ASUMA;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        addPower(new FieryClouds(getArenaUHC(), this, main));
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("§dÉpée d'Asuma")
                .addEnchant(Enchantment.DAMAGE_ALL, 5)
                .setUnbreakable(true)
                .toItemStack();
    }
}
