#!/bin/bash
ficheiro=./utilizadores.txt
valor=0
if [ -n "$1" ] && [ -n "$2" ] && [ -f $ficheiro ]; then
	if [ -n "$(cat $ficheiro | cut -d';' -f1 | grep "^$1$")" ]; then
		valor=$(cat $ficheiro | grep "^$1;" | cut -d';' -f7)
		if [ "$(echo $2 | tr "0-9" " " |  wc -w)" = "0" ]; then
			if [ $2 -gt 0 ]; then
				stringParaMudar="$(cat $ficheiro | grep "^$1;" | cut -d';' -f1-6);"
				$(cat $ficheiro | sed -i -e "s/^$stringParaMudar$valor$/$stringParaMudar$(($valor+$2))/" $ficheiro)
				echo "Foi adicionado $2 minutos ao utilizador $1 com sucesso"
			else
				echo "Não pode adicionar zero!"
			fi
		else
			echo "Não podes adicionar valores não numericos ou valores negativos!"
		fi
       else
	       echo "Utilizador $1 não encontrado!"
       fi
else
	echo "Adicione o nome de utilizador e saldo a adicionar como parâmetros de execução! Ou verifique se existe o ficheiro utilizadores.txt!"
fi
