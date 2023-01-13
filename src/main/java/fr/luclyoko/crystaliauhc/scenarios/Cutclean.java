package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Cutclean implements Scenario, Listener {
    private boolean isEnabled;

    public Cutclean() {
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        this.isEnabled = false;
    }

    public String getName() {
        return "§6Cutclean";
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Nullable
    public Class<? extends GuiBuilder> getConfigGui() {
        return null;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        List<Material> authorizedTools;
        ItemStack itemToDrop;
        if (event.isCancelled())
            return;
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        Block block = event.getBlock();
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int expToDrop = 0;
        switch (block.getType()) {
            case STONE:
                authorizedTools = Arrays.asList(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
                itemToDrop = (new ItemBuilder(Material.COBBLESTONE)).toItemStack();
                break;
            case IRON_ORE:
                authorizedTools = Arrays.asList(Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
                expToDrop = 2;
                itemToDrop = (new ItemBuilder(Material.IRON_INGOT)).toItemStack();
                break;
            case GOLD_ORE:
                authorizedTools = Arrays.asList(Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
                expToDrop = 3;
                itemToDrop = (new ItemBuilder(Material.GOLD_INGOT)).toItemStack();
                break;
            case COAL_ORE:
                authorizedTools = Arrays.asList(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
                expToDrop = (new Random()).nextInt(2);
                itemToDrop = (new ItemBuilder(Material.TORCH)).setAmount(4).toItemStack();
                break;
            default:
                return;
        }
        event.setCancelled(true);
        block.breakNaturally((new ItemBuilder(Material.AIR)).toItemStack());
        if (authorizedTools.stream().anyMatch(material -> player.getItemInHand().getType().equals(material))) {
            block.getWorld().dropItem(block.getLocation().add(0.5D, 0.5D, 0.5D), itemToDrop)
                    .setVelocity(new Vector(0.0D, 0.0D, 0.0D));
            if (expToDrop > 0)
                ((ExperienceOrb)block.getWorld().spawn(block.getLocation().add(0.5D, 0.5D, 0.5D), ExperienceOrb.class)).setExperience(expToDrop);
        }
    }
}
