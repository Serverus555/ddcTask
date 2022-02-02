# ddcTask
Docker: serverus555/ddc-task
# Работа с типами новостей
- Создание: post на /newsType  
  - Параметры: name, color
- Чтение: get на /newsType/{id}
- Получить всё: get на /newsType
- Обновление: put на /newsType/{id}  
  - Возможные параметры: name, color
- Удаление: delete на /newsType/{id}
  
# Работа с новостями
- Создание: post на /news  
  - Параметры: name, shortDescription, description, typeId
- Чтение: get на /news/{id}
- Получить всё: get на /news  
  - В ответ приходят новости без полного описания
- Получить новости по типу: get на /news/byType/{typeId}
- Обновление: put на /news/{id}  
  - Возможные параметры: name, shortDescription, description, typeId
- Удаление: delete на /news/{id}
