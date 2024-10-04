# LibraryAssistant
## Запуск
Для запуска приложения необходимо скачать репозиторий, иметь установленный Docker и свободные порты 5432 для PostgreSQL и 8080 для приложения
1. Клонирование репозитория
```
git clone https://github.com/KrolTryCode/LibraryAssistant
cd LibraryAssistant
```
2. Запуск приложения
```
docker compose up --build
```
3. Остановка приложения
Для остановки всех запущенных контейнеров команда:
```
docker compose down
```

## Тесты
Тесты автоматиечски запускаются после сборки приложения в сервисе tests, но можно и вручную:
```
docker compose run tests
```
