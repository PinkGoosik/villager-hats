package ru.pinkgoosik.villagerhats.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.item.VillagerHat;

public class VillagerHatRenderer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    public VillagerHatRenderer(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, Player entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
        var biped = this.getParentModel();

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
