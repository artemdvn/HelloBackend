# HelloBackend
 
REST сервис hello с ресурсом /hello/contacts?nameFilter=val
Возвращает массив контактов в JSON формате из таблицы БД contacts, кроме записей, в которых contacts.name совпадает с регулярным выражением (значение параметра nameFilter).
Использована СУБД MySQL, начальное заполнение базы - с помощью скрипта createTables.sql.

[![Build Status](https://travis-ci.org/artemdvn/HelloBackend.svg?branch=master)](https://travis-ci.org/artemdvn/HelloBackend)

