package fr.luclyoko.crystaliauhc.teams;

import org.bukkit.DyeColor;

public class TeamAssets {
    public enum TeamColors {
        BLUE(4, "§9", "blue", "Bleu", DyeColor.BLUE),
        RED(1, "§c", "red", "Rouge", DyeColor.RED),
        GREEN(2, "§a", "green", "Vert", DyeColor.GREEN),
        YELLOW(11, "§e", "yellow", "Jaune", DyeColor.YELLOW),
        ORANGE(14, "§6", "gold", "Orange", DyeColor.ORANGE),
        PINK(9, "§d", "light_purple", "Rose", DyeColor.PINK),
        CYAN(6, "§b", "aqua", "Cyan", DyeColor.LIGHT_BLUE);

        private int colorId;

        private String displayColor;

        private String colorName;

        private String displayName;

        private DyeColor dyeColor;

        TeamColors(int colorId, String displayColor, String colorName, String displayName, DyeColor dyeColor) {
            this.colorId = colorId;
            this.displayColor = displayColor;
            this.colorName = colorName;
            this.displayName = displayName;
            this.dyeColor = dyeColor;
        }

        public int getColorId() {
            return this.colorId;
        }

        public String getDisplayColor() {
            return this.displayColor;
        }

        public String getColorName() {
            return this.colorName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public DyeColor getDyeColor() {
            return this.dyeColor;
        }
    }

    public enum TeamSymbols {
        CLASSIC(""),
        HEART("♥"),
        STAR("✭"),
        SKULL("☠");

        private String symbol;

        TeamSymbols(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return this.symbol;
        }
    }
}
