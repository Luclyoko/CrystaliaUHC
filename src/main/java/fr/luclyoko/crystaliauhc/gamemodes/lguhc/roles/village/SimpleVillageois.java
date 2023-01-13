package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleVillageois extends LGRoleVillage {
    private SVTypes svType;

    public SimpleVillageois(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        setName("Villageois");
        List<SVTypes> types = new ArrayList<>(Arrays.asList(SVTypes.values()));
        Collections.shuffle(types);
        this.svType = types.get(0);
        this.svType.kit.forEach(crystaliaPlayer::giveItem);
        crystaliaPlayer.sendMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + getShortDescription());
    }

    public String getName() {
        return this.name + " " + this.svType.name;
    }

    public String getPowersDescription() {
        return "Vous disposez d'un kit aléatoire.";
    }

    private enum SVTypes {
        MINEUR("Mineur",
                Collections.singletonList((new ItemBuilder(Material.DIAMOND_PICKAXE)).addEnchant(Enchantment.DIG_SPEED, 2).toItemStack())),
        FORGERON("Forgeron",
                Arrays.asList(new ItemStack[] { (new ItemBuilder(Material.ANVIL)).toItemStack(),
                        (new ItemBuilder(Material.EXP_BOTTLE)).setAmount(10).toItemStack() })),
        LIBRAIRE("Libraire",
                Arrays.asList(new ItemStack[] { (new ItemBuilder(Material.BOOK)).setAmount(8).toItemStack(),
                        (new ItemBuilder(Material.EXP_BOTTLE)).setAmount(10).toItemStack() })),
        GOLEM("Golem",
                Arrays.asList(new ItemStack[] { (new ItemBuilder(Material.IRON_INGOT)).setAmount(15).toItemStack(),
                        (new ItemBuilder(Material.ENCHANTED_BOOK)).setAmount(3).addEnchant(Enchantment.DURABILITY, 3).toItemStack() })),
        ARCHER("Archer",
                Arrays.asList(new ItemStack[] { (new ItemBuilder(Material.STRING)).setAmount(6).toItemStack(),
                        (new ItemBuilder(Material.ARROW)).setAmount(32).toItemStack() })),
        ARMURIER("Armurier",
                Collections.singletonList((new ItemBuilder(Material.DIAMOND_BOOTS)).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack())),
        PRETRE("Prêtre",
                Collections.singletonList((new ItemBuilder(Material.GOLDEN_APPLE)).setAmount(3).toItemStack()));

        private String name;

        private List<ItemStack> kit;

        SVTypes(String name, List<ItemStack> kit) {
            this.name = name;
            this.kit = kit;
        }
    }
}
