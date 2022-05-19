package ru.pinkgoosik.villagerhats.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;

public class VillagerHatItem extends Item implements VillagerHat, Wearable {
    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public VillagerHatItem(VillagerProfession profession) {
        super(new FabricItemSettings());
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public VillagerHatItem(VillagerProfession profession, float size, double height) {
        super(new FabricItemSettings());
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            if(!world.isClient) {
                user.equipStack(EquipmentSlot.HEAD, new ItemStack(stack.getItem(), 1));
                stack.decrement(1);
            }
            else {
                user.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
            }
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public VillagerProfession getProfession() {
        return profession;
    }

    @Override
    public String getHatName() {
        return profession.getId() + "_hat";
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
