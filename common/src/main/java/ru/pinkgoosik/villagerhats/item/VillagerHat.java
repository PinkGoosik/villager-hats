package ru.pinkgoosik.villagerhats.item;

import net.minecraft.world.entity.npc.VillagerProfession;

public interface VillagerHat {
    VillagerProfession getProfession();
    String getHatName();
    float getSize();
    double getHeight();
}
