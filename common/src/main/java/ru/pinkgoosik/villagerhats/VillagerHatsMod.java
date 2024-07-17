package ru.pinkgoosik.villagerhats;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class VillagerHatsMod {
	public static final String MOD_ID = "villagerhats";
    public static final Map<ResourceLocation, Supplier<Item>> ITEMS = new LinkedHashMap<>();

}
