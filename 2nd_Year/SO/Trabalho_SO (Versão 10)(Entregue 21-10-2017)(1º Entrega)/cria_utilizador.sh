#!/bin/bash
ficheiroPasswd=/etc/passwd
ficheiroUtilizadores=./utilizadores.txt
cicloUser=0
cicloTurma=0
cicloSaldo=0
numeroDeCaracteresMinimo=2
numeroDeLetrasParaTurma=4
touch $ficheiroUtilizadores
echo "Insira o numero de aluno válido por favor: " 
read numberGiven
if [ $(cat $ficheiroPasswd | cut -d':' -f1 | grep "^a$numberGiven$") ];
then
    nome=$(cat $ficheiroPasswd | grep "^a$numberGiven:" | cut -d':' -f5 | cut -d',' -f1)
	if [ "$(cat $ficheiroUtilizadores | cut -d';' -f5 | grep "^a$numberGiven@" | wc -l)" == "0" ];
    then
	while [ $cicloUser = 0 ]; do
        	printf "Utilizador:"
        	read user
		if [ -z "$(cat $ficheiroUtilizadores | cut -d';' -f1 | grep "^$user$")" ] && [ $(echo $user | wc -m) -ge $(($numeroDeCaracteresMinimo+1)) ]; then
			cicloUser=1
		else
			echo "O Username que inserio já está a ser utilizado ou contem menos de $numeroDeCaracteresMinimo  letras, tente novamente."
		fi
	done

        printf "Password:"
        read -s password
	echo ""

	while [ $cicloTurma = 0 ]; do
		echo "Insira a turma a que pertence por favor (até $numeroDeLetrasParaTurma letras): " 
        	read turma
		if [ $(echo $turma | wc -m) -le $(($numeroDeLetrasParaTurma+1)) ]; then
			cicloTurma=1
		else
			echo "A turma excede $numeroDeLetrasParaTurma letras, tente novamente."
		fi
	done

	while [ $cicloSaldo = 0 ]; do
        	echo "Insira o saldo atual: "
        	read saldo
		if [ "$(echo $saldo | tr "0-9" " " | wc -w)" = "0" ]; then
			cicloSaldo=1
		else
			echo "Apenas insira números (positivos) no saldo. Tente novamente."
		fi
	done

	if [ -z $saldo ]; then
		saldo=0
	fi

        echo "$user;$password;$((1000000+$numberGiven));$nome;a$numberGiven@iscte-iul.pt;$turma;$saldo" >> $ficheiroUtilizadores
        echo "Parabéns conseguiu inserir o utilizador $nome"
    else
        echo "O utilizador já estava registado!"
    fi
else
    echo "Não foi inserido um numero válido... :/"
fi
