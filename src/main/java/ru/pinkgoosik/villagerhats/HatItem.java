package ru.pinkgoosik.villagerhats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class HatItem extends Item implements ICurioItem, ICurioRenderer {
    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public HatItem(VillagerProfession profession) {
        super(new Item.Properties().tab(VillagerHatsMod.TAB));
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public HatItem(VillagerProfession profession, float size, double height) {
        super(new Item.Properties().tab(VillagerHatsMod.TAB));
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public VillagerProfession getProfession() {
        return profession;
    }

    public String getHatName() {
        return profession.getName() + "_hat";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        poseStack.pushPose();
        translateToHead(poseStack, slotContext.entity(), netHeadYaw, headPitch);
        poseStack.translate(0D, -(height), 0.3D);
        poseStack.scale(size, size, size);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        String modelId = "villagerhats:" + this.getHatName() + "#inventory";
        itemRenderer.render(stack, ItemTransforms.TransformType.FIXED, false, poseStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, itemRenderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(modelId)));
        poseStack.popPose();
    }

    static void translateToHead(PoseStack poseStack, LivingEntity entity, float headYaw, float headPitch) {

        if (entity.isVisuallySwimming() || entity.isFallFlying()) {
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(entity.yHeadRot));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
        } else {

            if (entity.isCrouching()) {
                poseStack.translate(0.0F, 0.25F, 0.0F);
            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
        }
        poseStack.translate(0.0F, -0.25F, -0.3F);
    }
}
