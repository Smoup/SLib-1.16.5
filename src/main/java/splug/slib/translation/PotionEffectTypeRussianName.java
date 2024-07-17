package splug.slib.translation;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import splug.slib.utils.strings.StringsSimilarity;

@Getter @SuppressWarnings("unused")
public enum PotionEffectTypeRussianName {
    SPEED(PotionEffectType.SPEED, "Скорость"),
    SLOW(PotionEffectType.SLOW, "Замедление"),
    FAST_DIGGING(PotionEffectType.FAST_DIGGING, "Спешка"),
    SLOW_DIGGING(PotionEffectType.SLOW_DIGGING, "Утомление"),
    INCREASE_DAMAGE(PotionEffectType.INCREASE_DAMAGE, "Сила"),
    HEAL(PotionEffectType.HEAL, "Исцеление"),
    HARM(PotionEffectType.HARM, "Моментальный урон"),
    JUMP(PotionEffectType.JUMP, "Прыгучесть"),
    CONFUSION(PotionEffectType.CONFUSION, "Тошнота"),
    REGENERATION(PotionEffectType.REGENERATION, "Регенерация"),
    DAMAGE_RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, "Сопротивление"),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, "Огнестойкость"),
    WATER_BREATHING(PotionEffectType.WATER_BREATHING, "Водное дыхание"),
    INVISIBILITY(PotionEffectType.INVISIBILITY, "Невидимость"),
    BLINDNESS(PotionEffectType.BLINDNESS, "Слепота"),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION, "Ночное зрение"),
    HUNGER(PotionEffectType.HUNGER, "Голод"),
    WEAKNESS(PotionEffectType.WEAKNESS, "Слабость"),
    POISON(PotionEffectType.POISON, "Отравление"),
    WITHER(PotionEffectType.WITHER, "Иссушение"),
    HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, "Прилив здоровья"),
    ABSORPTION(PotionEffectType.ABSORPTION, "Поглощение"),
    SATURATION(PotionEffectType.SATURATION, "Насыщенность"),
    GLOWING(PotionEffectType.GLOWING, "Свечение"),
    LEVITATION(PotionEffectType.LEVITATION, "Левитация"),
    LUCK(PotionEffectType.LUCK, "Везение"),
    UNLUCK(PotionEffectType.UNLUCK, "Невезение"),
    SLOW_FALLING(PotionEffectType.SLOW_FALLING, "Плавное падение"),
    CONDUIT_POWER(PotionEffectType.CONDUIT_POWER, "Сила источника"),
    DOLPHINS_GRACE(PotionEffectType.DOLPHINS_GRACE, "Грация дельфина"),
    BAD_OMEN(PotionEffectType.BAD_OMEN, "Дурное знамение"),
    HERO_OF_THE_VILLAGE(PotionEffectType.HERO_OF_THE_VILLAGE, "Герой деревни");

    private final PotionEffectType potionEffectType;
    private final String russianName;

    PotionEffectTypeRussianName(@NotNull PotionEffectType type, @NotNull String russianName) {
        this.potionEffectType = type;
        this.russianName = russianName;
    }

    @NonNull
    public static PotionEffectTypeRussianName mostSimilar(String similar) {
        final char[] charsToCheck = similar.toCharArray();
        double similarPercent = 0;
        PotionEffectTypeRussianName target = SPEED;

        for (final PotionEffectTypeRussianName value : values()) {
            final double percent = StringsSimilarity
                    .calculate(charsToCheck, value.russianName.toCharArray());
            if (percent > similarPercent) {
                target = value;
                similarPercent = percent;
            }
        }

        return target;
    }

    @NonNull
    public static String getByEffectType(@NonNull PotionEffectType type) {
        for (final PotionEffectTypeRussianName value : values()) {
            if (!value.getPotionEffectType().equals(type)) continue;
            return value.getRussianName();
        }

        return "Неизвестный эффект";
    }
}
