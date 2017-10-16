#!/bin/bash
ficheiro=./utilizadores.txt
valor=0
if [ -n "$1" ] && [ -n "$2" ] && [ -f $ficheiro ]; then
	if [ -n "$(cat $ficheiro | grep "^$1")" ]; then
		valor=$(cat $ficheiro | grep "^$1" | cut -d';' -f7)
		if [ $2 -ge 0 ]; then
			$(cat $ficheiro | grep "^$1" | cut -d';' -f7 | sed -i -e "s/$valor/$(( $valor+$2 ))/" $ficheiro)
			echo "Foi adicionado $2 minutos ao utilizador $1 com sucesso"
		else
			echo "Não podes adicionar valores negativos"
		fi
       else
	       echo "Utilizador $1 não encontrado"
       fi
else
	echo "Adicione o nome de utilizador e saldo a adicionar como parâmetros de execução"
fi
