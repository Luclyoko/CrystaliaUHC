package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaRolesManager {

    Main main;

    GameManager gameManager;

    boolean aliveAssassin;

    List<ArenaRolesEnum> arenaRolesEnums;
    List<ArenaRolesEnum> playingRoles;

    Map<CrystaliaPlayer, ArenaRolesEnum> forcedRoles;
    boolean forceRoles;

    public ArenaRolesManager(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.aliveAssassin = false;
        this.forcedRoles = new HashMap<>();
        this.forceRoles = true;
        this.arenaRolesEnums = Arrays.asList(ArenaRolesEnum.values());
        this.playingRoles = new ArrayList<>();
    }

    public void pickRole(CrystaliaPlayer crystaliaPlayer, boolean sendDesc) {
        List<ArenaRolesEnum> availableRoles = arenaRolesEnums.stream().filter(rolesEnum -> !playingRoles.contains(rolesEnum)).filter(rolesEnum -> !forcedRoles.containsValue(rolesEnum)).collect(Collectors.toList());
        Collections.shuffle(availableRoles);
        Random random = new Random();
        ArenaRolesEnum pickedRole = availableRoles.get(random.nextInt(availableRoles.size() - 1));
        if (forceRoles && forcedRoles.containsKey(crystaliaPlayer)) {
            pickedRole = forcedRoles.get(crystaliaPlayer);
            removeForcedRole(crystaliaPlayer);
        }
        Class[] classArgs = new Class[2];
        classArgs[0] = GameManager.class;
        classArgs[1] = CrystaliaPlayer.class;
        try {
            crystaliaPlayer.setRole(pickedRole.getRoleClass().getDeclaredConstructor(classArgs).newInstance(this.gameManager, crystaliaPlayer));
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole) ((ArenaRole)crystaliaPlayer.getRole()).setSilent(!sendDesc);
            if (sendDesc) crystaliaPlayer.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + ((ArenaRole)crystaliaPlayer.getRole()).getDescription());
            addPlayingRole(pickedRole);
        } catch (InstantiationException|IllegalAccessException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void addPlayingRole(ArenaRolesEnum rolesEnum) {
        this.playingRoles.add(rolesEnum);
    }

    public void removePlayingRole(ArenaRolesEnum rolesEnum) {
        this.playingRoles.remove(rolesEnum);
    }

    public boolean isPlayingRole(ArenaRolesEnum rolesEnum) {
        return this.playingRoles.contains(rolesEnum);
    }

    public void addForcedRole(CrystaliaPlayer crystaliaPlayer, ArenaRolesEnum rolesEnum) {
        this.forcedRoles.put(crystaliaPlayer, rolesEnum);
    }

    public void removeForcedRole(CrystaliaPlayer crystaliaPlayer) {
        this.forcedRoles.remove(crystaliaPlayer);
    }

    public boolean isForceRoles() {
        return forceRoles;
    }

    public void setForceRoles(boolean forceRoles) {
        this.forceRoles = forceRoles;
    }

    public boolean isAliveAssassin() {
        return aliveAssassin;
    }

    public void setAliveAssassin(boolean aliveAssassin) {
        this.aliveAssassin = aliveAssassin;
    }
}
