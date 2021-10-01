package ru.pinkgoosik.villagerhats;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.village.VillagerProfession;

public class HatItem extends TrinketItem implements TrinketRenderer {

    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public HatItem(VillagerProfession profession) {
        super(new FabricItemSettings());
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public HatItem(VillagerProfession profession, float size, double height) {
        super(new FabricItemSettings());
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    public VillagerProfession getProfession() {
        return profession;
    }

    public String getHatName() {
        return profession.getId() + "_hat";
    }

    @SuppressWarnings("unchecked")
    @Environment(EnvType.CLIENT)
    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrixStack.push();
        TrinketRenderer.translateToFace(matrixStack, (PlayerEntityModel<AbstractClientPlayerEntity>) entityModel, (AbstractClientPlayerEntity) entity, headYaw, headPitch);

        matrixStack.translate(0D, -(height), 0.30000001192092896D);
        matrixStack.scale(size, size, size);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

        String modelId = "villager-hats:" + this.getHatName() + "#inventory";
        itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, false, matrixStack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV, itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier(modelId)));
        matrixStack.pop();
    }
}
