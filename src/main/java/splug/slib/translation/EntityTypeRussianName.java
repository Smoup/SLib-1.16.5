package splug.slib.translation;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.EntityType;
import splug.slib.utils.strings.StringsSimilarity;

@Getter @SuppressWarnings("unused")
public enum EntityTypeRussianName {
    DROPPED_ITEM(EntityType.DROPPED_ITEM, "Выброшенный предмет"),
    EXPERIENCE_ORB(EntityType.EXPERIENCE_ORB, "Опыт"),
    AREA_EFFECT_CLOUD(EntityType.AREA_EFFECT_CLOUD, "Облако эффектов"),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, "Древний страж"),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, "Визер-скелет"),
    STRAY(EntityType.STRAY, "Зимогор"),
    EGG(EntityType.EGG, "Брошенное яйцо"),
    LEASH_HITCH(EntityType.LEASH_HITCH, "Узел поводка"),
    PAINTING(EntityType.PAINTING, "Картина"),
    ARROW(EntityType.ARROW, "Стрела"),
    SNOWBALL(EntityType.SNOWBALL, "Снежок"),
    FIREBALL(EntityType.FIREBALL, "Файербол"),
    SMALL_FIREBALL(EntityType.SMALL_FIREBALL, "Малый файербол"),
    ENDER_PEARL(EntityType.ENDER_PEARL, "Брошенный эндер-жемчуг"),
    ENDER_SIGNAL(EntityType.ENDER_SIGNAL, "Око Эндера"),
    SPLASH_POTION(EntityType.SPLASH_POTION, "Зелье"),
    THROWN_EXP_BOTTLE(EntityType.THROWN_EXP_BOTTLE, "Брошенный пузырёк опыта"),
    ITEM_FRAME(EntityType.ITEM_FRAME, "Рамка"),
    WITHER_SKULL(EntityType.WITHER_SKULL, "Череп визера"),
    PRIMED_TNT(EntityType.PRIMED_TNT, "Активированный динамит"),
    FALLING_BLOCK(EntityType.FALLING_BLOCK, "Падающий блок"),
    FIREWORK(EntityType.FIREWORK, "Фейерверк"),
    HUSK(EntityType.HUSK, "Кадавр"),
    SPECTRAL_ARROW(EntityType.SPECTRAL_ARROW, "Спектральная стрела"),
    SHULKER_BULLET(EntityType.SHULKER_BULLET, "Снаряд шалкера"),
    DRAGON_FIREBALL(EntityType.DRAGON_FIREBALL, "Файербол дракона"),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, "Крестьянин-зомби"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, "Лошадь-скелет"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, "Лошадь-зомби"),
    ARMOR_STAND(EntityType.ARMOR_STAND, "Стойка для брони"),
    DONKEY(EntityType.DONKEY, "Осёл"),
    MULE(EntityType.MULE, "Мул"),
    EVOKER_FANGS(EntityType.EVOKER_FANGS, "Клыки заклинателя"),
    EVOKER(EntityType.EVOKER, "Заклинатель"),
    VEX(EntityType.VEX, "Вредина"),
    VINDICATOR(EntityType.VINDICATOR, "Поборник"),
    ILLUSIONER(EntityType.ILLUSIONER, "Иллюзор"),
    MINECART_COMMAND(EntityType.MINECART_COMMAND, "Вагонетка с командным блоком"),
    BOAT(EntityType.BOAT, "Лодка"),
    MINECART(EntityType.MINECART, "Вагонетка"),
    MINECART_CHEST(EntityType.MINECART_CHEST, "Грузовая вагонетка"),
    MINECART_FURNACE(EntityType.MINECART_FURNACE, "Самоходная вагонетка"),
    MINECART_TNT(EntityType.MINECART_TNT, "Вагонетка с динамитом"),
    MINECART_HOPPER(EntityType.MINECART_HOPPER, "Загрузочная вагонетка"),
    MINECART_MOB_SPAWNER(EntityType.MINECART_MOB_SPAWNER, "Вагонетка с рассадником"),
    CREEPER(EntityType.CREEPER, "Крипер"),
    SKELETON(EntityType.SKELETON, "Скелет"),
    SPIDER(EntityType.SPIDER, "Паук"),
    GIANT(EntityType.GIANT, "Гигант"),
    ZOMBIE(EntityType.ZOMBIE, "Зомби"),
    SLIME(EntityType.SLIME, "Слизень"),
    GHAST(EntityType.GHAST, "Гаст"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, "Зомбифицированный пиглин"),
    ENDERMAN(EntityType.ENDERMAN, "Эндермен"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, "Пещерный паук"),
    SILVERFISH(EntityType.SILVERFISH, "Чешуйница"),
    BLAZE(EntityType.BLAZE, "Всполох"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, "Магмовый куб"),
    ENDER_DRAGON(EntityType.ENDER_DRAGON, "Эндер-дракон"),
    WITHER(EntityType.WITHER, "Визер"),
    BAT(EntityType.BAT, "Летучая мышь"),
    WITCH(EntityType.WITCH, "Ведьма"),
    ENDERMITE(EntityType.ENDERMITE, "Эндермит"),
    GUARDIAN(EntityType.GUARDIAN, "Страж"),
    SHULKER(EntityType.SHULKER, "Шалкер"),
    PIG(EntityType.PIG, "Свинья"),
    SHEEP(EntityType.SHEEP, "Овца"),
    COW(EntityType.COW, "Корова"),
    CHICKEN(EntityType.CHICKEN, "Курица"),
    SQUID(EntityType.SQUID, "Спрут"),
    WOLF(EntityType.WOLF, "Волк"),
    MUSHROOM_COW(EntityType.MUSHROOM_COW, "Грибная-корова"),
    SNOWMAN(EntityType.SNOWMAN, "Снеговик"),
    OCELOT(EntityType.OCELOT, "Оцелот"),
    IRON_GOLEM(EntityType.IRON_GOLEM, "Железный голем"),
    HORSE(EntityType.HORSE, "Лошадь"),
    RABBIT(EntityType.RABBIT, "Кролик"),
    POLAR_BEAR(EntityType.POLAR_BEAR, "Белый медведь"),
    LLAMA(EntityType.LLAMA, "Лама"),
    LLAMA_SPIT(EntityType.LLAMA_SPIT, "Плевок ламы"),
    PARROT(EntityType.PARROT, "Попугай"),
    VILLAGER(EntityType.VILLAGER, "Кресьянин"),
    ENDER_CRYSTAL(EntityType.ENDER_CRYSTAL, "Кристалл Энда"),
    TURTLE(EntityType.TURTLE, "Черепаха"),
    PHANTOM(EntityType.PHANTOM, "Фантом"),
    TRIDENT(EntityType.TRIDENT, "Трезубец"),
    COD(EntityType.COD, "Треска"),
    SALMON(EntityType.SALMON, "Лосось"),
    PUFFERFISH(EntityType.PUFFERFISH, "Иглобрюх"),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, "Тропическая рыба"),
    DROWNED(EntityType.DROWNED, "Утопленник"),
    DOLPHIN(EntityType.DOLPHIN,"Дельфин"),
    CAT(EntityType.CAT, "Кошка"),
    PANDA(EntityType.PANDA, "Панда"),
    PILLAGER(EntityType.PILLAGER, "Разбойник"),
    RAVAGER(EntityType.RAVAGER, "Разоритель"),
    TRADER_LLAMA(EntityType.TRADER_LLAMA, "Лама торговца"),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, "Странствующий торговец"),
    FOX(EntityType.FOX, "Лисица"),
    BEE(EntityType.BEE, "Пчела"),
    HOGLIN(EntityType.HOGLIN, "Хоглин"),
    PIGLIN(EntityType.PIGLIN, "Пиглин"),
    STRIDER(EntityType.SPIDER, "Паук"),
    ZOGLIN(EntityType.ZOGLIN, "Зоглин"),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, "Брутальный пиглин"),
    FISHING_HOOK(EntityType.FISHING_HOOK, "Поплавок"),
    LIGHTNING(EntityType.LIGHTNING, "Молния"),
    PLAYER(EntityType.PLAYER, "Игрок"),
    UNKNOWN(EntityType.UNKNOWN, "Неизвестный тип");

    private final EntityType entityType;
    private final String russianName;

    EntityTypeRussianName(EntityType entityType, String russianName) {
        this.entityType = entityType;
        this.russianName = russianName;
    }

    @NonNull
    public static EntityTypeRussianName mostSimilar(String similar) {
        final char[] charsToCheck = similar.toCharArray();
        double similarPercent = 0;
        EntityTypeRussianName target = UNKNOWN;

        for (final EntityTypeRussianName value : values()) {
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
    public static String getByEntityType(@NonNull EntityType type) {
        for (final EntityTypeRussianName value : values()) {
            if (!value.getEntityType().equals(type)) continue;
            return value.getRussianName();
        }

        return "Неизвестная сущность";
    }
}
