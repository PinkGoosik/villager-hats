package ru.pinkgoosik.villagerhats.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.villagerhats.HatItem;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    LivingEntity self = LivingEntity.class.cast(this);

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    void drop(DamageSource source, CallbackInfo ci){
        if(self.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && self.getType().equals(EntityType.ZOMBIE_VILLAGER) && self instanceof VillagerDataHolder villagerDataContainer){
            VillagerProfession prof = villagerDataContainer.getVillagerData().getProfession();
            VillagerHatsMod.ITEMS.getEntries().forEach(itemRegistryObject -> {
                if(itemRegistryObject.get() instanceof HatItem item){
                    if(item.getProfession().equals(prof)) self.spawnAtLocation(item.getDefaultInstance());
                }
            });
        }
    }
}
