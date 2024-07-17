package ru.pinkgoosik.villagerhats.neoforge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;
import ru.pinkgoosik.villagerhats.neoforge.client.VillagerHatsClient;
import ru.pinkgoosik.villagerhats.neoforge.compat.CuriosCompat;

import java.util.function.Supplier;

import static net.minecraft.world.entity.npc.VillagerProfession.*;
import static ru.pinkgoosik.villagerhats.VillagerHatsMod.*;

@SuppressWarnings("unused")
@Mod("villagerhats")
public class VillagerHatsNeoforge {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "villagerhats");

    public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("villagerhats", () -> CreativeModeTab.builder().icon(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("villagerhats", "farmer_hat"))::getDefaultInstance).title(Component.translatable("itemGroup.villagerhats.items")).build());

    public VillagerHatsNeoforge(IEventBus bus) {
        add(FARMER, 1.15F, -0.0625D);
        add(FLETCHER, 1.05F, -0.125D);
        add(FISHERMAN, 1.05F, -0.0625D);
        add(ARMORER, 1.15F, -0.0625D);
        add(SHEPHERD, 1.05F, -0.125D);
        add(LIBRARIAN, 1.15F, 0D);
        add(BUTCHER, 1.15F, -0.125D);

        CREATIVE_MODE_TABS.register(bus);
        bus.addListener(this::buildCreativeTab);
		bus.addListener(this::register);

        if(FMLEnvironment.dist.isClient()) {
            VillagerHatsClient.init(bus);
        }
    }

	public void register(RegisterEvent event) {
		event.register(Registries.ITEM, registry -> ITEMS.forEach((id, sup) -> registry.register(id, sup.get())));
	}

    private void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CREATIVE_TAB.get()) {
            VillagerHatsMod.ITEMS.forEach((id, sup) -> event.accept(BuiltInRegistries.ITEM.get(id)));
        }
    }

    private static void add(VillagerProfession profession, float size, double height) {

		ITEMS.put(ResourceLocation.fromNamespaceAndPath("villagerhats", profession.name() + "_hat"), () -> {
			Item item;

			if(ModList.get().isLoaded("curios")) {
				item = CuriosCompat.createCurio(profession, size, height);
			}
			else item = new VillagerHatItem(profession, size, height);

			return item;
		});
    }
}
