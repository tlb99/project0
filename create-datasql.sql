CREATE TYPE roles AS ENUM('Customer','Employee','Admin');

CREATE TABLE IF NOT EXISTS users(
	id serial PRIMARY KEY NOT NULL,
	username varchar,
	pwd varchar,
	user_role roles
);

CREATE TABLE IF NOT EXISTS accounts(
	id serial PRIMARY KEY NOT NULL,
	balance numeric DEFAULT 0.00,
	acc_owner int REFERENCES users (id),
	active boolean
);

INSERT INTO users(username,pwd,user_role) VALUES('customer', 'pwd', 'Customer');
INSERT INTO users(username,pwd,user_role) VALUES('employee', 'pwd', 'Employee');
INSERT INTO users(username,pwd,user_role) VALUES('admin', 'pwd', 'Admin');

INSERT INTO accounts(balance, acc_owner, active) VALUES(0.0, 1, FALSE);
INSERT INTO accounts(balance, acc_owner, active) VALUES(4.20, 1, TRUE);