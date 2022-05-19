package ru.pinkgoosik.villagerhats.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHatTrinket;
import ru.pinkgoosik.villagerhats.client.render.VillagerHatTrinketRenderer;

import java.util.Optional;

public class TrinketsCompat {

    public static void onInitializeClient() {
        VillagerHatsMod.ITEMS.forEach((id, item) -> TrinketRendererRegistry.registerRenderer(item, new VillagerHatTrinketRenderer()));
    }

    public static boolean hasVillagerHat(PlayerEntity player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            for(Pair<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
                if(pair.getRight().getItem() instanceof VillagerHatTrinket) return true;
            }
        }
        return false;
    }

}
