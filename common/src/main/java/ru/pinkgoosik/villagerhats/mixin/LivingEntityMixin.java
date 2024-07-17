package ru.pinkgoosik.villagerhats.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import ru.pinkgoosik.villagerhats.item.VillagerHatItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract boolean shouldDropLoot();

    LivingEntity self = (LivingEntity)(Object)this;

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    void drop(ServerLevel level, DamageSource damageSource, CallbackInfo ci) {
        if(this.shouldDropLoot() && self.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && self.getType().equals(EntityType.ZOMBIE_VILLAGER) && self instanceof VillagerDataHolder villagerDataContainer) {
            VillagerProfession prof = villagerDataContainer.getVillagerData().getProfession();

            VillagerHatsMod.ITEMS.forEach((id, sup) -> {
				var item = BuiltInRegistries.ITEM.get(id);
                if(((VillagerHat)item).getProfession().equals(prof)) self.spawnAtLocation(item.getDefaultInstance());
            });
        }
    }

    @Inject(method = "getEquipmentSlotForItem", at = @At("HEAD"), cancellable = true)
    private void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if(stack.getItem() instanceof VillagerHatItem) cir.setReturnValue(EquipmentSlot.HEAD);
    }

}
