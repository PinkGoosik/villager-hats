package ru.pinkgoosik.villagerhats.fabric.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.fabric.item.VillagerHatTrinket;
import ru.pinkgoosik.villagerhats.fabric.client.render.VillagerHatTrinketRenderer;

import java.util.Optional;

/**
 * Whatever method from this class should only
 * be called after FabricLoader.getInstance().isModLoaded("trinkets")
 */
public class TrinketsCompat {

    public static void onInitializeClient() {
        VillagerHatsMod.ITEMS.forEach((id, sup) -> TrinketRendererRegistry.registerRenderer(BuiltInRegistries.ITEM.get(id), new VillagerHatTrinketRenderer()));
    }

    public static boolean hasVillagerHat(Player player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            for(Tuple<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
                if(pair.getB().getItem() instanceof VillagerHatTrinket) return true;
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
