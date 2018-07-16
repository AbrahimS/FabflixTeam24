 DELIMITER $$
 
 CREATE PROCEDURE  add_movie (IN in_title varchar(100), IN in_year int(11), IN in_director varchar(100), IN in_star varchar(100), IN in_genre varchar(32))

 	leaveBlock: BEGIN
 	DECLARE mtitle varchar(100);
 	DECLARE sName varchar(100);
 	DECLARE gName varchar(32);
 	DECLARE mid varchar(10);
 	DECLARE sid varchar(10);
 	DECLARE gid varchar(10);

	 SELECT title INTO mtitle FROM movies WHERE title = in_title;
	 IF mtitle = in_title THEN 
	 SELECT CONCAT(mtitle, " EXISTS") as ret;
	 LEAVE leaveBlock;
	 ELSEIF in_star LIKE ' %' THEN
	 SELECT "Star must be included or not begin with whitespace" as ret;
	 LEAVE leaveBlock;
	 ELSEIF in_genre LIKE ' %' THEN
	 SELECT "Genre must be included or not begin with whitespace" as ret;
	 LEAVE leaveBlock;
	 ELSE 
	 SELECT CONCAT('tt0', max(cast(substring(id, 3) AS UNSIGNED)) + 1) INTO mid FROM movies;
	 INSERT INTO movies (id, title, year, director) VALUES(mid, in_title, in_year, in_director);
	 SELECT "Movie has been created" as ret;
	 END IF;

	 SELECT name INTO sName FROM stars WHERE name = in_star; 
	 IF sName = in_star THEN 
	 SELECT id INTO sid FROM stars WHERE name = in_star;
	 INSERT INTO stars_in_movies (starId, movieId) VALUES(sid, mid);
	 SELECT "Star exists and has been linked" as ret;
	 ELSE
	 SELECT CONCAT('nm', max(cast(substring(id, 3) AS UNSIGNED)) + 1) INTO sid FROM stars;
	 INSERT INTO stars (id, name) VALUES(sid, in_star);
	 INSERT INTO stars_in_movies (starId, movieId) VALUES(sid, mid);
	 SELECT "Star has been created and linked" as ret;
	 END IF;

	 SELECT name INTO gName FROM genres WHERE name = in_genre;
	 IF gName = in_genre THEN 
	 SELECT id INTO gid FROM genres WHERE name = in_genre;
	 INSERT INTO genres_in_movies (genreId, movieId) VALUES(gid, mid);
	 SELECT "Genre exists and has been linked" as ret;
	 ELSE
	 SELECT max(cast(id AS UNSIGNED)) + 1 INTO gid FROM genres;
	 INSERT INTO genres (id, name) VALUES(gid, in_genre);
	 INSERT INTO genres_in_movies (genreId, movieId) VALUES(gid, mid);
	 SELECT "Genre has been created and linked" as ret;
	 END IF;

	END leaveBlock;
 $$

 DELIMITER ;