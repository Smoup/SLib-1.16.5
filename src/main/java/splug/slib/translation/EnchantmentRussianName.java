package splug.slib.translation;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.enchantments.Enchantment;

//Для 1.16.5

@Getter @SuppressWarnings("unused")
public enum EnchantmentRussianName {

    PROTECTION_ENVIRONMENTAL(Enchantment.PROTECTION_ENVIRONMENTAL, "Защита"),
    PROTECTION_FIRE(Enchantment.PROTECTION_FIRE, "Огнеупорность"),
    PROTECTION_FALL(Enchantment.PROTECTION_FALL, "Невесомость"),
    PROTECTION_EXPLOSIONS(Enchantment.PROTECTION_EXPLOSIONS, "Взрывоустойчивость"),
    PROTECTION_PROJECTILE(Enchantment.PROTECTION_PROJECTILE, "Защита от снарядов"),
    OXYGEN(Enchantment.OXYGEN, "Подводное дыхание"),
    WATER_WORKER(Enchantment.WATER_WORKER, "Подводник"),
    THORNS(Enchantment.THORNS, "Шипы"),
    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER, "Подводная ходьба"),
    FROST_WALKER(Enchantment.FROST_WALKER, "Ледоход"),
    BINDING_CURSE(Enchantment.BINDING_CURSE, "Проклятие несъёмности"),
    DAMAGE_ALL(Enchantment.DAMAGE_ALL, "Острота"),
    DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD, "Небесная кара"),
    DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "Бич членистоногих"),
    KNOCKBACK(Enchantment.KNOCKBACK, "Отдача"),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "Заговор Огня"),
    LOOT_BONUS_MOBS(Enchantment.LOOT_BONUS_MOBS, "Добыча"),
    SWEEPING_EDGE(Enchantment.SWEEPING_EDGE, "Разящий клинок"),
    DIG_SPEED(Enchantment.DIG_SPEED, "Эффективность"),
    SILK_TOUCH(Enchantment.SILK_TOUCH, "Шёлковое касание"),
    DURABILITY(Enchantment.DURABILITY, "Прочность"),
    LOOT_BONUS_BLOCKS(Enchantment.LOOT_BONUS_BLOCKS, "Удача"),
    ARROW_DAMAGE(Enchantment.ARROW_DAMAGE, "Сила"),
    ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK, "Откидывание"),
    ARROW_FIRE(Enchantment.ARROW_FIRE, "Воспламенение"),
    ARROW_INFINITE(Enchantment.ARROW_INFINITE, "Бесконечность"),
    LUCK(Enchantment.LUCK, "Везучий Рыбак"),
    LURE(Enchantment.LURE, "Приманка"),
    LOYALTY(Enchantment.LOYALTY, "Верность"),
    IMPALING(Enchantment.IMPALING, "Пронзатель"),
    RIPTIDE(Enchantment.RIPTIDE, "Тягун"),
    CHANNELING(Enchantment.CHANNELING, "Громовержец"),
    MULTISHOT(Enchantment.MULTISHOT, "Тройной выстрел"),
    QUICK_CHARGE(Enchantment.QUICK_CHARGE, "Быстрая Перезарядка"),
    PIERCING(Enchantment.PIERCING, "Пронзающая стрела"),
    MENDING(Enchantment.MENDING, "Починка"),
    VANISHING_CURSE(Enchantment.VANISHING_CURSE, "Проклятие утраты"),
    SOUL_SPEED(Enchantment.SOUL_SPEED, "Скорость Души");

    private final Enchantment enchantment;
    private final String russianName;

    EnchantmentRussianName(@NonNull Enchantment enchantment, @NonNull String russianName) {

        this.enchantment = enchantment;
        this.russianName = russianName;
    }

    @NonNull
    public static String getByEnchantment(@NonNull Enchantment enchantment) {
        for (EnchantmentRussianName value : EnchantmentRussianName.values()) {
            if (!value.getEnchantment().equals(enchantment)) continue;

            return value.getRussianName();
        }

        return "Неизвестное зачарование";
    }

}
