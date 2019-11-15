#!/bin/bash

cria_utilizador=./cria_utilizador.sh
adiciona_saldo=./adiciona_saldo.sh
cria_viatura=./cria_viatura.sh
stats=./stats.sh
backup=./backup.sh

sair=0
while [ $sair -ne 1 ]; do
	echo 'Menu de Administração'
	opcoes=("1. Cria utilizador" "2. Adiciona saldo" "3. Cria viatura" "4. Mostra utilizadores" "5. Mostra estatísticas" "6. Backup" "0. Sair")
	
	printf '%s\n' "${opcoes[@]}"
	read resposta 
	case $resposta in
		"1")
			if [ -f $cria_utilizador ]; then
				$cria_utilizador
			fi
			;;
		"2")
			if [ -f $adiciona_saldo ]; then
				cicloSaldoUser=0
				cicloSaldoMontante=0
				while [ $cicloSaldoUser = 0 ]; do
					printf "Por favor indique o utilizador: "
					read user
					if [ "$(echo $user | wc -w)" = "1" ]; then
						cicloSaldoUser=1
					else
						echo "Apenas um argumento!"
					fi
				done
				while [ $cicloSaldoMontante = 0 ]; do
					printf "Por favor indique o montante: "
					read montante
					if [ "$(echo $montante | wc -w)" = "1" ]; then
						cicloSaldoMontante=1
					else
						echo "Apenas um argumento!"
					fi
				done
				$adiciona_saldo $user $montante
			fi
			;;
		"3")
			if [ -f $cria_viatura ]; then
				$cria_viatura
			fi
			;;
		"4")
			echo "Lista de utilizadores:"
			cat utilizadores.txt | sort | awk -F "[;]" '{print $1,"-",$4}'
			;;
		"5")
			if [ -f $stats ]; then
				$stats
			fi
			;;
		"6")
			if [ -f $backup ]; then
				$backup
			fi
			;;
		"0")
			sair=1
			;;
		*) echo "Opção não aceitável";;
	esac
done
exit 0
