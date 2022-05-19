package ru.pinkgoosik.villagerhats.item;

import net.minecraft.village.VillagerProfession;

public interface VillagerHat {
    VillagerProfession getProfession();
    String getHatName();
    float getSize();
    double getHeight();
}
