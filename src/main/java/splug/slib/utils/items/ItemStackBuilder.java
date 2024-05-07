package splug.slib.utils.items;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

@Getter @SuppressWarnings("unused")
public class ItemStackBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private ItemStackBuilder(@NonNull ItemStack itemStack) {

        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    @NonNull
    public static ItemStackBuilder of(@NonNull Material material) {

        final ItemStack itemStack = new ItemStack(material);
        return new ItemStackBuilder(itemStack);
    }

    @NonNull
    public static ItemStackBuilder of(@NonNull Material material, int amount) {

        final ItemStack itemStack = new ItemStack(material, Math.min(amount, 64));
        return new ItemStackBuilder(itemStack);
    }

    @NonNull
    public static ItemStackBuilder of(@NonNull ItemStack itemStack) {

        return new ItemStackBuilder(itemStack);
    }

    @NonNull
    public static ItemStackBuilder empty() {

        final ItemStack itemStack = new ItemStack(Material.AIR, 1);
        return new ItemStackBuilder(itemStack);
    }

    public boolean equalsMaterial(Material material) {
        return itemStack.getType().equals(material);
    }

    public boolean equalsMaterial(Material... materials) {
        for (Material material : materials) {
            if (itemStack.getType().equals(material)) return true;
        }

        return false;
    }

    public boolean materialNameContainsString(String string) {
        return itemStack.getType().name().toUpperCase().contains(string.toUpperCase());
    }

    @NonNull
    public ItemStackBuilder amount(int amount) {

        itemStack.setAmount(Math.min(amount, 64));
        return this;
    }

    @NonNull
    public ItemStackBuilder material(@NonNull Material material) {

        itemStack.setType(material);
        return this;
    }

    @NonNull
    public ItemStackBuilder lore(String string) {

        itemMeta.setLore(List.of(string));
        return this;
    }

    @NonNull
    public ItemStackBuilder lore(List<String> strings) {

        itemMeta.setLore(strings);
        return this;
    }

    @NonNull
    public ItemStackBuilder displayName(String string) {

        itemMeta.setDisplayName(string);
        return this;
    }

    @NonNull
    public ItemStackBuilder unbreakable(boolean unbreakable) {

        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    @NonNull
    public ItemStackBuilder customModelData(int customModelData) {

        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    @NonNull
    public ItemStackBuilder localizedName(String localizedName) {

        itemMeta.setLocalizedName(localizedName);
        return this;
    }

    @NonNull
    public ItemStackBuilder attributeModifier(
            @NonNull Attribute attribute, @NonNull AttributeModifier attributeModifier) {

        itemMeta.addAttributeModifier(attribute, attributeModifier);
        return this;
    }

    @NonNull
    public ItemStackBuilder removeAttributeModifier(
            @NonNull Attribute attribute, @NonNull AttributeModifier attributeModifier) {

        itemMeta.removeAttributeModifier(attribute, attributeModifier);
        return this;
    }

    @NonNull
    public ItemStackBuilder removeAttributeModifier(@NonNull Attribute attribute) {

        itemMeta.removeAttributeModifier(attribute);
        return this;
    }

    @NonNull
    public ItemStackBuilder removeAttributeModifier(@NonNull EquipmentSlot equipmentSlot) {

        itemMeta.removeAttributeModifier(equipmentSlot);
        return this;
    }

    @NonNull
    public ItemStackBuilder itemFlags(@NonNull ItemFlag... itemFlags) {

        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    @NonNull
    public ItemStackBuilder removeItemFlags(@NonNull ItemFlag... itemFlags) {

        itemMeta.removeItemFlags(itemFlags);
        return this;
    }

    @NonNull
    public ItemStackBuilder enchant(@NonNull Enchantment enchantment, int level) {

        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    @NonNull
    public ItemStackBuilder removeEnchant(@NonNull Enchantment enchantment) {

        itemMeta.removeEnchant(enchantment);
        return this;
    }

    @NonNull
    public ItemStackBuilder fakeEnchantment(boolean yes) {

        if (yes) {
            itemMeta.addEnchant(Enchantment.LUCK, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            itemMeta.removeEnchant(Enchantment.LUCK);
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        return this;
    }

    @NonNull
    public ItemStackBuilder colorBuild(int red, int green, int blue) {
        //Только для кожаных предметов

        if (!itemStack.getType().name().toUpperCase().contains("LEATHER")) {
            return this;
        }

        final LeatherArmorMeta colorItemMeta = (LeatherArmorMeta) getItemMeta();
        colorItemMeta.setColor(Color.fromRGB(red, green, blue));
        itemStack.setItemMeta(colorItemMeta);

        return this;
    }

    @NonNull
    public ItemStack build() {

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
