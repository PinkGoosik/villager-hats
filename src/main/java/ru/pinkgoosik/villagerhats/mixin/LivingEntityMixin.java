package ru.pinkgoosik.villagerhats.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.villagerhats.VillagerHatsMod;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    LivingEntity self = (LivingEntity)(Object)this;

    @Inject(method = "drop", at = @At("TAIL"))
    void drop(DamageSource source, CallbackInfo ci){

        if(self.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && self.getType().equals(EntityType.ZOMBIE_VILLAGER) && self instanceof VillagerDataContainer villagerDataContainer){
            VillagerProfession prof = villagerDataContainer.getVillagerData().getProfession();

            if(prof.equals(VillagerProfession.FARMER)) self.dropStack(VillagerHatsMod.FARMER_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.FLETCHER)) self.dropStack(VillagerHatsMod.FLETCHER_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.FISHERMAN)) self.dropStack(VillagerHatsMod.FISHERMAN_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.ARMORER)) self.dropStack(VillagerHatsMod.ARMORER_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.SHEPHERD)) self.dropStack(VillagerHatsMod.SHEPHERD_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.LIBRARIAN)) self.dropStack(VillagerHatsMod.LIBRARIAN_HAT.getDefaultStack());
            else if(prof.equals(VillagerProfession.BUTCHER)) self.dropStack(VillagerHatsMod.BUTCHER_HAT.getDefaultStack());
        }
    }
}
