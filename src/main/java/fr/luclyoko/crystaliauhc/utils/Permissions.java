package fr.luclyoko.crystaliauhc.utils;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Permissions {

    private Main main;
    private final Map<UUID, PermissionAttachment> attachmentMap;

    public Permissions(Main main) {
        this.main = main;
        this.attachmentMap = new HashMap<>();
    }

    public void grantPermission(Player player, String permission) {
        UUID playerUUID = player.getUniqueId();

        if (!attachmentMap.containsKey(playerUUID)) {
            attachmentMap.put(playerUUID, player.addAttachment(main));
        }

        attachmentMap.get(playerUUID).setPermission(permission, true);
    }

    public void removePermission(Player player, String permission) {
        UUID playerUUID = player.getUniqueId();

        if (!attachmentMap.containsKey(playerUUID)) {
            attachmentMap.put(playerUUID, player.addAttachment(main));
        }

        attachmentMap.get(playerUUID).setPermission(permission, false);
    }
}
