USE gai0006;
SELECT * FROM game;
SELECT * FROM move;
SELECT * FROM player;
SELECT * FROM results;

SELECT * FROM game HAVING (SELECT COUNT(Player) as players FROM results WHERE Game = Id_g) < 2;
SELECT * FROM results LEFT JOIN player ON Player = Id_p WHERE Game = 37;