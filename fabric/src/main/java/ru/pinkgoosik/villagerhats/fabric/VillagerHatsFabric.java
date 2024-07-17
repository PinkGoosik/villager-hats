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
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;

import static net.minecraft.world.entity.npc.VillagerProfession.*;
import static ru.pinkgoosik.villagerhats.VillagerHatsMod.*;

public class VillagerHatsFabric implements ModInitializer {
    public static CreativeModeTab ITEM_GROUP;

    @Override
    public void onInitialize() {
        add(FARMER, 1.15F, -0.0625D);
        add(FLETCHER, 1.05F, -0.125D);
        add(FISHERMAN, 1.05F, -0.0625D);
        add(ARMORER, 1.15F, -0.0625D);
        add(SHEPHERD, 1.05F, -0.125D);
        add(LIBRARIAN, 1.15F, 0D);
        add(BUTCHER, 1.15F, -0.125D);

		ITEMS.forEach((id, sup) -> {
			Registry.register(BuiltInRegistries.ITEM, id, sup.get());
		});

		ITEM_GROUP = FabricItemGroup.builder()
			.title(Component.translatable("itemGroup.villagerhats.items"))
			.displayItems((ctx, entries) -> VillagerHatsMod.ITEMS.forEach((id, sup) -> entries.accept(BuiltInRegistries.ITEM.get(id).getDefaultInstance())))
			.icon(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("villagerhats", "farmer_hat"))::getDefaultInstance).build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("villagerhats", "items"), ITEM_GROUP);
    }

//    private static Supplier<Item> add(VillagerProfession profession) {
//        Item item;
//
//        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
//            item = TrinketsCompat.createTrinket(profession);
//        }
//        else item = new VillagerHatItem(profession);
//
//        VillagerHatsMod.ITEMS.put(ResourceLocation.fromNamespaceAndPath("villagerhats", ((VillagerHat)item).getHatName()), item);
//        return () -> item;
//    }

    private static void add(VillagerProfession profession, float size, double height) {
        VillagerHatsMod.ITEMS.put(ResourceLocation.fromNamespaceAndPath("villagerhats", profession.name() + "_hat"), () -> {
			Item item;

			if(FabricLoader.getInstance().isModLoaded("trinkets")) {
				item = TrinketsCompat.createTrinket(profession, size, height);
			}
			else item = new VillagerHatItem(profession, size, height);

			return item;
		});
    }
}
