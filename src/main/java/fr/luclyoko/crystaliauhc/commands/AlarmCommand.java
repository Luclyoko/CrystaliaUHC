package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AlarmCommand implements TabExecutor {

    private final Main main;

    private final GameManager gameManager;

    public AlarmCommand(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (this.gameManager.getGameSettings().getHost() == null || !this.gameManager.getGameSettings().getHost().equals(player.getUniqueId())) {
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cVous n'avez pas l'autorisation de faire ceci.");
            return true;
        }
        if (args.length < 2) {
            player.sendMessage("§cErreur: Veuillez spécifier un horaire valide.");
            return true;
        }
        String arg0 = args[0];
        char[] chars = arg0.toCharArray();
        StringBuilder hourSb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                hourSb.append(c);
            }
        }
        String arg1 = args[1];
        chars = arg1.toCharArray();
        StringBuilder minutesSb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                minutesSb.append(c);
            }
        }
        if (hourSb.toString().isEmpty() || minutesSb.toString().isEmpty()) {
            player.sendMessage("§cErreur: Veuillez spécifier un horaire valide.");
            return true;
        }

        int hour = Integer.parseInt(hourSb.toString());
        int minutes = Integer.parseInt(minutesSb.toString());

        if ((0 <= hour && hour <= 23) && (0 <= minutes && minutes <= 59)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            hourFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
            calendar.setTimeInMillis(System.currentTimeMillis());
            String hourString = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + " - " + hour + ":" + minutes;

            try {
                Date date = simpleDateFormat.parse(hourString);
                long dateTime = date.getTime();
                long remainingTime = dateTime - System.currentTimeMillis();

                player.sendMessage("§eAlarme configurée pour: §6" + hourFormat.format(dateTime));
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    Bukkit.broadcastMessage("§e§lALARME !");
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        main.getTitle().sendTitle(player1, 20, 60, 20, "§e§lIl est §6" + hourFormat.format(dateTime));
                        player1.playSound(player1.getLocation(), Sound.WITHER_DEATH, 1f, 1f);
                    });
                },(20 * remainingTime) / 1000);
                return true;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        player.sendMessage("§cErreur: Veuillez spécifier un horaire valide.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
