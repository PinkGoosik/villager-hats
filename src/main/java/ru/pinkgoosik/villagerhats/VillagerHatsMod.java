package ru.pinkgoosik.villagerhats;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class VillagerHatsMod implements ModInitializer, ClientModInitializer {

    private static final Map<Identifier, HatItem> ITEMS = new LinkedHashMap<>();

    public static final Item FARMER_HAT = add(VillagerProfession.FARMER);
    public static final Item FLETCHER_HAT = add(VillagerProfession.FLETCHER);
    public static final Item FISHERMAN_HAT = add(VillagerProfession.FISHERMAN);
    public static final Item ARMORER_HAT = add(VillagerProfession.ARMORER, 1.15F, 0.17D);
    public static final Item SHEPHERD_HAT = add(VillagerProfession.SHEPHERD, 1.05F, 0.2D);
    public static final Item LIBRARIAN_HAT = add(VillagerProfession.LIBRARIAN);
    public static final Item BUTCHER_HAT = add(VillagerProfession.BUTCHER);

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier("villager-hats", "items"))
            .appendItems(itemStacks -> ITEMS.forEach((identifier, item) -> itemStacks.add(item.getDefaultStack())))
            .icon(FARMER_HAT::getDefaultStack).build();

    @Override
    public void onInitialize() {
        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }

    @Override
    public void onInitializeClient() {
        ITEMS.forEach((id, item) -> TrinketRendererRegistry.registerRenderer(item, item));
    }

    public static Map<Identifier, HatItem> getHats() {
        return ITEMS;
    }

    private static HatItem add(VillagerProfession profession) {
        HatItem item = new HatItem(profession);
        ITEMS.put(new Identifier("villager-hats", item.getHatName()), item);
        return item;
    }

    private static HatItem add(VillagerProfession profession, float size, double height) {
        HatItem item = new HatItem(profession, size, height);
        ITEMS.put(new Identifier("villager-hats", item.getHatName()), item);
        return item;
    }
}
