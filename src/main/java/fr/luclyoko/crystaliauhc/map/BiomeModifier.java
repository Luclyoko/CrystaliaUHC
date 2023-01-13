package fr.luclyoko.crystaliauhc.map;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BiomeModifier
{
    public BiomeModifier(final HashMap<String, HashMap<String, Integer>> biomestoreplace) {
        try {
            final String mojangPath = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            final Class clazz = Class.forName(String.valueOf(mojangPath) + ".BiomeBase");
            final HashMap<Object, HashMap<Object, Integer>> biomestoreplaceobject = new HashMap<Object, HashMap<Object, Integer>>();
            for (final Map.Entry<String, HashMap<String, Integer>> e : biomestoreplace.entrySet()) {
                final Field fieldkey = clazz.getDeclaredField(e.getKey());
                fieldkey.setAccessible(true);
                final Object objectkey = fieldkey.get(null);
                final HashMap<Object, Integer> biomereplacerobject = new HashMap<Object, Integer>();
                for (final Map.Entry<String, Integer> ev : e.getValue().entrySet()) {
                    final Field fieldvalue = clazz.getDeclaredField(ev.getKey());
                    fieldvalue.setAccessible(true);
                    final Object objectvalue = fieldvalue.get(null);
                    biomereplacerobject.put(objectvalue, ev.getValue());
                }
                biomestoreplaceobject.put(objectkey, biomereplacerobject);
            }
            final Field field = clazz.getDeclaredField("biomes");
            field.setAccessible(true);
            final Object[] biomes = (Object[])field.get(null);
            for (int i = 0; i < biomes.length; ++i) {
                if (biomes[i] != null && biomestoreplaceobject.containsKey(biomes[i])) {
                    if (biomestoreplaceobject.get(biomes[i]).size() == 1) {
                        for (final Map.Entry<Object, Integer> e2 : biomestoreplaceobject.get(biomes[i]).entrySet()) {
                            biomes[i] = e2.getKey();
                        }
                    }
                    else {
                        final HashMap<Object, Integer> biomereplacerobject2 = biomestoreplaceobject.get(biomes[i]);
                        Integer size = 0;
                        for (final Map.Entry<Object, Integer> e3 : biomereplacerobject2.entrySet()) {
                            size += e3.getValue();
                        }
                        Integer r = new Random().nextInt(size) + 1;
                        for (final Map.Entry<Object, Integer> e4 : biomereplacerobject2.entrySet()) {
                            r -= e4.getValue();
                            if (r <= 0) {
                                biomes[i] = e4.getKey();
                                break;
                            }
                        }
                    }
                }
            }
            try {
                field.setAccessible(true);
                field.set(null, biomes);
            }
            catch (Exception ex) {}
        }
        catch (Exception e5) {
            e5.printStackTrace();
        }
    }
}
