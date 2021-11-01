package fr.luclyoko.crystaliauhc.utils.schematics;

public class Schematic {

    private short[] blocks;

    private byte[] data;

    private short width;

    private short lenght;

    private short height;

    Schematic(short[] sblocks, byte[] data, short width, short lenght, short height) {
        this.blocks = sblocks;
        this.data = data;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
    }

    short[] getBlocks() {
        return this.blocks;
    }

    byte[] getData() {
        return this.data;
    }

    short getWidth() {
        return this.width;
    }

    short getLenght() {
        return this.lenght;
    }

    short getHeight() {
        return this.height;
    }
}