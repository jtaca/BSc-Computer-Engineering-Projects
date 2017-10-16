#!/bin/bash
touch viaturas.txt
touch utilizadores.txt
backup_files="viaturas.txt utilizadores.txt"
destinoDoBackup="$(pwd)"
echo "destino: $destinoDoBackup ,ficheiros: $backup_files"

nomeDoArquivo=".ecooliul-$(date +%Y)-$(date | cut -d' ' -f2)-$(date | cut -d' ' -f3)-$(date | cut -d' ' -f4 | cut -d':' -f1)-$(date | cut -d' ' -f4 | cut -d':' -f2).tgz"
#nomeDoArquivo=".ecooliul-$(date +%Y)-$(date +%B)-$(date +%d)-$(date | cut -d' ' -f4 | cut -d':' -f1)-$(date | cut -d' ' -f4 | cut -d':' -f2).tgz"
tar czf $destinoDoBackup/$nomeDoArquivo $backup_files

boaResposta=0
while [ $boaResposta -ne 1 ] ; do
	printf "Backup com o nome $nomeDoArquivo, listar conteudos?(s/n): "
	read resposta
	case $resposta in
		"s")
			tar -tvf $nomeDoArquivo
			boaresposta=1
			break 
			;;
		"n")
			boaresposta=1
			break
			;;
		*)
			echo "responda com s ou n para sim ou não respetivamente."
			;;
	esac
done

#para inserir no crontab $ crontab -e
# e inserir no ficheiro 0 0 * * * bash /home/a77812/Proj/backup.sh neste caso o backup é todoe os dias às 24:00
