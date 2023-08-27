-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-Created-users-table
CREATE TABLE `users` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(100) NOT NULL,
  `LAST_NAME` VARCHAR(100) NOT NULL,
  `EMAIL_ID` VARCHAR(200) NOT NULL,
  `PASSWORD` VARCHAR(255) NOT NULL,
  `REGISTRATION_DATE_TIME` DATETIME NOT NULL,
  `ROLE_ID` INT NOT NULL,
  PRIMARY KEY (`ID`));


--changeset Rushikesh Malvadkar:2-Created-role-table
CREATE TABLE `role` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`ID`));

--changeset Rushikesh Malvadkar:3-adding-roles
INSERT INTO `role` (`NAME`) VALUES ('STUDENT');
INSERT INTO `role` (`NAME`) VALUES ('INSTRUCTOR');

--changeset Rushikesh Malvadkar:4-adding-users
INSERT INTO `users`
(`FIRST_NAME`, `LAST_NAME`, `EMAIL_ID`, `PASSWORD`, `REGISTRATION_DATE_TIME`, `ROLE_ID`)
VALUES ('abc', 'test', 'abc123@gmail.com', '$2a$10$VUx2/VjH8pkqiGWVRnmiZ.UF2OmrHmJCcpOBh7o5PEZvHfR660u3e', now(), 1);

INSERT INTO `users`
(`FIRST_NAME`, `LAST_NAME`, `EMAIL_ID`, `PASSWORD`, `REGISTRATION_DATE_TIME`, `ROLE_ID`)
VALUES ('def', 'test', 'def123@gmail.com', '$2a$10$Sjku2xmYK3uOJQDApeFRA.x9hHb2/PUUmiX6PPCMn.Rk/ed4rBCP2', now(), 2);


