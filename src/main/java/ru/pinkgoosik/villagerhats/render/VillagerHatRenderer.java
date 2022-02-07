package ru.pinkgoosik.villagerhats.render;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;

@Environment(EnvType.CLIENT)
public class VillagerHatRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(contextModel instanceof BipedEntityModel<? extends LivingEntity> biped) {
            if(stack.getItem() instanceof VillagerHatItem hat) {
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                double height = hat.getHeight();
                float size = hat.getSize();

                matrices.push();
                biped.head.rotate(matrices);
                matrices.translate(0D, -1D, 0D);
                matrices.translate(0D, -(height), 0D);
                matrices.scale(size, size, size);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

                String modelId = "villager-hats:" + hat.getHatName() + "#inventory";
                itemRenderer.renderItem(stack, ModelTransformation.Mode.NONE, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
                matrices.pop();
            }
        }
    }
}
