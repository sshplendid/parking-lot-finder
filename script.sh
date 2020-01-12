docker run --name mysql -p 3306:3306 -v $(pwd)/.docker/mysql:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=admin1! \
-e MYSQL_DATABASE=parkinglot \
-d mysql:5.7 \
--lower_case_table_names=1
