package ru.pinkgoosik.villagerhats.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    LivingEntity self = (LivingEntity)(Object)this;

    @Inject(method = "drop", at = @At("TAIL"))
    void drop(DamageSource source, CallbackInfo ci) {
        if(self.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && self.getType().equals(EntityType.ZOMBIE_VILLAGER) && self instanceof VillagerDataContainer villagerDataContainer) {
            VillagerProfession prof = villagerDataContainer.getVillagerData().getProfession();

            VillagerHatsMod.ITEMS.forEach((id, item) -> {
                if(((VillagerHat)item).getProfession().equals(prof)) self.dropStack(item.getDefaultStack());
            });
        }
    }

    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if(stack.getItem() instanceof VillagerHatItem) cir.setReturnValue(EquipmentSlot.HEAD);
    }

}
