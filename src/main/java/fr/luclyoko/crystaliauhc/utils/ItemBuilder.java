package fr.luclyoko.crystaliauhc.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, byte data) {
        this.itemStack = new ItemStack(material, 1, data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemStack toItemStack() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

    public static ItemStack skullFromName(
            @Nonnegative @Nonnull Integer amount,
            @Nullable String displayName,
            @Nullable List<String> lore,
            @Nullable HashMap<Enchantment, Integer> enchantments,
            @Nullable List<ItemFlag> itemFlags,
            @Nullable String owner
    ) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if (owner != null) meta.setOwner(owner);

        if (displayName != null) meta.setDisplayName(displayName);

        if (lore != null && !lore.isEmpty()) meta.setLore(lore);

        if (enchantments != null && !enchantments.isEmpty())
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));

        if (itemFlags != null && !itemFlags.isEmpty()) itemFlags.forEach(meta::addItemFlags);

        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack skullFromURL(
            @Nonnegative @Nonnull Integer amount,
            @Nullable String displayName,
            @Nullable List<String> lore,
            @Nullable HashMap<Enchantment, Integer> enchantments,
            @Nullable List<ItemFlag> itemFlags,
            @Nullable String url
    ) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if (url != null && !url.isEmpty()) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("texture", url));
            try {
                Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (displayName != null) meta.setDisplayName(displayName);

        if (lore != null && !lore.isEmpty()) meta.setLore(lore);

        if (enchantments != null && !enchantments.isEmpty())
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));

        if (itemFlags != null && !itemFlags.isEmpty()) itemFlags.forEach(meta::addItemFlags);

        skull.setItemMeta(meta);

        return skull;
    }
}