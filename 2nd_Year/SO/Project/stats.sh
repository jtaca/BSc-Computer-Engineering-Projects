#!/bin/bash
utilizadores=./utilizadores.txt
viaturas=./viaturas.txt
echo "Número de utilizadores inscritos: $(cat $utilizadores | wc -l)"
echo "-----------------------------------------------"
echo "Número de viaturas no sistema: $(cat $viaturas | wc -l) sendo estas: "
echo "$(cat $viaturas | cut -d';' -f2 | sort | uniq -c | awk '{print $1" são da cor "$2 }')"
echo "-----------------------------------------------"

cat $utilizadores | awk -F ';' 'BEGIN{saldo=0} {saldo=saldo+$7} END { print "O valor total dos saldos dos utilizadores é:" saldo }'

echo "-----------------------------------------------"
echo "Top 5 Utilizadores com mais saldo:"
echo $(cat $utilizadores | sort -k 7 -g -r | head -n 5 | cut -d';' -f2)
echo "-----------------------------------------------"

echo "Utilizadores por curso:"
echo "$(cat $utilizadores | cut -d';' -f6 | sort | uniq -c | awk '{print $2":", $1}')"

