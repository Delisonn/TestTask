# Запуск проєкту в Android Studio

Ця інструкція допоможе вам запустити проєкт в Android Studio.

## Крок 1: Клонування репозиторію

1. Відкрийте Android Studio.
2. Виберіть опцію "Check out project from Version Control" на головному екрані.
3. Оберіть "Git" та вставте URL репозиторію.
4. Клацніть на "Clone".

## Крок 2: Відкриття проєкту

1. Після клонування виберіть опцію "Open an existing Android Studio project".
2. Перейдіть до теки, в яку ви клонували репозиторій.
3. Оберіть файл `build.gradle` у корені проєкту та клацніть "Open".

## Крок 3: Налаштування залежностей

1. Відкрийте `build.gradle` файл у каталозі `app`.
2. Переконайтеся, що всі залежності правильно налаштовані та відсутні помилки.
3. Виберіть "Sync Now" у верхньому правому куті, щоб синхронізувати залежності.
4. Також не забудьте додати файл `locale.properties` у корень каталогу проєкту зі своїм шляхом до Sdk.

## Крок 4: Запуск додатку

1. Виберіть цільовий пристрій (симулятор чи реальний пристрій) зі списку доступних пристроїв.
2. Натисніть на зелений трикутник "Run" або використайте `Shift + F10`, щоб запустити додаток.

Тепер ваш проєкт повинен успішно запуститися на вибраному пристрої!
