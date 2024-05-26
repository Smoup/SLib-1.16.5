package splug.slib.config.parsers.item;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;
import splug.slib.utils.items.ItemStackBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@UtilityClass
@SuppressWarnings("unused")
@ToString @EqualsAndHashCode
public class AttributeModifiersParser {

    public static void handleAttributeModifiers(Logger logger, ItemStackBuilder itemStackBuilder,
                                                 ConfigurationSection itemSection) {
        final ConfigurationSection attributesSection = itemSection.getConfigurationSection("attribute-modifiers");

        if (attributesSection == null) return;

        for (final String attributeName : attributesSection.getKeys(false)) {
            final ConfigurationSection attributeSection = attributesSection.getConfigurationSection(attributeName);
            if (attributeSection == null) continue;

            parseAttributeSection(logger, itemStackBuilder, attributeSection);
        }
    }

    private static void parseAttributeSection(Logger logger, ItemStackBuilder itemStackBuilder,
                                              ConfigurationSection attributeSection) {
        final Attribute attribute = parseAttribute(logger, attributeSection);
        if (attribute == null) return;

        final Double modifier = parseModifier(logger, attributeSection);
        if (modifier == null) return;

        final AttributeModifier.Operation operation = parseOperation(logger, attributeSection);
        if (operation == null) return;

        final Set<EquipmentSlot> equipmentSlots = parseEquipmentSlots(logger, attributeSection);

        if (equipmentSlots.isEmpty()) {
            final AttributeModifier attributeModifier = new AttributeModifier(attribute.name(), modifier, operation);
            itemStackBuilder.attributeModifier(attribute, attributeModifier);
            return;
        }

        for (final EquipmentSlot equipmentSlot : equipmentSlots) {
            final AttributeModifier attributeModifier = new AttributeModifier(
                    UUID.randomUUID(), attribute.name(), modifier, operation, equipmentSlot);
            itemStackBuilder.attributeModifier(attribute, attributeModifier);
        }
    }

    private static Double parseModifier(Logger logger, ConfigurationSection attributeSection) {
        final double modifier = attributeSection.getDouble("modifier");
        if (modifier == 0) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §fИспользован 0 модификатор(§cmodifier§f) для §6%s §f|" +
                    " Используйте значение больше 0 §f| путь: %s")
                    .formatted(attributeSection.getName(), attributeSection.getCurrentPath()));
            return null;
        }
        return modifier;
    }

    private static Attribute parseAttribute(Logger logger, ConfigurationSection attributeSection) {
        final Attribute attribute;
        try {
            attribute = Attribute.valueOf(attributeSection.getName());
        } catch (IllegalArgumentException e) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип attribute §6%s §f|" +
                    " Используйте значение из org.bukkit.attribute.Attribute §f| путь: %s")
                    .formatted(attributeSection.getName(), attributeSection.getCurrentPath()));
            return null;
        }
        return attribute;
    }

    private static AttributeModifier.Operation parseOperation(Logger logger, ConfigurationSection attributeSection) {
        final AttributeModifier.Operation operation;
        final String operationName = attributeSection.getString("modify-type", "default");

        try {
            operation = AttributeModifier.Operation.valueOf(operationName);
        } catch (IllegalArgumentException e) {
            logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип Operation(§6modify-type§c) §6%s §f|" +
                    " Используйте значение из org.bukkit.attribute.AttributeModifier.Operation §f| путь: %s")
                    .formatted(operationName, attributeSection.getCurrentPath()));
            return null;
        }
        return operation;
    }

    private static Set<EquipmentSlot> parseEquipmentSlots(Logger logger, ConfigurationSection attributeSection) {
        final Set<EquipmentSlot> out = new HashSet<>();

        final List<String> slotsSection = attributeSection.getStringList("equipments-slots");

        for (final String slotName : slotsSection) {
            final EquipmentSlot equipmentSlot;

            try {
                equipmentSlot = EquipmentSlot.valueOf(slotName);
            } catch (IllegalArgumentException e) {
                logger.warning(("§f[§6Config§f] [§6ItemStackParser§f] §cНекорректный тип EquipmentSlot §6%s §f|" +
                        " Используйте значение из org.bukkit.inventory.EquipmentSlot §f| путь: %s")
                        .formatted(slotName, attributeSection.getCurrentPath()));
                continue;
            }

            out.add(equipmentSlot);
        }

        return out;
    }
}
