package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGRevealListeLoupsEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.timers.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class LGRolesManager implements Timer {
    Main main;

    GameManager gameManager;

    int rolesTime;

    List<Integer> remindTimers;

    boolean roles;

    boolean blindCompo;

    Map<LGRolesEnum, Integer> composition;

    public LGRolesManager(Main main, GameManager gameManager, int rolesTime) {
        this.main = main;
        this.gameManager = gameManager;
        this.rolesTime = rolesTime;
        this.remindTimers = Arrays.asList(new Integer[] { Integer.valueOf(60), Integer.valueOf(5) });
        this.roles = false;
        this.blindCompo = false;
        this.composition = new HashMap<>(getDefaultCompo());
    }

    public int getTriggerTime() {
        return this.rolesTime;
    }

    public List<Integer> getRemindTimers() {
        return this.remindTimers;
    }

    public void setTriggerTime(int triggerTime) {
        this.rolesTime = triggerTime;
    }

    public void init() {
        this.roles = true;
        List<CrystaliaPlayer> gamePlayers = new ArrayList<>(this.main.getPlayerManager().getAliveGamePlayers());
        Collections.shuffle(gamePlayers);
        Class[] classArgs = new Class[2];
        classArgs[0] = GameManager.class;
        classArgs[1] = CrystaliaPlayer.class;
        for (LGRolesEnum lgRolesEnum : this.composition.keySet().stream().filter(lgRolesEnum -> (((Integer)this.composition.get(lgRolesEnum)).intValue() > 0)).collect(Collectors.toList())) {
            for (int i = 1; i <= ((Integer)this.composition.get(lgRolesEnum)).intValue(); i++) {
                if (!gamePlayers.isEmpty()) {
                    CrystaliaPlayer player = gamePlayers.get(0);
                    try {
                        player.setRole(lgRolesEnum.getRoleClass().getDeclaredConstructor(classArgs).newInstance(new Object[] { this.gameManager, player }));
                        player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + ((LGRole)player.getRole()).getShortDescription());
                    } catch (InstantiationException|IllegalAccessException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    gamePlayers.remove(0);
                }
            }
        }
        Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> this.main.getServer().getPluginManager().callEvent((Event)new LGRevealListeLoupsEvent()), 400L);
    }

    public void reminder(int remindTime) {
        Bukkit.broadcastMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§dAttribution des rôles dans §5" + ((remindTime >= 60) ? ((remindTime / 60) + " §dminute(s).") : (remindTime + " §dseconde(s).")));
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F));
    }

    public boolean hasTriggered() {
        return this.roles;
    }

    public String getForceCommand() {
        return "roles";
    }

    private Map<LGRolesEnum, Integer> getDefaultCompo() {
        Map<LGRolesEnum, Integer> compo = new HashMap<>();
        for (LGRolesEnum lgRolesEnum : LGRolesEnum.values())
            compo.put(lgRolesEnum, Integer.valueOf(0));
        return compo;
    }

    public Map<LGRolesEnum, Integer> getCompoForSide(LGSides.LGBasicSides lgBasicSide) {
        Map<LGRolesEnum, Integer> sideCompo = new HashMap<>();
        for (LGRolesEnum lgRolesEnum : LGRolesEnum.values()) {
            if (lgRolesEnum.getBasicSide().equals(lgBasicSide))
                sideCompo.put(lgRolesEnum, this.composition.get(lgRolesEnum));
        }
        return sideCompo;
    }

    public int getSideSize(LGSides.LGBasicSides lgBasicSides) {
        Map<LGRolesEnum, Integer> sideCompo = new HashMap<>(getCompoForSide(lgBasicSides));
        int i = 0;
        for (LGRolesEnum lgRolesEnum : sideCompo.keySet())
            i += ((Integer)sideCompo.get(lgRolesEnum)).intValue();
        return i;
    }

    public boolean isBlindCompo() {
        return this.blindCompo;
    }

    public void setBlindCompo(boolean blindCompo) {
        this.blindCompo = blindCompo;
    }

    public int getRoleSize(LGRolesEnum lgRolesEnum) {
        return ((Integer)this.composition.get(lgRolesEnum)).intValue();
    }

    public void increaseRole(LGRolesEnum lgRolesEnum) {
        int i = ((Integer)this.composition.get(lgRolesEnum)).intValue();
        this.composition.replace(lgRolesEnum, Integer.valueOf(++i));
    }

    public void decreaseRole(LGRolesEnum lgRolesEnum) {
        int i = ((Integer)this.composition.get(lgRolesEnum)).intValue();
        if (i > 0)
            this.composition.replace(lgRolesEnum, Integer.valueOf(--i));
    }
}
