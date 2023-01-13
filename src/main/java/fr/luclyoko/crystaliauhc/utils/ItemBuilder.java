package fr.luclyoko.crystaliauhc.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

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
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, byte data) {
        this.itemStack = new ItemStack(material, 1, (short)data);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
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

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(new ItemFlag[] { itemFlag });
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

    public ItemBuilder setDefaultBannerColor(DyeColor color) {
        if (this.itemStack.getType().equals(Material.BANNER)) {
            BannerMeta bannerMeta = (BannerMeta)this.itemMeta;
            bannerMeta.setBaseColor(color);
        }
        return this;
    }

    public ItemBuilder setBannerPatterns(List<Pattern> pattern) {
        if (this.itemStack.getType().equals(Material.BANNER)) {
            BannerMeta bannerMeta = (BannerMeta)this.itemMeta;
            bannerMeta.setPatterns(pattern);
            this.itemMeta = (ItemMeta)bannerMeta;
        }
        return this;
    }

    public BannerMeta getBannerMeta() {
        if (this.itemStack.getType().equals(Material.BANNER))
            return (BannerMeta)this.itemMeta;
        return null;
    }

    public ItemBuilder setPotionMeta(PotionType potionType, boolean splash, int level, boolean extended) {
        if (this.itemStack.getType().equals(Material.POTION)) {
            Potion potion = new Potion(potionType);
            potion.setSplash(splash);
            potion.setLevel(level);
            if (!potionType.isInstant())
                potion.setHasExtendedDuration(extended);
            potion.apply(this.itemStack);
        }
        return this;
    }

    public ItemBuilder setPotionMeta(PotionType potionType, boolean splash, int level) {
        return setPotionMeta(potionType, splash, level, false);
    }

    public ItemBuilder setPotionMeta(PotionType potionType, boolean splash) {
        return setPotionMeta(potionType, splash, 1, false);
    }

    public static ItemStack skullFromName(@Nonnegative @Nonnull Integer amount, @Nullable String displayName, @Nullable List<String> lore, @Nullable HashMap<Enchantment, Integer> enchantments, @Nullable List<ItemFlag> itemFlags, @Nullable String owner) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        if (owner != null)
            meta.setOwner(owner);
        if (displayName != null)
            meta.setDisplayName(displayName);
        if (lore != null && !lore.isEmpty())
            meta.setLore(lore);
        if (enchantments != null && !enchantments.isEmpty())
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        if (itemFlags != null && !itemFlags.isEmpty())
            itemFlags.forEach(meta::addItemFlags);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }

    public static ItemStack skullFromURL(@Nonnegative @Nonnull Integer amount, @Nullable String displayName, @Nullable List<String> lore, @Nullable HashMap<Enchantment, Integer> enchantments, @Nullable List<ItemFlag> itemFlags, @Nullable String url) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        if (url != null && !url.isEmpty()) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("texture", url));
            try {
                Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException|SecurityException|IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (displayName != null)
            meta.setDisplayName(displayName);
        if (lore != null && !lore.isEmpty())
            meta.setLore(lore);
        if (enchantments != null && !enchantments.isEmpty())
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        if (itemFlags != null && !itemFlags.isEmpty())
            itemFlags.forEach(meta::addItemFlags);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }
}
