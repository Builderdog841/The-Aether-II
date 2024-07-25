package com.aetherteam.aetherii.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.Map;

public final class EquipmentUtil {
    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    public static boolean hasArmorAbility(LivingEntity entity, Holder<ArmorMaterial> material) {
        Map<Holder<ArmorMaterial>, Integer> armorTypeCount = new HashMap<>();
        for (ItemStack itemStack : entity.getArmorSlots()) {
            if (itemStack.getItem() instanceof ArmorItem armorItem) {
                Holder<ArmorMaterial> materialHolder = armorItem.getMaterial();
                if (armorTypeCount.containsKey(materialHolder)) {
                    armorTypeCount.put(materialHolder, armorTypeCount.get(materialHolder) + 1);
                } else {
                    armorTypeCount.put(materialHolder, 1);
                }
            }
        }
        return armorTypeCount.computeIfAbsent(material, i -> 0) >= 3;
    }
}
