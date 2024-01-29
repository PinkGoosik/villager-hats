package ru.pinkgoosik.villagerhats.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VillagerHatItem extends Item implements VillagerHat, Equipable {
    private final float size;
    private final double height;
    private final VillagerProfession profession;

    public VillagerHatItem(VillagerProfession profession) {
        super(new Item.Properties());
        this.size = 1.15F;
        this.height = 0.2D;
        this.profession = profession;
    }

    public VillagerHatItem(VillagerProfession profession, float size, double height) {
        super(new Item.Properties());
        this.size = size;
        this.height = height;
        this.profession = profession;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            if(!level.isClientSide()) {
                player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(stack.getItem(), 1));
                stack.shrink(1);
            }
            else {
                player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
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

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
