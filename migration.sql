CREATE TABLE users (
	id INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
	username VARCHAR(30), 
    password VARCHAR(64), 
    wins INTEGER, 
    total_games INTEGER,
    money INTEGER DEFAULT 100, 
    steroids INTEGER,
    morphine INTEGER,
    epinephrine INTEGER
);
