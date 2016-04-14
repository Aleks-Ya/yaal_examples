#!/bin/bash

# Вывод строки
echo Hello stupid
echo "Hello stupid 2"

# Пустая строка
echo; echo

# Синтаксис переменных
MESSAGE="Hello, var!"
echo "1: $MESSAGE"
echo "2: ${MESSAGE}"
echo 3: $MESSAGE
echo 4: ${MESSAGE}

# Пробелы сохраняются или нет
hello="A B  C   D"
echo $hello   # A B C D
echo "$hello" # A B  C   D

# Подавление $
esc=Escape
echo Escape var 1: '$esc'
echo Escape var 2: \$esc

# Кавычки обязательны
quotes_need="1 2 3"
echo $quotes_need
quotes_need2="Всем привет"
echo $quotes_need2

# Неинициализированная переменная
if [ -z "$unassigned" ]
then
  echo "\$unassigned is NULL."
fi

# Чтение переменных окружения
echo Path 1: $PATH
p=$PATH
echo Path 2: $p