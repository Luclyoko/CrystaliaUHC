package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.plugin.Plugin;

public class HasteyBoys implements Scenario, Listener {
    private boolean isEnabled;

    public HasteyBoys() {
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        this.isEnabled = false;
    }

    public String getName() {
        return "§9HasteyBoys";
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        if (isEnabled)
            ScenariosEnum.HASTEY_BABIES.getScenarioClass().setEnabled(false);
        this.isEnabled = isEnabled;
    }

    @Nullable
    public Class<? extends GuiBuilder> getConfigGui() {
        return null;
    }

    @EventHandler
    public void onPlayerCraft(PrepareItemCraftEvent event) {
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        List<Material> tools = Arrays.asList(Material.WOOD_AXE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.STONE_AXE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.IRON_AXE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.GOLD_AXE,
                Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE);
        Material material = event.getRecipe().getResult().getType();
        if (tools.contains(material))
            event.getInventory().setResult((new ItemBuilder(material))
                    .addEnchant(Enchantment.DIG_SPEED, 3)
                    .addEnchant(Enchantment.DURABILITY, 3)
                    .toItemStack());
    }
}
