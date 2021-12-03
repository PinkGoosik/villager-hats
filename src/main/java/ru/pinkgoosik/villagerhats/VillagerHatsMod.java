package ru.pinkgoosik.villagerhats;

import static net.minecraft.world.entity.npc.VillagerProfession.*;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@SuppressWarnings("unused")
@Mod("villagerhats")
public class VillagerHatsMod {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "villagerhats");

    public static final CreativeModeTab TAB = new CreativeModeTab("villagerhats") {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return FARMER_HAT.getDefaultInstance();
        }
    };

    public static final Item FARMER_HAT = add(FARMER);
    public static final Item FLETCHER_HAT = add(FLETCHER);
    public static final Item FISHERMAN_HAT = add(FISHERMAN);
    public static final Item ARMORER_HAT = add(ARMORER, 1.15F, 0.17D);
    public static final Item SHEPHERD_HAT = add(SHEPHERD, 1.05F, 0.2D);
    public static final Item LIBRARIAN_HAT = add(LIBRARIAN);
    public static final Item BUTCHER_HAT = add(BUTCHER);

    public VillagerHatsMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onInitialize);
        eventBus.addListener(this::onClientInitialize);
        eventBus.addListener(this::enqueueIMC);
        ITEMS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onInitialize(final FMLCommonSetupEvent event) {
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    public void onClientInitialize(final FMLClientSetupEvent event) {
        ITEMS.getEntries().forEach(itemRegistryObject -> {
            Item item = itemRegistryObject.get();
            if(item instanceof ICurioRenderer renderer) {
                CuriosRendererRegistry.register(item, () -> renderer);
            }
        });
    }

    private static HatItem add(VillagerProfession profession) {
        HatItem item = new HatItem(profession);
        ITEMS.register(item.getHatName(), () -> item);
        return item;
    }

    private static HatItem add(VillagerProfession profession, float size, double height) {
        HatItem item = new HatItem(profession, size, height);
        ITEMS.register(item.getHatName(), () -> item);
        return item;
    }
}
