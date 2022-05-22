start
cmd /k "docker run -d -p 3309:3306 --name bookshelter-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=bsapi -e MYSQL_USER=bsuser -e MYSQL_PASSWORD=100316 mysql"
pause