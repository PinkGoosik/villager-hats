package ru.pinkgoosik.villagerhats;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class VillagerHatsMod implements ModInitializer, ClientModInitializer {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    public static final Item FARMER_HAT = register(new HatItem("farmer_hat", 1.15F, 0.25D));
    public static final Item FLETCHER_HAT = register(new HatItem("fletcher_hat", 1.15F, 0.2D));
    public static final Item FISHERMAN_HAT = register(new HatItem("fisherman_hat", 1.15F, 0.25D));
    public static final Item ARMORER_HAT = register(new HatItem("armorer_hat", 1.15F, 0.2D));
    public static final Item SHEPHERD_HAT = register(new HatItem("shepherd_hat", 1.05F, 0.2D));
    public static final Item LIBRARIAN_HAT = register(new HatItem("librarian_hat", 1.15F, 0.25D));
    public static final Item BUTCHER_HAT = register(new HatItem("butcher_hat", 1.15F, 0.2D));

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier("villagershats", "items"))
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
        ITEMS.forEach((id, item) -> TrinketRendererRegistry.registerRenderer(item, (TrinketRenderer)item));
    }

    private static HatItem register(HatItem item) {
        ITEMS.put(new Identifier("villagershats", item.getIdentifier()), item);
        return item;
    }
}
