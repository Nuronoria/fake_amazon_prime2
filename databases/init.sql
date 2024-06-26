CREATE DATABASE IF NOT EXISTS userlogin;

CREATE USER IF NOT EXISTS 'taha'@'%' IDENTIFIED BY 'password123';

GRANT ALL PRIVILEGES ON userlogin.* TO 'taha'@'%';

FLUSH PRIVILEGES;

USE userlogin;

SOURCE /docker-entrypoint-initdb.d/userlogin_comment.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_genre.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_language.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_movie.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_moviegenre.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_movielanguage.sql;
SOURCE /docker-entrypoint-initdb.d/userlogin_user.sql;
