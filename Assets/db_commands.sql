INSERT INTO player(name,pass,salt)
VALUES ('','','');	/*New player*/
SELECT * FROM player WHERE Name='' AND Pass='';/*Login*/

INSERT INTO game(Type)
VALUES (''); /*New game - types: Classic*/
SELECT * FROM game WHERE Id=0; /*GetGame*/

INSERT INTO results(Game,Player,Role,Finished)
VALUES (0,1,'Spectator',0); /* Add player to game Role: Player,Spectator; Finished: 0-false,1-true*/

UPDATE results SET Finished=1 WHERE Game=0; /* End game part A*/
UPDATE game SET Finished=NOW() WHERE Id_g = 0; /* End game part B*/

INSERT INTO move(Game,Player,FromX,FromY,ToX,ToY,FigureId)
VALUES (0,1,1,1,2,2,165);	/* Insert Move */

SELECT * FROM move WHERE Game=0 AND Player=1 ORDER BY Time DESC; /* GetLastMoves */


