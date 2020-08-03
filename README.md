<h1 align="center">task-manager</h1>

Учебный проект в рамках курса по программе JAVA SPRING.

Зеркало: [JSE github.com](https://github.com/KOPTEC-PRGM/jse)

## Требования к software

* Java OpenJDK version "11"
* Apache Maven 3.6.1

## Описание стека технологий

* Java SE 
* Сборщик проектов Apache Maven

## Разработчик

**Потапов Вадим** potapov_vs@nlmk.com

## Команды для сборки приложения

Очистка и удаление каталога `\target`
```
mvn clean
```
Создание каталога `\target` и сборка проекта
```
mvn package
```
## Команда для запуска приложения

```
java -jar target\task-manager-1.0.0.jar
```

## Поддерживаемые терминальные команды

```
help - Вывод списка терминальных команд
version - Информация о версии приложения
about - Информация о разработчике
exit - Выход из приложения

project-create - Создание проекта
project-list - Вывод списка проектов
project-clear - Очистка списка проектов
project-view-by-index - Просмотр проекта по номеру
project-view-by-id - Просмотр проекта по ID
project-remove-by-name - Удаление проекта по имени
project-remove-by-id - Удаление проекта по ID
project-remove-by-index - Удаление проекта по номеру
project-remove-with-tasks-by-id - Удаление проекта с его задачами по ID проекта.
project-update-by-index - Обновление проекта по номеру
project-update-by-id - Обновление проекта по ID

task-create - Создание задачи
task-list - Вывод списка задач
task-clear - Очистка списка задач
task-view-by-index - Просмотр задачи по номеру
task-view-by-id - Просмотр задачи по ID
task-remove-by-name - Удаление задачи по имени
task-remove-by-id - Удаление задачи по ID
task-remove-by-index - Удаление задачи по номеру
task-update-by-index - Обновление задачи по номеру
task-update-by-id - Обновление задачи по ID
task-list-by-project-id - Список задач в проекте по ID проекта
task-add-to-project-by-ids - Добавление задачи в проект по ID проекта и ID задачи
task-remove-from-project-by-ids - Удаление задачи из проекта по ID проекта и ID задачи
        
```
