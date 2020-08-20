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
---------------Для всех пользователей---------------

help - Вывод списка терминальных команд
version - Информация о версии приложения
about - Информация о разработчике
exit - Выход из приложения
login - Аутентификация пользователя
logout - Завершение сеанса пользователя
command-history - Вывод списка 10 последних введенных команд

---------Для авторизированных пользователей---------

project-create - Создание проекта
project-list - Вывод списка проектов
project-list-with-task - Вывод списка проектов с подзадачами
project-clear - Очистка списка проектов
project-view-by-index - Просмотр проекта по номеру
project-view-by-id - Просмотр проекта по ID
project-remove-by-name - Удаление проекта по имени
project-remove-by-id - Удаление проекта по ID
project-remove-by-index - Удаление проекта по номеру
project-remove-with-tasks-by-id - Удаление проекта с его задачами по ID проекта.
project-update-by-index - Обновление проекта по номеру
project-update-by-id - Обновление проекта по ID
project-assign-by-name-to-user-by-id - Назначение проекта по имени пользователю по ID

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
task-assign-by-name-to-user-by-id - Назначение задачи по имени пользователю по ID

user-create - Добавление нового пользователя
user-list - Список пользователей
user-clear - Очистка списка пользователей
user-view-by-id - Просмтор пользователя по ID
user-view-by-index - Просмтор пользователя по номеру
user-view-by-login - Просмтор пользователя по логину
user-remove-by-id - Удаление пользователя по ID
user-remove-by-index - Удаление пользователя по номеру
user-remove-by-login - Удаление пользователя по логину
user-update-by-id - Обновление пользователя по ID
user-update-by-index - Обновление пользователя по номеру
user-update-by-login - Обновление пользователя по логину
user-update-password - Обновление пароля пользователя
user-view-current - Просмотр данных текущего пользователя
user-update-current - Обновление данных текущего пользователя
```
