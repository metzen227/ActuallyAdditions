/*
 * This file ("ItemArmorAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerEnergizer;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemArmorAA extends ItemArmor implements IDisableableItem {

    private final ItemStack repairItem;
    private final String name;
    private final IRarity rarity;
    private final boolean disabled;

    public ItemArmorAA(String name, ArmorMaterial material, int type, ItemStack repairItem) {
        this(name, material, type, repairItem, EnumRarity.RARE);
    }

    public ItemArmorAA(String name, ArmorMaterial material, int type, ItemStack repairItem, IRarity rarity) {
        super(material, 0, ContainerEnergizer.VALID_EQUIPMENT_SLOTS[type]);
        this.repairItem = repairItem;
        this.name = name;
        this.rarity = rarity;
        this.disabled = ConfigurationHandler.config.getBoolean("Disable: " + StringUtil.badTranslate(name), "Tool Control", false, "This will disable the " + StringUtil.badTranslate(name) + ". It will not be registered.");
        if (!this.disabled) this.register();
    }

    private void register() {
        ItemUtil.registerItem(this, this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName() {
        return this.name;
    }

    public boolean shouldAddCreative() {
        return true;
    }

    protected void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return this.rarity;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
        return StackUtil.isValid(this.repairItem) && ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }
}
