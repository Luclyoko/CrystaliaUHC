package fr.luclyoko.crystaliauhc.gamemodes.arena.inventories;

import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InventoryManager {

    private ArenaUHC arenaUHC;

    private HashMap<CrystaliaPlayer, InventoryConfig> inventoryMap;

    public InventoryManager(ArenaUHC arenaUHC) {
        this.arenaUHC = arenaUHC;
        this.inventoryMap = new HashMap<>();
    }

    public void fillInventory(CrystaliaPlayer crystaliaPlayer) {
        InventoryConfig inventoryConfig;
        if (inventoryMap.containsKey(crystaliaPlayer)) {
            inventoryConfig = inventoryMap.get(crystaliaPlayer);
        } else {
            inventoryConfig = new InventoryConfig(crystaliaPlayer);
            this.inventoryMap.put(crystaliaPlayer, inventoryConfig);
        }
        Player player = crystaliaPlayer.getPlayer();
        ArenaRole arenaRole = (ArenaRole) crystaliaPlayer.getRole();
        player.getInventory().setHelmet(arenaRole.getHelmet());
        player.getInventory().setChestplate(arenaRole.getChestplate());
        player.getInventory().setLeggings(arenaRole.getLeggings());
        player.getInventory().setBoots(arenaRole.getBoots());
        player.getInventory().setItem(inventoryConfig.getSword(), arenaRole.getSword());
        player.getInventory().setItem(inventoryConfig.getWater1(), new ItemBuilder(Material.WATER_BUCKET).toItemStack());
        player.getInventory().setItem(inventoryConfig.getWater2(), new ItemBuilder(Material.WATER_BUCKET).toItemStack());
        player.getInventory().setItem(inventoryConfig.getBow(), arenaRole.getBow());
        player.getInventory().setItem(inventoryConfig.getBlocs1(), new ItemBuilder(Material.COBBLESTONE).setAmount(64).toItemStack());
        player.getInventory().setItem(inventoryConfig.getGapples(), new ItemBuilder(Material.GOLDEN_APPLE).setAmount(12).toItemStack());
        player.getInventory().setItem(inventoryConfig.getCarrots(), new ItemBuilder(Material.GOLDEN_CARROT).setAmount(64).toItemStack());
        player.getInventory().setItem(inventoryConfig.getLava1(), new ItemBuilder(Material.LAVA_BUCKET).toItemStack());
        player.getInventory().setItem(inventoryConfig.getLava2(), new ItemBuilder(Material.LAVA_BUCKET).toItemStack());
        player.getInventory().setItem(inventoryConfig.getArrows(), new ItemBuilder(Material.ARROW).setAmount(64).toItemStack());
        player.getInventory().setItem(inventoryConfig.getBlocs2(), new ItemBuilder(Material.COBBLESTONE).setAmount(64).toItemStack());
        player.getInventory().setItem(inventoryConfig.getBlocs3(), new ItemBuilder(Material.COBBLESTONE).setAmount(64).toItemStack());
        player.getInventory().setItem(inventoryConfig.getBlocs4(), new ItemBuilder(Material.COBBLESTONE).setAmount(64).toItemStack());
    }

    public void saveInventory(CrystaliaPlayer crystaliaPlayer) {
        if (!inventoryMap.containsKey(crystaliaPlayer)) {
            crystaliaPlayer.sendMessage("§cErreur: Aucune configuration actuellement enregistrée!");
            return;
        }
        inventoryMap.get(crystaliaPlayer).saveActualInventory(crystaliaPlayer);
        crystaliaPlayer.sendMessage("§aInventaire sauvegardé avec succès!");
    }
}
