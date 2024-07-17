package ru.pinkgoosik.villagerhats.neoforge.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.villagerhats.item.VillagerHat;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class VillagerHatCurio extends Item implements VillagerHat, ICurioItem {
    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public VillagerHatCurio(VillagerProfession profession) {
        super(new Item.Properties());
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public VillagerHatCurio(VillagerProfession profession, float size, double height) {
        super(new Item.Properties());
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 1.0F, 1.0F);
    }

    @Override
    public VillagerProfession getProfession() {
        return profession;
    }

    @Override
    public String getHatName() {
        return profession.name() + "_hat";
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

