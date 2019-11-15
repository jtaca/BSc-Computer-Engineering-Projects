#!/bin/bash
ficheiro=./viaturas.txt
touch $ficheiro
cicloCor=0
digitoParaMudanca=2
cicloMudanca=0
numeroDeCaracteresMinimo=2
tiposDeVeiculos=("manual" "electrica")
cicloTipo=0
echo "Vamos criar uma nova viatura, qual vai ser o seu nome?"
read nome
if [ "$(cat $ficheiro | cut -d';' -f1 | grep "^$nome$" | wc -l)" == "0" ] && [ $(echo $nome | wc -m) -ge $(($numeroDeCaracteresMinimo+1)) ]; then

	while [ $cicloCor = 0 ]; do
		echo "Cor:"
		read cor
		if [ $(echo $cor | wc -m) -ge $(($numeroDeCaracteresMinimo+1)) ] && [ $(echo $cor | tr "a-z" " " | tr "A-Z" " " | wc -w) -eq 0 ]; then
			cicloCor=1
		else
			echo "A cor tem de conter mais do que $numeroDeCaracteresMinimo caracteres, e apenas letras"
		fi
	done

	echo "Marca:"
	read marca
	echo "Modelo:"
	read modelo

	while [ $cicloTipo = 0 ]; do
		echo "Tipo:"
		read tipo
		index=0
		for (( i=0;  i<${#tiposDeVeiculos[*]}; i++ )); do
			if [ "$tipo" = "${tiposDeVeiculos[i]}" ]; then
				cicloTipo=1
			fi
		done
		if [ $cicloTipo = 0 ]; then
			echo "Os unicos tipos possiveis de inserir são: ${tiposDeVeiculos[@]}"
		fi
	done

	while [ $cicloMudanca = 0 ]; do
        	echo "Nº De mudanças: (numero com $digitoParaMudanca digito): " 
        	read mudancas
		if [ "$(echo $mudancas | wc -m)" = "$(($digitoParaMudanca+1))" ] && [ "$(echo $mudancas | tr "0-9" " " | wc -w)" = "0" ]; then
            		cicloMudanca=1
        	else
            		echo "O numero de mudanças deve ser um valor com $digitoParaMudanca digito e composto por um numero, tente novamente."
        	fi
    	done

	echo "Matrícula:"
	read matricula

	echo "$nome;$cor;$marca;$modelo;$tipo;$mudancas;$matricula" >> $ficheiro
	echo "A viatura $nome foi inserida correctamente"
else
	echo "O nome para a viatura que inserio já existe ou contem menos de $numeroDeCaracteresMinimo letras"
fi
