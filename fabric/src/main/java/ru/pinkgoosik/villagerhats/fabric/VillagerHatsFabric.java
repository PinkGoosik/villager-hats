package ru.pinkgoosik.villagerhats.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.fabric.compat.TrinketsCompat;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;

import java.util.function.Supplier;

import static net.minecraft.world.entity.npc.VillagerProfession.*;
import static ru.pinkgoosik.villagerhats.VillagerHatsMod.*;

public class VillagerHatsFabric implements ModInitializer {
    public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.villagerhats.items"))
            .displayItems((ctx, entries) -> VillagerHatsMod.ITEMS.forEach((id, item) -> entries.accept(item.getDefaultInstance())))
            .icon(VillagerHatsMod.FARMER_HAT.get()::getDefaultInstance).build();

    @Override
    public void onInitialize() {
        FARMER_HAT = add(FARMER, 1.15F, -0.0625D);
        FLETCHER_HAT = add(FLETCHER, 1.05F, -0.125D);
        FISHERMAN_HAT = add(FISHERMAN, 1.05F, -0.0625D);
        ARMORER_HAT = add(ARMORER, 1.15F, -0.0625D);
        SHEPHERD_HAT = add(SHEPHERD, 1.05F, -0.125D);
        LIBRARIAN_HAT = add(LIBRARIAN, 1.15F, 0D);
        BUTCHER_HAT = add(BUTCHER, 1.15F, -0.125D);

        for (ResourceLocation id : VillagerHatsMod.ITEMS.keySet()) {
            Registry.register(BuiltInRegistries.ITEM, id, VillagerHatsMod.ITEMS.get(id));
        }

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation("villagerhats", "items"), ITEM_GROUP);
    }

    private static Supplier<Item> add(VillagerProfession profession) {
        Item item;

        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
            item = TrinketsCompat.createTrinket(profession);
        }
        else item = new VillagerHatItem(profession);

        VillagerHatsMod.ITEMS.put(new ResourceLocation("villagerhats", ((VillagerHat)item).getHatName()), item);
        return () -> item;
    }

    private static Supplier<Item> add(VillagerProfession profession, float size, double height) {
        Item item;

        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
            item = TrinketsCompat.createTrinket(profession, size, height);
        }
        else item = new VillagerHatItem(profession, size, height);

        VillagerHatsMod.ITEMS.put(new ResourceLocation("villagerhats", ((VillagerHat)item).getHatName()), item);
        return () -> item;
    }
}
