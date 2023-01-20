package fr.luclyoko.crystaliauhc.gamemodes.arena;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ArenaPower;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ArenaPowerEnum;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ArenaCommand implements TabExecutor {
    private Main main;

    private GameManager gameManager;

    public ArenaCommand(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(this.gameManager.getGamemodeUhc() instanceof ArenaUHC))
            return false;
        ArenaUHC arenaUHC = (ArenaUHC) this.gameManager.getGamemodeUhc();
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (args.length > 0) {
            CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            switch (args[0]) {
                case "forceroles":
                    if (gameManager.getGameSettings().getHost().equals(player.getUniqueId())) {
                        arenaUHC.getArenaRolesManager().setForceRoles(!arenaUHC.getArenaRolesManager().isForceRoles());
                        crystaliaPlayer.sendMessage("§6Rôles forcés: " + (arenaUHC.getArenaRolesManager().isForceRoles() ? "§aOui" : "§cNon"));
                    }
                    return true;
                case "role":
                    if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole)
                        crystaliaPlayer.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + ((ArenaRole)crystaliaPlayer.getRole()).getDescription());
                    return true;
                case "forcenextrole":
                    if (gameManager.getGameSettings().getHost().equals(player.getUniqueId())) {
                        if (args.length > 1) {
                            String role = args[1];
                            CrystaliaPlayer playerToForce = crystaliaPlayer;
                            if (args.length > 2) {
                                String name = args[2];
                                if (Bukkit.getPlayer(name) != null) {
                                    CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(Bukkit.getPlayer(name));
                                    if (crystaliaTarget.isOnline()) {
                                        playerToForce = crystaliaTarget;
                                    }
                                }
                            }
                            try {
                                ArenaRolesEnum arenaRolesEnum = ArenaRolesEnum.valueOf(role.toUpperCase(Locale.ROOT));
                                arenaUHC.getArenaRolesManager().addForcedRole(playerToForce, arenaRolesEnum);
                                crystaliaPlayer.sendMessage("§aProchain rôle forcé pour " + playerToForce.getPlayerName() + ": §6" + arenaRolesEnum.getName());
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return true;
                case "grantpower":
                    if (gameManager.getGameSettings().getHost().equals(player.getUniqueId())) {
                        if (args.length > 1) {
                            String role = args[1];
                            CrystaliaPlayer playerToGrant = crystaliaPlayer;
                            if (args.length > 2) {
                                String name = args[2];
                                if (Bukkit.getPlayer(name) != null) {
                                    CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(Bukkit.getPlayer(name));
                                    if (crystaliaTarget.isOnline()) {
                                        playerToGrant = crystaliaTarget;
                                    }
                                }
                            }
                            if (!playerToGrant.isOnline() || !playerToGrant.isAlive()) return true;
                            if (playerToGrant.getRole() != null && playerToGrant.getRole() instanceof ArenaRole) {
                                ArenaRole arenaRole = (ArenaRole) playerToGrant.getRole();
                                try {
                                    ArenaPowerEnum arenaPowerEnum = ArenaPowerEnum.valueOf(role.toUpperCase(Locale.ROOT));
                                    Class[] classArgs = new Class[3];
                                    classArgs[0] = ArenaUHC.class;
                                    classArgs[1] = ArenaRole.class;
                                    classArgs[2] = Main.class;
                                    ArenaPower power = arenaPowerEnum.getPowerClass().getDeclaredConstructor(classArgs).newInstance(arenaUHC, arenaRole, main);
                                    arenaRole.addPower(power);
                                    playerToGrant.giveItem(power.getPowerItem());
                                    crystaliaPlayer.sendMessage("§aPouvoir accordé à " + playerToGrant.getPlayerName() + ": §6" + arenaPowerEnum.getName());
                                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                                         NoSuchMethodException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    return true;
                case "saveinventory":
                    arenaUHC.getInventoryManager().saveInventory(crystaliaPlayer);
                    return true;
                default:
                    break;
            }
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa commande est incorrecte ou incomplète.");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>(Arrays.asList("role", "saveinventory"));
            List<String> admincommands = new ArrayList<>(Arrays.asList("forceroles", "forcenextrole", "grantpower"));
            if (gameManager.getGameSettings().getHost().equals(player.getUniqueId())) subcommands.addAll(admincommands);
            return subcommands.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        if (args.length == 2) {
            if (args[0].equals("forcenextrole")) {
                List<String> roles = new ArrayList<>();
                for (ArenaRolesEnum rolesEnum : ArenaRolesEnum.values()) {
                    roles.add(rolesEnum.name());
                }
                return roles.stream().filter(s -> s.startsWith(args[1].toUpperCase(Locale.ROOT))).collect(Collectors.toList());
            } else if (args[0].equals("grantpower")) {
                List<String> powers = new ArrayList<>();
                for (ArenaPowerEnum powerEnum : ArenaPowerEnum.values()) {
                    powers.add(powerEnum.name());
                }
                return powers.stream().filter(s -> s.startsWith(args[1].toUpperCase(Locale.ROOT))).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
