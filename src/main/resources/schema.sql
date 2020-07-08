CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
INSERT INTO USERS (username, salt, password, firstName, lastName) VALUES ('js', 'Ja7Vr04VG/pVXAPOEwZ+yA==', 'e00G4Nvu8Wk2GN1x32aFyw==', 'John', 'Smith');
INSERT INTO USERS (username, salt, password, firstName, lastName) VALUES ('jd', 'aJwcLfh6P7wSqM2WrnMjvg==', 'FXPdtbu0+ItQKFcEQ0itCA==', 'Jane', 'Doe');
INSERT INTO NOTES (noteTitle, notedescription, userid) VALUES ('Jane First Note', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 2);
INSERT INTO USERS (username, salt, password, firstName, lastName) VALUES ('lm', '2npcs4nJM7D+mMNM3VpGSQ==', 'G0qSm+2sR0dop/nN5mjaOg==', 'Lionel', 'Messy');
INSERT INTO NOTES (noteTitle, notedescription, userid) VALUES ('Lio First Note', '1. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 3);
INSERT INTO NOTES (noteTitle, notedescription, userid) VALUES ('Lio Second Note', '2. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 3);
//INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES ('Lio Second Note', 'its layout.', 'sdsdsdsd', 'sdsdsd123', 3);
//INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES ('Lio Second Note', 'its layout.', 'sdsdsdsd', 'sdsdsd123', 1);