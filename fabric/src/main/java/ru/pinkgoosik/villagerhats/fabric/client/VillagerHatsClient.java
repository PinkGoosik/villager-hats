package ru.pinkgoosik.villagerhats.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import ru.pinkgoosik.villagerhats.client.render.VillagerHatRenderer;
import ru.pinkgoosik.villagerhats.fabric.compat.TrinketsCompat;

public class VillagerHatsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsCompat.onInitializeClient();
        }
        else {
            LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, reg, context) -> {
                if(entityRenderer instanceof PlayerRenderer renderer) {
                    reg.register(new VillagerHatRenderer<>(renderer));
                }
            });
        }
    }

}
