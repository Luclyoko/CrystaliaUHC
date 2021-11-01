package fr.luclyoko.crystaliauhc.utils.schematics;

import java.io.InputStream;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SchematicManager {

    public void pasteSchematic(Location location, Schematic schematic) {
        short[] blocks = schematic.getBlocks();
        byte[] blockData = schematic.getData();
        int length = schematic.getLenght();
        int width = schematic.getWidth();
        int height = schematic.getHeight();
        World world = location.getWorld();
        int x = 0;
        while (x < width) {
            int y = 0;
            while (y < height) {
                int z = 0;
                while (z < length) {
                    int index = y * width * length + z * width + x;
                    Block block = (new Location(world, x + location.getX(), y + location.getY(), z + location.getZ())).getBlock();
                    if (blocks[index] != Material.AIR.ordinal())
                        block.setTypeIdAndData(blocks[index], blockData[index], true);
                    z++;
                }
                y++;
            }
            x++;
        }
    }

    public Schematic loadSchematic(InputStream inputStream) {
        try {
            NBTTagCompound nbtdata = NBTCompressedStreamTools.a(inputStream);
            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");
            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");
            byte[] addId = new byte[0];
            if (nbtdata.hasKey("AddBlocks"))
                addId = nbtdata.getByteArray("AddBlocks");
            short[] sblocks = new short[blocks.length];
            int index = 0;
            while (index < blocks.length) {
                sblocks[index] = (index >> 1 >= addId.length) ? (short)(blocks[index] & 0xFF) : (((index & 0x1) == 0) ? (short)(((addId[index >> 1] & 0xF) << 8) + (blocks[index] & 0xFF)) : (short)(((addId[index >> 1] & 0xF0) << 4) + (blocks[index] & 0xFF)));
                index++;
            }
            inputStream.close();
            return new Schematic(sblocks, data, width, length, height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getSchematicFromRessources(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}