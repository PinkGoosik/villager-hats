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
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;
import ru.pinkgoosik.villagerhats.render.VillagerHatRenderer;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.village.VillagerProfession.*;

@SuppressWarnings("unused")
public class VillagerHatsMod implements ModInitializer, ClientModInitializer {
    public static final Map<Identifier, VillagerHatItem> ITEMS = new LinkedHashMap<>();

    public static final Item FARMER_HAT = add(FARMER, 1.15F, -0.0625D);
    public static final Item FLETCHER_HAT = add(FLETCHER, 1.05F, -0.125D);
    public static final Item FISHERMAN_HAT = add(FISHERMAN, 1.05F, -0.0625D);
    public static final Item ARMORER_HAT = add(ARMORER, 1.15F, -0.0625D);
    public static final Item SHEPHERD_HAT = add(SHEPHERD, 1.05F, -0.125D);
    public static final Item LIBRARIAN_HAT = add(LIBRARIAN, 1.15F, 0D);
    public static final Item BUTCHER_HAT = add(BUTCHER, 1.15F, -0.125D);

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder
            .create(new Identifier("villager-hats", "items"))
            .appendItems(stacks -> ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack())))
            .icon(FARMER_HAT::getDefaultStack).build();

    @Override
    public void onInitialize() {
        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }

    @Override
    public void onInitializeClient() {
        ITEMS.forEach((id, item) -> TrinketRendererRegistry.registerRenderer(item, new VillagerHatRenderer()));
    }

    private static VillagerHatItem add(VillagerProfession profession) {
        VillagerHatItem item = new VillagerHatItem(profession);
        ITEMS.put(new Identifier("villager-hats", item.getHatName()), item);
        return item;
    }

    private static VillagerHatItem add(VillagerProfession profession, float size, double height) {
        VillagerHatItem item = new VillagerHatItem(profession, size, height);
        ITEMS.put(new Identifier("villager-hats", item.getHatName()), item);
        return item;
    }
}
