package fr.luclyoko.crystaliauhc.gamemodes.arena.inventories;

import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryConfig {

    private CrystaliaPlayer crystaliaPlayer;

    private int sword;
    private int bow;

    private int gapples;
    private int carrots;

    private int blocs1;
    private int blocs2;
    private int blocs3;
    private int blocs4;

    private int water1;
    private int water2;

    private int lava1;
    private int lava2;

    private int arrows;

    public InventoryConfig(CrystaliaPlayer crystaliaPlayer) {
        this.crystaliaPlayer = crystaliaPlayer;
        this.sword = 0;
        this.water1 = 1;
        this.water2 = 2;
        this.bow = 3;
        this.blocs1 = 4;
        this.gapples = 5;
        this.carrots = 6;
        this.lava1 = 7;
        this.lava2 = 8;
        this.arrows = 9;
        this.blocs2 = 13;
        this.blocs3 = 22;
        this.blocs4 = 31;
    }

    public void saveActualInventory(CrystaliaPlayer crystaliaPlayer) {
        Player player = crystaliaPlayer.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        boolean cancel = false;

        if (!playerInventory.contains(Material.DIAMOND_SWORD)) cancel = true;
        if (!playerInventory.contains(Material.BOW)) cancel = true;
        if (!playerInventory.contains(Material.GOLDEN_APPLE)) cancel = true;
        if (!playerInventory.contains(Material.GOLDEN_CARROT)) cancel = true;
        if (!playerInventory.contains(Material.ARROW)) cancel = true;
        List<Integer> cobblestoneSlots = new ArrayList<>(playerInventory.all(Material.COBBLESTONE).keySet());
        if (cobblestoneSlots.size() < 4) cancel = true;
        List<Integer> waterBucketsSlots = new ArrayList<>(playerInventory.all(Material.WATER_BUCKET).keySet());
        if (waterBucketsSlots.size() < 2) cancel = true;
        List<Integer> lavaBucketsSlots = new ArrayList<>(playerInventory.all(Material.LAVA_BUCKET).keySet());
        if (lavaBucketsSlots.size() < 2) cancel = true;

        if (cancel) {
            crystaliaPlayer.sendMessage("§cErreur: Votre inventaire actuel ne peut être sauvegardé! Veuillez vérifier que vous possédez bien tous les items de l'inventaire en quantités suffisantes.");
            return;
        }
        this.sword = playerInventory.first(Material.DIAMOND_SWORD);
        this.bow = playerInventory.first(Material.BOW);
        this.gapples = playerInventory.first(Material.GOLDEN_APPLE);
        this.carrots = playerInventory.first(Material.GOLDEN_CARROT);
        this.arrows = playerInventory.first(Material.ARROW);
        this.blocs1 = cobblestoneSlots.get(0);
        this.blocs2 = cobblestoneSlots.get(1);
        this.blocs3 = cobblestoneSlots.get(2);
        this.blocs4 = cobblestoneSlots.get(3);
        this.water1 = waterBucketsSlots.get(0);
        this.water2 = waterBucketsSlots.get(1);
        this.lava1 = lavaBucketsSlots.get(0);
        this.lava2 = lavaBucketsSlots.get(1);
    }

    public int getSword() {
        return sword;
    }

    public int getBow() {
        return bow;
    }

    public int getGapples() {
        return gapples;
    }

    public int getCarrots() {
        return carrots;
    }

    public int getArrows() {
        return arrows;
    }

    public int getBlocs1() {
        return blocs1;
    }

    public int getBlocs2() {
        return blocs2;
    }

    public int getBlocs3() {
        return blocs3;
    }

    public int getBlocs4() {
        return blocs4;
    }

    public int getWater1() {
        return water1;
    }

    public int getWater2() {
        return water2;
    }

    public int getLava1() {
        return lava1;
    }

    public int getLava2() {
        return lava2;
    }
}
