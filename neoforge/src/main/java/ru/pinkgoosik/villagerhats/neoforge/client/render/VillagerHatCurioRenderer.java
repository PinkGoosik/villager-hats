package ru.pinkgoosik.villagerhats.neoforge.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class VillagerHatCurioRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrices, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource vertexConsumers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> biped) {
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

                var model = new ModelResourceLocation("villagerhats", hat.getHatName(), "inventory");
                itemRenderer.render(stack, ItemDisplayContext.NONE, false, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY, itemRenderer.getItemModelShaper().getModelManager().getModel(model));
                matrices.popPose();
            }
        }
    }
}
