package ru.pinkgoosik.villagerhats;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class VillagerHatsMod {
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();

    public static Supplier<Item> FARMER_HAT;
    public static Supplier<Item> FLETCHER_HAT;
    public static Supplier<Item> FISHERMAN_HAT;
    public static Supplier<Item> ARMORER_HAT;
    public static Supplier<Item> SHEPHERD_HAT;
    public static Supplier<Item> LIBRARIAN_HAT;
    public static Supplier<Item> BUTCHER_HAT;

}
