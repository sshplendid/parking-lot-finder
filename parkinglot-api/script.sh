docker run --name mysql -p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=admin1! \
-e MYSQL_DATABASE=parkinglot \
-d mysql:5.7 \
--lower_case_table_names=1 \
--character-set-server=utf8 \
--collation-server=utf8_unicode_ci
#-v $(pwd)/.docker/mysql:/var/lib/mysql \
