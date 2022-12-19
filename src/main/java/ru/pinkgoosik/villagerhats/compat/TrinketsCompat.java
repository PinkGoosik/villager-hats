package ru.pinkgoosik.villagerhats.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.village.VillagerProfession;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHatTrinket;
import ru.pinkgoosik.villagerhats.client.render.VillagerHatTrinketRenderer;

import java.util.Optional;

/**
 * Whatever method from this class should only
 * be called after FabricLoader.getInstance().isModLoaded("trinkets")
 */
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

    public static Item createTrinket(VillagerProfession profession) {
        return new VillagerHatTrinket(profession);
    }

    public static Item createTrinket(VillagerProfession profession, float size, double height) {
        return new VillagerHatTrinket(profession, size, height);
    }

}
