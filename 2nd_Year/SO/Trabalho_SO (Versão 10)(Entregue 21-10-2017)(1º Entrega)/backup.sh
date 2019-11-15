#!/bin/bash
cd /home/a78178/Trabalho_SO
#cd /home/a77812/Proj
nome_da_pasta=.backup
pasta_de_backup=./$nome_da_pasta

if [ ! -d $pasta_de_backup ]; then
	mkdir $nome_da_pasta
fi

touch ./viaturas.txt
touch ./utilizadores.txt
ficheirosParaBackup="viaturas.txt utilizadores.txt"
echo "destino: $destinoDoBackup ,ficheiros: $backup_files"
nomeDoArquivo=".ecooliul-$(date +%Y)-$(date +%m)-$(date +%d)-$(date +%H)-$(date +%M).tgz"
tar czf $pasta_de_backup/$nomeDoArquivo $ficheirosParaBackup
echo "Backup com o nome $nomeDoArquivo, com os conteudos: "
tar -tvf $pasta_de_backup/$nomeDoArquivo

#para inserir no crontab $ crontab -e e inserir no ficheiro:
# 0 0 * * * bash /home/a77812/Proj/backup.sh neste caso o backup é todoe os dias às 24:00
