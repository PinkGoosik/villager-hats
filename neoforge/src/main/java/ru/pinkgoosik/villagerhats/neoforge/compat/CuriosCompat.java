package ru.pinkgoosik.villagerhats.neoforge.compat;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.neoforge.client.render.VillagerHatCurioRenderer;
import ru.pinkgoosik.villagerhats.neoforge.item.VillagerHatCurio;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

/**
 * Whatever method from this class should only
 * be called after ModList.get().isLoaded("curios") check
 */
public class CuriosCompat {

    public static void onInitializeClient() {
        VillagerHatsMod.ITEMS.forEach((id, sup) -> CuriosRendererRegistry.register(BuiltInRegistries.ITEM.get(id), VillagerHatCurioRenderer::new));
    }

    public static boolean hasVisibleHat(Player player) {
        var curiosItemHandler = CuriosApi.getCuriosInventory(player);
        if(curiosItemHandler.isPresent()) {
            for (Map.Entry<String, ICurioStacksHandler> entry : curiosItemHandler.get().getCurios().entrySet()) {
                IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                IDynamicStackHandler cosmeticStacksHandler = entry.getValue().getCosmeticStacks();

                for(int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);

                    NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                    boolean renderable = renderStates.size() > i && renderStates.get(i);

                    if (stack.isEmpty() && renderable) {
                        stack = stackHandler.getStackInSlot(i);
                    }

                    if (!stack.isEmpty() && stack.getItem() instanceof VillagerHatCurio) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static Item createCurio(VillagerProfession profession) {
        return new VillagerHatCurio(profession);
    }

    public static Item createCurio(VillagerProfession profession, float size, double height) {
        return new VillagerHatCurio(profession, size, height);
    }

}
