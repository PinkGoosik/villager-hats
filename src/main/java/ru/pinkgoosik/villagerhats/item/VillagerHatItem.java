package ru.pinkgoosik.villagerhats.item;

import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.village.VillagerProfession;

public class VillagerHatItem extends TrinketItem {
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

    public VillagerProfession getProfession() {
        return profession;
    }

    public String getHatName() {
        return profession.getId() + "_hat";
    }

    public float getSize() {
        return size;
    }

    public double getHeight() {
        return height;
    }
}
