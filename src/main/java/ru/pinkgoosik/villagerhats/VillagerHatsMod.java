package ru.pinkgoosik.villagerhats;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;
import ru.pinkgoosik.villagerhats.item.VillagerHatTrinket;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.village.VillagerProfession.*;

@SuppressWarnings("unused")
public class VillagerHatsMod implements ModInitializer {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

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

    private static Item add(VillagerProfession profession) {
        Item item;

        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
            item = new VillagerHatTrinket(profession);
        }
        else {
            item = new VillagerHatItem(profession);
        }

        ITEMS.put(new Identifier("villager-hats", ((VillagerHat)item).getHatName()), item);
        return item;
    }

    private static Item add(VillagerProfession profession, float size, double height) {
        Item item;

        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
            item = new VillagerHatTrinket(profession, size, height);
        }
        else {
            item = new VillagerHatItem(profession, size, height);
        }

        ITEMS.put(new Identifier("villager-hats", ((VillagerHat)item).getHatName()), item);
        return item;
    }

}
