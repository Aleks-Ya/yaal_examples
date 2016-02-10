#!/bin/bash

# Инициализация массива 1
names[0]='John'
names[1]='Mary'

# Инициализация массива 2
declare -a cities=('Moscow' 'Spb');

# Размер массива
echo Array lenght: ${#cities[@]}

# Добавление элементов в существующий массив
declare -a girls=('Mary' 'Nina');
girls=("${girls[@]}" 'Laura' 'Gil');
echo girls lenght: ${#girls[@]}