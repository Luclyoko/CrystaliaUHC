package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.AragornCrown;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.FightingSpirit;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class Aragorn extends ArenaRole {
    public Aragorn(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.ARAGORN;
        addPower(new FightingSpirit(getArenaUHC(), this, main));
        addPower(new AragornCrown(getArenaUHC(), this, main));
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplayName("§6§lÉpée d'Aragorn")
                .addEnchant(Enchantment.DAMAGE_ALL, 4)
                .setUnbreakable(true)
                .toItemStack();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous possédez une épée §dSharpness 4§f.");
    }
}
