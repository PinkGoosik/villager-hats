package ru.pinkgoosik.villagerhats.neoforge;

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
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;
import ru.pinkgoosik.villagerhats.neoforge.client.VillagerHatsClient;
import ru.pinkgoosik.villagerhats.neoforge.compat.CuriosCompat;

import java.util.function.Supplier;

import static net.minecraft.world.entity.npc.VillagerProfession.*;
import static ru.pinkgoosik.villagerhats.VillagerHatsMod.*;

@Mod("villagerhats")
public class VillagerHatsNeoforge {
    public static final DeferredRegister.Items ITEMS_REGISTERER = DeferredRegister.createItems("villagerhats");
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "villagerhats");

    public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("villagerhats", () -> CreativeModeTab.builder().icon(VillagerHatsMod.FARMER_HAT.get()::getDefaultInstance).title(Component.translatable("itemGroup.villagerhats.items")).build());

    public VillagerHatsNeoforge(IEventBus bus) {
        FARMER_HAT = add(FARMER, 1.15F, -0.0625D);
        FLETCHER_HAT = add(FLETCHER, 1.05F, -0.125D);
        FISHERMAN_HAT = add(FISHERMAN, 1.05F, -0.0625D);
        ARMORER_HAT = add(ARMORER, 1.15F, -0.0625D);
        SHEPHERD_HAT = add(SHEPHERD, 1.05F, -0.125D);
        LIBRARIAN_HAT = add(LIBRARIAN, 1.15F, 0D);
        BUTCHER_HAT = add(BUTCHER, 1.15F, -0.125D);

        ITEMS_REGISTERER.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        bus.addListener(this::buildCreativeTab);

        if(FMLEnvironment.dist.isClient()) {
            VillagerHatsClient.init(bus);
        }
    }

    private void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CREATIVE_TAB.get()) {
            VillagerHatsMod.ITEMS.forEach((id, item) -> event.accept(item));
        }
    }

    private static Supplier<Item> add(VillagerProfession profession, float size, double height) {

        return ITEMS_REGISTERER.register(profession.name() + "_hat", () -> {
            Item item;

            if(ModList.get().isLoaded("curios")) {
                item = CuriosCompat.createCurio(profession, size, height);
            }
            else item = new VillagerHatItem(profession, size, height);

            VillagerHatsMod.ITEMS.put(new ResourceLocation("villagerhats", ((VillagerHat)item).getHatName()), item);

            return item;
        });
    }
}
