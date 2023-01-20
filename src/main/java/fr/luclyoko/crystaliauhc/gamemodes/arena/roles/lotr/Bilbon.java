package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Bilbon extends ArenaRole {
    public Bilbon(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.BILBON;
        addPermEffect(PotionEffectType.SPEED, 0);
        removeMaxHealth(4);
    }

    @Override
    public boolean isNoFall() {
        return true;
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplayName("§6§lÉpée de Bilbon")
                .addEnchant(Enchantment.DAMAGE_ALL, 4)
                .setUnbreakable(true)
                .toItemStack();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous possédez §bNoFall §fde manière permanente ainsi qu'une épée §dSharpness 4§f.");
    }
}
