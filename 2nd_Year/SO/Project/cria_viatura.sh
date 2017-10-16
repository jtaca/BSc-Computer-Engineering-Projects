#!/bin/bash
ficheiro=./viaturas.txt
if [ -f $ficheiro ]; then
	echo ""
else
	touch $ficheiro
fi
echo "Vamos criar uma nova viatura, qual vai ser o seu nome?"
read nome
if [ "$(cat $ficheiro | cut -d';' -f1 | grep "^$nome$" | wc -l)" == "0" ]; then
	echo "Cor:"
	read cor
	echo "Marca:"
	read marca
	echo "Modelo:"
	read modelo
	echo "Tipo:"
	read tipo
	echo "Nº De mudanças:"
	read mudancas
	echo "Matrícula:"
	read matricula
	echo "$nome;$cor;$marca;$modelo;$tipo;$mudancas;$matricula" >> $ficheiro
	echo "A viatura foi inserida correctamente"
else
	echo "O nome para a viatura que inserio já existe"
fi
