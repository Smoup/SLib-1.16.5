# Настройки для предмета

## Настройки предмета
```yaml
items:
  item-key:
    material: 'STONE'
    player-head-texture: "Значение с сайта https://minecraft-heads.com/ For Developers: Value"
    rgb: 153, 217, 236
    display-name: "§f§lЛопаточка"
    lore:
      - '§fСтрока1'
      - '§cСтрока2'
    amount: 5
    unbreakable: true
    custom-model-data: 123
    localized-name: "localized-name"
    fake-enchant: true
    un-stackable: true
    
    enchants:
      DURABILITY: 10
    
    item-flags:
      HIDE_ENCHANTS: true
    
    attribute-modifiers:
      GENERIC_MAX_HEALTH:
        modifier: 1.0
        modify-type: ADD_NUMBER
        equipments-slots:
          - OFF_HAND

    tripped-arrow-effects:
      NIGHT_VISION:
        long-duration: true
        second-level: true

    potion-data:
      potion-type: WATER #org.bukkit.potion.PotionType
      extended: true #default - false
      upgraded: true #default - false
    
    potion-effects:
      INCREASE_DAMAGE:
        duration: 60 #секунды
        amplifier: 3 #уровень эффекта
        ambient: true #default - true
        particles: true #default - true
```
> [!IMPORTANT]
> Ключ предмета `item-key` должен быть уникален

