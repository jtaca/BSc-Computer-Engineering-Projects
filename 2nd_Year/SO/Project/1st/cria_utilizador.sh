#!/bin/bash

path=$(pwd)
quit=0
#cd /
ficheiro=/etc/passwd
ficheiroUtilizadores=utilizadores.txt
touch $ficheiroUtilizadores
echo "Insira o numero de aluno válido por favor: " 
read numberGiven
if [ $(cat $ficheiro | cut -d':' -f1 | grep "^a$numberGiven") ];
then
    nome=$(cat $ficheiro | grep "^a$numberGiven" | cut -d':' -f5 | cut -d',' -f1)
    #cd $path
	if [ "$(cat $(pwd)/$ficheiroUtilizadores| grep "a$numberGiven" | wc -l)" == "0" ];
    then
        echo "Insira a turma a que pertence por favor: " 
        read turma
        echo "Insira o saldo atual: "
        read saldo
        printf "Utilizador:"
        read user
        printf "Password:"
        read -s password

        echo "$user;$password;$((1000000+$numberGiven));$nome;a$numberGiven@iscte-iul.pt;$turma;$saldo" >> $ficheiroUtilizadores
        echo "Parabéns conseguiu inserir o utilizador $nome"
    else
        echo "O utilizador já estva registado!"
    fi
else
    echo "Não foi inserido um numero válido... :/"
fi
