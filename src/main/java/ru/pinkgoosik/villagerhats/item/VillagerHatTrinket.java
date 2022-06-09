package ru.pinkgoosik.villagerhats.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;

public class VillagerHatTrinket extends TrinketItem implements VillagerHat {
    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public VillagerHatTrinket(VillagerProfession profession) {
        super(new FabricItemSettings());
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public VillagerHatTrinket(VillagerProfession profession, float size, double height) {
        super(new FabricItemSettings());
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);
        entity.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
    }

    @Override
    public VillagerProfession getProfession() {
        return profession;
    }

    @Override
    public String getHatName() {
        return profession.id() + "_hat";
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public double getHeight() {
        return height;
    }

}
