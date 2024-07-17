package ru.pinkgoosik.villagerhats.neoforge.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import ru.pinkgoosik.villagerhats.client.render.VillagerHatRenderer;
import ru.pinkgoosik.villagerhats.neoforge.compat.CuriosCompat;

@OnlyIn(Dist.CLIENT)
public class VillagerHatsClient {

    public static void init(IEventBus bus) {
        bus.addListener(VillagerHatsClient::clientSetup);
        bus.addListener(VillagerHatsClient::registerRenderLayers);
    }

    private static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skin);

            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new VillagerHatRenderer<>(playerRenderer));
            }
        }
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        if(ModList.get().isLoaded("curios")) {
            CuriosCompat.onInitializeClient();
        }
    }
}
