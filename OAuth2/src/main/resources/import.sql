DROP TABLE IF EXISTS users;
CREATE TABLE users(username varchar(50) NOT NULL,password varchar(50) NOT NULL,enabled tinyint(1) NOT NULL,PRIMARY KEY (username))ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES users WRITE;
INSERT INTO users VALUES ('target','target@123',1),('targetint','targetinte',1),('sumit','sumit',1),('sumit123','sumit123',1);
UNLOCK TABLES;



DROP TABLE IF EXISTS authorities;

CREATE TABLE authorities(username varchar(50) NOT NULL,authority varchar(50) NOT NULL,UNIQUE KEY ix_auth_username (username,authority),CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES authorities WRITE;
/*!40000 ALTER TABLE authorities DISABLE KEYS */;
INSERT INTO authorities VALUES ('target','ROLE_USER,ROLE_ADMIN'),('targetint','ROLE_USER');
/*!40000 ALTER TABLE authorities ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token(token_id varchar(256) DEFAULT NULL,token blob,authentication_id varchar(256) NOT NULL,user_name varchar(256) DEFAULT NULL,client_id varchar(256) DEFAULT NULL,authentication blob,refresh_token varchar(256) DEFAULT NULL,PRIMARY KEY (authentication_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details(client_id varchar(256) NOT NULL,resource_ids varchar(256) DEFAULT NULL,client_secret varchar(256) DEFAULT NULL,scope varchar(256) DEFAULT NULL,authorized_grant_types varchar(256) DEFAULT NULL,web_server_redirect_uri varchar(256) DEFAULT NULL,authorities varchar(256) DEFAULT NULL,access_token_validity int(11) DEFAULT NULL,refresh_token_validity int(11) DEFAULT NULL,additional_information varchar(4096) DEFAULT NULL,autoapprove varchar(256) DEFAULT NULL,PRIMARY KEY (client_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES oauth_client_details WRITE;
INSERT INTO oauth_client_details VALUES ('hello-client','client','client','read,write','authorization_code,implicit','','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',120,600,NULL,'true'),('target','target','target','read,write','authorization_code,implicit,password,refresh_token','','ROLE_CLIENT,ROLE_USER',120000,120000,NULL,'true');
UNLOCK TABLES;


DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token(token_id varchar(256) DEFAULT NULL,token blob,authentication blob) ENGINE=InnoDB DEFAULT CHARSET=utf8;


