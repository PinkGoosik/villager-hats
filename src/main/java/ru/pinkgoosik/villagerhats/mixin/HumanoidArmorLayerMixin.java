package ru.pinkgoosik.villagerhats.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    void renderArmor(PoseStack f, MultiBufferSource f1, T entity, EquipmentSlot flag, int flag1, A armoritem, CallbackInfo ci){
        if(entity instanceof Player player){
            if (flag.equals(EquipmentSlot.HEAD) && hasVillagerHat(player)) ci.cancel();
        }
    }

    @Unique
    private static boolean hasVillagerHat(LivingEntity entity){
        for (RegistryObject<Item> item : VillagerHatsMod.ITEMS.getEntries()){
            if(CuriosApi.getCuriosHelper().findEquippedCurio(item.get(), entity).isPresent()){
                return true;
            }
        }
        return false;
    }
}
