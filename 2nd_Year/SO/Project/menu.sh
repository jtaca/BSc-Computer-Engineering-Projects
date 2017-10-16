#!/bin/bash

sair=0
while [ $sair -ne 1 ]; do
	PS3='Menu de Administração'
	opcoes=("1. Cria utilizador" "2. Adiciona saldo" "3. Cria viatura" "4. Mostra utilizadores" "5. Mostra estatísticas" "6. Backup" "0. Sair")
	
	printf '%s\n' "${opcoes[@]}"
	read resposta 
	case $resposta in
		"1")
			./cria_utilizador.sh
			;;
		"2")
			printf "Por favor indique o utiizador: "
			read user
			printf "Por favor indique o montante: "
			read montante
			./adiciona_saldo.sh $user $montante
			;;
		"3")
			./cria_viatura.sh 
			;;
		"4")
			echo "Lista de utilizadores:"
			cat utilizadores.txt | sort | uniq
			;;
		"5")
			./stats.sh
			;;
		"6")
			./backup.sh
			;;
		"0")
			sair=1
			break
			;;
		*) echo opção não aceitavel;;
	esac
done
exit 0