| `Ключ`                | `Значение по умолчанию` |         `Пример значения`         | `Источники`                                                                                                                                                                                           | `Дополнительная информация`                                                                                                                                               |
|-----------------------|:-----------------------:|:---------------------------------:|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| material              |            -            |               STONE               | [Материалы](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html) (использовать `Enum Constant`)                                                                                         | Значение обязательно                                                                                                                                                      |
| player-head-texture   |            -            |           под таблицей            | [Текстуры-голов](https://minecraft-heads.com/) (использовать `For Developer: Value`)                                                                                                                  | Значение будет использовано если `material` = `PLAYER_HEAD`                                                                                                               |
| rgb                   |            -            |           153, 217, 236           | [RGB](https://g.co/kgs/jMKSRgx) (использовать `RGB`)                                                                                                                                                  | Значение будет использовано если `material` содержит в названии `LEATHER` или `material` = `POTION` или `SPLASH_POTION` или `LINGERING_POTION` или `TIPPED_ARROW`         |
| display-name          |            -            |           "§f§lПредмет"           | [цвета](https://www.birdflop.com/resources/rgb/)                                                                                                                                                      | Для название предмета можно использовать цвета `§fТекст`                                                                                                                  |
| lore                  |            -            | - '§fСтрока1' <br/> - '§cСтрока2' | [цвета](https://www.birdflop.com/resources/rgb/)                                                                                                                                                      | В описании предмета можно использовать несколько строк `§fСтрока1` `§cСтрока2`                                                                                            |
| amount                |            1            |                 8                 | <div align="center"> -                                                                                                                                                                                | Количество может быть в пределах `1-64`                                                                                                                                   |
| unbreakable           |          false          |               true                | <div align="center"> -                                                                                                                                                                                | Будет ли предмет ломаться                                                                                                                                                 |
| custom-model-data     |            -            |               12345               | [Wiki](https://www.planetminecraft.com/forums/communities/texturing/new-1-14-custom-item-models-tuto-578834/)                                                                                         | Альтернативная модель текстуры                                                                                                                                            |
| localized-name        |            -            |             "пример"              | <div align="center"> -                                                                                                                                                                                | <div align="center"> -                                                                                                                                                    |
| fake-enchant          |          false          |               true                | <div align="center"> -                                                                                                                                                                                | Будет ли предмет иметь зачарованную текстуру без зачарования в описании                                                                                                   |
| un-stackable          |          false          |               true                | <div align="center"> -                                                                                                                                                                                | Будет ли предмет иметь возможность стакаться если предмет имеет такую возможность изначально <br/> это возможно за счет добавления в PDC предмета id из UUID.randomUUID() |
| enchants              |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5#%D0%B7%D0%B0%D1%87%D0%B0%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F-enchants)                                                                            | <div align="center"> -                                                                                                                                                    |
| item-flags            |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5#%D1%84%D0%BB%D0%B0%D0%B3%D0%B8-%D0%BF%D1%80%D0%B5%D0%B4%D0%BC%D0%B5%D1%82%D0%B0-item-flags)                                                             | <div align="center"> -                                                                                                                                                    |
| attribute-modifiers   |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5#%D0%B0%D1%82%D1%80%D0%B8%D0%B1%D1%83%D1%82%D1%8B-%D0%BF%D1%80%D0%B5%D0%B4%D0%BC%D0%B5%D1%82%D0%B0-attribute-modifiers)                                  | <div align="center"> -                                                                                                                                                    |
| tripped-arrow-effects |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5#%D1%8D%D1%84%D1%84%D0%B5%D0%BA%D1%82%D1%8B-%D1%81%D1%82%D1%80%D0%B5%D0%BB%D1%8B-tripped-arrow-effects)                                                  | Значение будет использовано если `material` = `TIPPED_ARROW`                                                                                                              |
| potion-data           |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5/tree/master?tab=readme-ov-file#%D0%BD%D0%B0%D1%81%D1%82%D1%80%D0%BE%D0%B9%D0%BA%D0%B8-%D0%B7%D0%B5%D0%BB%D0%B8%D0%B9-potion-data-%D0%B8-potion-effects) | Значение будет использовано если `material` = `POTION` или `SPLASH_POTION` или `LINGERING_POTION`                                                                         |
| potion-effects        |            -            |                 -                 | [`Клик`](https://github.com/Smoup/SLib-1.16.5/tree/master?tab=readme-ov-file#%D0%BD%D0%B0%D1%81%D1%82%D1%80%D0%BE%D0%B9%D0%BA%D0%B8-%D0%B7%D0%B5%D0%BB%D0%B8%D0%B9-potion-data-%D0%B8-potion-effects) | Значение будет использовано если `material` = `POTION` или `SPLASH_POTION` или `LINGERING_POTION`                                                                         |

> [!NOTE]
> `player-head-texture` пример значения:
> `eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU3OWQ3MTNhYTE4NzI4MGM4NDJjMWQyMGMzNzQ5N2IwOTEzMzk4NjI0MTI0NmNmNmEwY2U5MDVmYjk2MmU5YSJ9fX0=`



### Зачарования `enchants`
```yaml
enchants:
  ENCHANT-KEY: enchant-lvl
  EFFICIENCY: 7
```

> [!NOTE]
> `ENCHANT-KEY` значение с [`Зачарования`](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html)

> [!NOTE]
> `enchant-lvl` значением является уровень зачарования `число`



### Флаги предмета `item-flags`
```yaml
item-flags:
  ITEM-FLAG-KEY: boolean
  HIDE_ENCHANTS: true
```

> [!NOTE]
> `ITEM-FLAG-KEY` значение с [`Флаги-предметов`](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemFlag.html)

> [!NOTE]
> `boolean` значением является `true`



### Атрибуты предмета `attribute-modifiers`
```yaml
attribute-modifiers:
  ATTRIBUTE-MODIFIER-KEY:
    modifier: 1.0
    modify-type: ADD_NUMBER
    equipments-slots:
      - FEET
      - HEAD
  GENERIC_MAX_HEALTH:
    modifier: 1.0
    modify-type: ADD_NUMBER
    equipments-slots:
      - OFF_HAND
```

> [!NOTE]
> `ATTRIBUTE-MODIFIER-KEY` значение с [`Атрибуты-предметов`](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/Attribute.html)

| `ключ`            | `значение по умолчанию` | `пример значения`       | `источник`                                                                                                         | `Дополнительная информация`                                               |
|-------------------|:-----------------------:|-------------------------|--------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| modifier          |            -            | 1.0                     | <div align="center"> -                                                                                             | Значение по умолчанию не предусмотренно, но значение обязательно          |
| modify-type       |            -            | ADD_NUMBER              | [Тип-модификатора](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/AttributeModifier.Operation.html) | Значение по умолчанию не предусмотренно, но значение обязательно          |
| equipments-slots  |        Все слоты        | - OFF_HAND <br/> - LEGS | [Тип-слота](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/EquipmentSlot.html)                      | Если не указать ни одного слота, то предмет будет работать во всех слотах |



### Эффекты стрелы `tripped-arrow-effects`
```yaml
tripped-arrow-effects:
  EFFECT-KEY:
    long-duration: true
    second-level: true
  NIGHT_VISION:
    long-duration: true
    second-level: false
```

> [!NOTE]
> `EFFECT-KEY` значение с [`Эффекты`](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionType.html)

> [!IMPORTANT]
> `long-duration` может быть `true` только если существует стрела с данным `EFFECT-KEY`
> в ванильном майнкрафте и имеет несколько значений длительности
> пример: `NIGHT_VISION`

> [!IMPORTANT]
> `second-level` может быть `true` только если существует стрела с данным `EFFECT-KEY`
> в ванильном майнкрафте и имеет второй `II` уровень эффекта
> пример: `STRONG_HEALING`



### Настройки зелий `potion-data` и `potion-effects`
```yaml
potion-data:
  potion-type: WATER #org.bukkit.potion.PotionType
  extended: true #default - false
  upgraded: true #default - false

potion-effects:
  INCREASE_DAMAGE: #org.bukkit.potion.PotionEffectType
    duration: 60 #секунды
    amplifier: 3 #уровень эффекта
    ambient: true #default - true
    particles: true #default - true
```

> [!NOTE]
> `potion-type` значение с [`Типы зелий`](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionType.html)

> [!NOTE]
> `potion-effects` значение с [`Типы эффектов`](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html)

> [!IMPORTANT]
> `extended` может быть `true` только если существует зелье с данным `potion-type`
> в ванильном майнкрафте и имеет несколько значений длительности
> пример: `NIGHT_VISION`

> [!IMPORTANT]
> `upgraded` может быть `true` только если существует стрела с данным `potion-type`
> в ванильном майнкрафте и имеет второй `II` уровень эффекта
> пример: `STRONG_HEALING`



# Настройки для меню

### Основные
| `ключ`     | `значение по умолчанию` | `пример значения` | `Дополнительная информция`                             |
|------------|-------------------------|-------------------|--------------------------------------------------------|
| menu.title | -                       | "Магазинчик"      | Название меню-магазина                                 |
| menu.size  | 18                      | 54                | Размер меню-магазина `<=54` и деление на 9 без остатка | 

### Расположение предметов
| `ключ`     | `значение по умолчанию` | `пример значения`                       | `Дополнительная информция`                                                                                   |
|------------|-------------------------|-----------------------------------------|--------------------------------------------------------------------------------------------------------------|
| menu-slot  | 0                       | 15                                      | Номер слота в меню `0-53`                                                                                    |
| menu-slots | 0                       | - '0-4' <br/> - '34-34' <br/> - '45-53' | Значения не должны превышать `0-53` предметы будут во <br/> всех слотах которые входят в указанные интервалы | 



# Настройки босс-бара

## Bukkit (org.bukkit.boss.BossBar)
```yaml
  boss-bar:
    color: 'RED'
    style: 'SOLID'
    flags:
      - 'PLAY_BOSS_MUSIC'
```

| `ключ`         | `пример значения`                        | `Дополнительная информция`                                                                   |
|----------------|------------------------------------------|----------------------------------------------------------------------------------------------|
| boss-bar.color | 'RED'                                    | Список цветов [Клик](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/boss/BarColor.html) |
| boss-bar.style | 'SOLID'                                  | Список стилей [Клик](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/boss/BarStyle.html) | 
| boss-bar.flags | - 'PLAY_BOSS_MUSIC' <br/> - 'DARKEN_SKY' | Список флагов [Клик](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/boss/BarFlag.html)  |

## Paper (net.kyori.adventure.bossbar.BossBar)
```yaml
   boss-bar:
     color: 'RED'
     overlay: 'PROGRESS'
     flags:
       - 'DARKEN_SCREEN'
```

| `ключ`           | `пример значения`                           | `Дополнительная информция`                                                                                                               |
|------------------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| boss-bar.color   | 'RED'                                       | Список цветов [Клик](https://github.com/KyoriPowered/adventure/blob/main/4/api/src/main/java/net/kyori/adventure/bossbar/BossBar.java)   |
| boss-bar.overlay | 'PROGRESS'                                  | Список оверлеев [Клик](https://github.com/KyoriPowered/adventure/blob/main/4/api/src/main/java/net/kyori/adventure/bossbar/BossBar.java) | 
| boss-bar.flags   | - 'DARKEN_SCREEN' <br/> - 'PLAY_BOSS_MUSIC' | Список флагов [Клик](https://github.com/KyoriPowered/adventure/blob/main/4/api/src/main/java/net/kyori/adventure/bossbar/BossBar.java)   |