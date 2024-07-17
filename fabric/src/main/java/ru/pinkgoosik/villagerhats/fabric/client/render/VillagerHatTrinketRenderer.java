package ru.pinkgoosik.villagerhats.fabric.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.item.VillagerHat;

@Environment(EnvType.CLIENT)
public class VillagerHatTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(contextModel instanceof HumanoidModel<? extends LivingEntity> biped) {
            if(stack.getItem() instanceof VillagerHat hat) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                double height = hat.getHeight();
                float size = hat.getSize();

                matrices.pushPose();
                biped.head.translateAndRotate(matrices);
                matrices.translate(0D, -1D, 0D);
                matrices.translate(0D, -(height), 0D);
                matrices.scale(size, size, size);
                matrices.mulPose(Axis.XP.rotationDegrees(180.0F));
                matrices.mulPose(Axis.YP.rotationDegrees(180.0F));

                var model = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath("villagerhats", hat.getHatName()), "inventory");
                itemRenderer.render(stack, ItemDisplayContext.NONE, false, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY, itemRenderer.getItemModelShaper().getModelManager().getModel(model));
                matrices.popPose();
            }
        }
    }

}
