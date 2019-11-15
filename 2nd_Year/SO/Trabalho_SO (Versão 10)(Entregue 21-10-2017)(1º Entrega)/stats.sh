#!/bin/bash
utilizadores=./utilizadores.txt
viaturas=./viaturas.txt
touch $utilizadores
touch $viaturas
listaDeCursos=("LEI" "ETI" "IGE")
definicaoDeTurma=("EI" "ET" "I")

echo "Número de utilizadores inscritos: $(cat $utilizadores | wc -l)"
echo "-----------------------------------------------"
echo "Número de viaturas no sistema: $(cat $viaturas | wc -l) sendo estas: "
echo "$(cat $viaturas | cut -d';' -f2 | sort | uniq -c | awk '{print $1" são da cor "$2 }')"
echo "-----------------------------------------------"

cat $utilizadores | awk -F ';' 'BEGIN{saldo=0} {saldo=saldo+$7} END { print "O valor total dos saldos dos utilizadores é:" saldo }'

echo "-----------------------------------------------"
echo "Top 5 Utilizadores com mais saldo:"
echo $(cat $utilizadores | sort -t';' -k 7 -g -r | head -n 5 | cut -d';' -f1)
echo "-----------------------------------------------"

echo "Utilizadores por curso:"
for (( i=0;  i<${#listaDeCursos[*]}; i++ )); do
	echo "${listaDeCursos[i]} ":" $(cat $utilizadores | cut -d';' -f6 | grep "^${definicaoDeTurma[i]}" | wc -l)"
done
