#!/bin/bash
cd /home/a77812/Proj
touch viaturas.txt
touch utilizadores.txt
ficheirosParaBackup="viaturas.txt utilizadores.txt"
echo "destino: $destinoDoBackup ,ficheiros: $backup_files"
nomeDoArquivo=".ecooliul-$(date +%Y)-$(date +%m)-$(date +%d)-$(date +%H)-$(date +%M).tgz"
tar czf ./$nomeDoArquivo $ficheirosParaBackup
echo "Backup com o nome $nomeDoArquivo, com os conteudos: "
tar -tvf $nomeDoArquivo

#para inserir no crontab $ crontab -e e inserir no ficheiro:
# 0 0 * * * bash /home/a77812/Proj/backup.sh neste caso o backup é todoe os dias às 24:00
