-- MySQL Script generated by MySQL Workbench
-- Sun Feb  9 15:48:31 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bacheca_online
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bacheca_online` ;

-- -----------------------------------------------------
-- Schema bacheca_online
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bacheca_online` DEFAULT CHARACTER SET utf8 ;
USE `bacheca_online` ;

-- -----------------------------------------------------
-- Table `bacheca_online`.`utente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`utente` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`utente` (
  `username` VARCHAR(45) NOT NULL,
  `cf` CHAR(16) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `data_nascita` DATE NOT NULL,
  `indirizzo_residenza` VARCHAR(100) NOT NULL,
  `indirizzo_fatturazione` VARCHAR(100) NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`categoria` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`categoria` (
  `nome_categoria` VARCHAR(45) NOT NULL,
  `categoria_superiore` VARCHAR(45) NULL,
  PRIMARY KEY (`nome_categoria`),
  INDEX `fk_categoria_categoria1_idx` (`categoria_superiore` ASC) VISIBLE,
  CONSTRAINT `fk_categoria_categoria1`
    FOREIGN KEY (`categoria_superiore`)
    REFERENCES `bacheca_online`.`categoria` (`nome_categoria`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`annuncio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`annuncio` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`annuncio` (
  `id_annuncio` INT NOT NULL AUTO_INCREMENT,
  `utente` VARCHAR(45) NOT NULL,
  `titolo` VARCHAR(45) NOT NULL,
  `descrizione` VARCHAR(300) NOT NULL,
  `categoria` VARCHAR(45) NULL,
  `data_pubblicazione` DATE NOT NULL,
  `data_vendita` DATE NULL,
  PRIMARY KEY (`id_annuncio`),
  INDEX `fk_annuncio_utente_idx` (`utente` ASC) VISIBLE,
  INDEX `fk_annuncio_categoria1_idx` (`categoria` ASC) VISIBLE,
  INDEX `idx_report_annunci_venduti` (`utente` ASC, `data_vendita` ASC) VISIBLE,
  INDEX `idx_annunci_venduti` (`data_vendita` ASC) VISIBLE,
  CONSTRAINT `fk_annuncio_utente`
    FOREIGN KEY (`utente`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_annuncio_categoria1`
    FOREIGN KEY (`categoria`)
    REFERENCES `bacheca_online`.`categoria` (`nome_categoria`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`messaggio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`messaggio` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`messaggio` (
  `id_messaggio` INT NOT NULL AUTO_INCREMENT,
  `mittente` VARCHAR(45) NOT NULL,
  `destinatario` VARCHAR(45) NOT NULL,
  `annuncio` INT NOT NULL,
  `ts_messaggio` DATETIME NOT NULL,
  `testo` VARCHAR(180) NOT NULL,
  PRIMARY KEY (`id_messaggio`),
  INDEX `fk_messaggio_utente1_idx` (`mittente` ASC) VISIBLE,
  INDEX `fk_messaggio_utente2_idx` (`destinatario` ASC) VISIBLE,
  INDEX `fk_messaggio_annuncio1_idx` (`annuncio` ASC) VISIBLE,
  CONSTRAINT `fk_messaggio_utente1`
    FOREIGN KEY (`mittente`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_messaggio_utente2`
    FOREIGN KEY (`destinatario`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_messaggio_annuncio1`
    FOREIGN KEY (`annuncio`)
    REFERENCES `bacheca_online`.`annuncio` (`id_annuncio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`commento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`commento` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`commento` (
  `id_commento` INT NOT NULL AUTO_INCREMENT,
  `utente` VARCHAR(45) NOT NULL,
  `annuncio` INT NOT NULL,
  `ts_commento` DATETIME NOT NULL,
  `testo` VARCHAR(180) NOT NULL,
  INDEX `fk_commento_utente1_idx` (`utente` ASC) VISIBLE,
  INDEX `fk_commento_annuncio1_idx` (`annuncio` ASC) VISIBLE,
  PRIMARY KEY (`id_commento`),
  CONSTRAINT `fk_commento_utente1`
    FOREIGN KEY (`utente`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_commento_annuncio1`
    FOREIGN KEY (`annuncio`)
    REFERENCES `bacheca_online`.`annuncio` (`id_annuncio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`metodo_di_contatto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`metodo_di_contatto` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`metodo_di_contatto` (
  `MDC` VARCHAR(80) NOT NULL,
  `utente` VARCHAR(45) NOT NULL,
  `tipo` ENUM('email', 'telefono', 'cellulare') NOT NULL,
  `preferenza` INT NOT NULL,
  PRIMARY KEY (`MDC`, `utente`),
  INDEX `fk_email_utente1_idx` (`utente` ASC) VISIBLE,
  CONSTRAINT `fk_email_utente1`
    FOREIGN KEY (`utente`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`attiva_notifica`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`attiva_notifica` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`attiva_notifica` (
  `utente` VARCHAR(45) NOT NULL,
  `annuncio` INT NOT NULL,
  INDEX `fk_attiva_notifica_utente1_idx` (`utente` ASC) VISIBLE,
  INDEX `fk_attiva_notifica_annuncio1_idx` (`annuncio` ASC) VISIBLE,
  PRIMARY KEY (`utente`, `annuncio`),
  CONSTRAINT `fk_attiva_notifica_utente1`
    FOREIGN KEY (`utente`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_attiva_notifica_annuncio1`
    FOREIGN KEY (`annuncio`)
    REFERENCES `bacheca_online`.`annuncio` (`id_annuncio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bacheca_online`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bacheca_online`.`login` ;

CREATE TABLE IF NOT EXISTS `bacheca_online`.`login` (
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `ruolo` ENUM('user', 'admin') NOT NULL,
  PRIMARY KEY (`user`),
  INDEX `fk_login_utente1_idx` (`user` ASC) VISIBLE,
  CONSTRAINT `fk_login_utente1`
    FOREIGN KEY (`user`)
    REFERENCES `bacheca_online`.`utente` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `bacheca_online` ;

-- -----------------------------------------------------
-- procedure creaAnnuncio
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`creaAnnuncio`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `creaAnnuncio` (in var_utente VARCHAR(45), in var_titolo VARCHAR(45), in var_descrizione VARCHAR(300), in var_categoria VARCHAR(45))
BEGIN
	INSERT INTO annuncio(utente, titolo, descrizione, categoria, data_pubblicazione) VALUES (var_utente, var_titolo, var_descrizione, var_categoria, curdate());
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure contrassegnaAnnuncioVenduto
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`contrassegnaAnnuncioVenduto`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `contrassegnaAnnuncioVenduto` (in var_annuncio INT, in var_data_vendita DATE)
BEGIN
	DECLARE var_check_vendita DATE;
    
    DECLARE exit handler for sqlexception
    begin
		rollback;
        resignal;
	end;
    
    set transaction isolation level repeatable read;
    
    start transaction;
    SELECT data_vendita FROM annuncio WHERE id_annuncio = var_annuncio INTO var_check_vendita;
    if var_check_vendita IS NOT NULL THEN
		signal sqlstate '45000' set message_text = "L'annuncio risulta già contrassegnato come venduto";
	end if;
    
    UPDATE annuncio SET data_vendita = var_data_vendita WHERE id_annuncio = var_annuncio AND data_vendita IS NULL;
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure listaAnnunciPubblicatiUtente
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`listaAnnunciPubblicatiUtente`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `listaAnnunciPubblicatiUtente` (in var_utente VARCHAR(45))
BEGIN
    SELECT * FROM annuncio WHERE utente = var_utente;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure attivaNotifichePerAnnuncio
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`attivaNotifichePerAnnuncio`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `attivaNotifichePerAnnuncio` (in var_utente VARCHAR(45), in var_annuncio int1)
BEGIN
	INSERT INTO attiva_notifica (utente, annuncio) VALUES (var_utente, var_annuncio);    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure listaAnnunciNotificheAttive
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`listaAnnunciNotificheAttive`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `listaAnnunciNotificheAttive` (in var_utente VARCHAR(45))
BEGIN
	SELECT A.id_annuncio, A.utente, A.titolo, A.descrizione, A.categoria, A.data_pubblicazione
    FROM attiva_notifica as AN
    JOIN annuncio as A ON AN.annuncio = A.id_annuncio
    WHERE AN.utente = var_utente
    ORDER BY A.data_pubblicazione DESC;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure scriviMessaggio
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`scriviMessaggio`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `scriviMessaggio` (in var_mittente VARCHAR(45), in var_destinatario VARCHAR(45), in var_annuncio INT, in var_testo VARCHAR(180))
BEGIN
    INSERT INTO messaggio (mittente, destinatario, annuncio, ts_messaggio, testo) VALUES (var_mittente, var_destinatario, var_annuncio, now(), var_testo);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure pubblicaCommento
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`pubblicaCommento`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `pubblicaCommento` (in var_utente VARCHAR(45), in var_annuncio INT, in var_testo VARCHAR(180))
BEGIN
    INSERT INTO commento (utente, annuncio, ts_commento, testo) VALUES (var_utente, var_annuncio, now(), var_testo);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure reportPercentualeAnnunciVendutiUtente
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`reportPercentualeAnnunciVendutiUtente`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `reportPercentualeAnnunciVendutiUtente` ()
BEGIN
    
	SELECT 
    A.utente,
    COUNT(*) AS totale_annunci,
    COUNT(CASE WHEN A.data_vendita IS NOT NULL THEN 1 END) AS annunci_venduti,
    ROUND((COUNT(CASE WHEN a.data_vendita IS NOT NULL THEN 1 END) * 100.0) / COUNT(*), 2) AS percentuale_annunci_venduti
	FROM annuncio AS A
	GROUP BY A.utente
	ORDER BY percentuale_annunci_venduti DESC;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure listaAnnunciAttivi
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`listaAnnunciAttivi`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `listaAnnunciAttivi` ()
BEGIN
	
	SELECT id_annuncio, utente, titolo, descrizione, categoria, data_pubblicazione 
    FROM annuncio
    WHERE data_vendita IS NULL
    ORDER BY data_pubblicazione DESC;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure inserisciMetodiDiContatto
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`inserisciMetodiDiContatto`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `inserisciMetodiDiContatto` (in var_username VARCHAR(45), in var_metodi_di_contatto TEXT, out var_num_contatti_inseriti INT)
BEGIN
	
	DECLARE var_temp TEXT;
    DECLARE var_i INT;
    DECLARE var_tipo VARCHAR(50);
    DECLARE var_contatto VARCHAR(80);
    DECLARE var_preferenza INT;
    DECLARE var_num_contatti_inseriti INT;
    SET var_num_contatti_inseriti = 0;
	-- separo metodi di contatto col ; (for loop)
    -- per ogni metodo di contatto estraggo le parti che mi interessano
    set var_i = 1;
    
    insert_metodi_di_contatto: loop
        set var_temp = tokenizzaMetodiDiContatto(var_metodi_di_contatto, var_i);
        if var_temp = ''
        then
            leave insert_metodi_di_contatto;
        end if;
        
        -- Estrarre i valori separati da ':'
		SELECT 
        SUBSTRING_INDEX(var_temp, ':', 1),
        SUBSTRING_INDEX(SUBSTRING_INDEX(var_temp, ':', 2), ':', -1),
        CAST(SUBSTRING_INDEX(var_metodo, ':', -1) AS UNSIGNED)
		INTO var_tipo, var_contatto, var_preferenza;
        
        INSERT INTO metodo_di_contatto(MDC, utente, tipo, preferenza) VALUES (var_contatto, var_username, var_tipo, var_preferenza);
        set var_num_contatti_inseriti = var_num_contatti_inseriti+1;
        set var_i = var_i+1;
    end loop;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure registraUtente
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`registraUtente`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `registraUtente` (in var_username varchar(45), in var_password varchar(32), in var_cf char(16), in var_nome varchar(45), in var_cognome varchar(45), in var_data_nascita date, in var_indirizzo_residenza varchar(100), in var_indirizzo_fatturazione varchar(100), in var_metodi_di_contatto TEXT)
BEGIN
	
    DECLARE var_num_contatti_inseriti INT;
	DECLARE exit handler for sqlexception
    
    begin
		rollback;
        resignal;
	end;
    
    set transaction isolation level read uncommitted;
    start transaction;
    
    INSERT INTO utente (username, cf, nome, cognome, data_nascita, indirizzo_residenza, indirizzo_fatturazione) VALUES (var_username, var_cf, var_nome, var_cognome, var_data_nascita, var_indirizzo_residenza, var_indirizzo_fatturazione);
    INSERT INTO login (user, password, impiegato) VALUES (var_username, MD5(var_password), 'user');
    call inserisciMetodiDiContatto(var_username, var_metodi_di_contatto, var_num_contatti_inseriti);
    if var_num_contatti_inseriti <= 0 then
        signal sqlstate '45000' set message_text = "L'utente deve fornire almeno un recapito";
    end if;
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`login`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `login` (in var_user VARCHAR(80), in var_password VARCHAR(80), out var_ruolo ENUM('user', 'admin')) 
BEGIN
	DECLARE var_check_username VARCHAR(80); 

	SELECT user, ruolo INTO var_check_username, var_ruolo 
	FROM Login
	WHERE user = var_user and password = MD5(var_password);

	IF var_check_username IS NULL THEN 
	SIGNAL SQLSTATE '45000' set message_text = 'Credenziali errate'; 
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungiCategoria
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`aggiungiCategoria`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `aggiungiCategoria` (in var_nomecat VARCHAR(45), in var_cat_sup VARCHAR(45))
BEGIN
	INSERT INTO categoria (nome_categoria, categoria_superiore) VALUES (var_nomecat, var_cat_sup);
END$$

DELIMITER ;
USE `bacheca_online`;

-- -----------------------------------------------------
-- procedure getCategories
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`getCategories`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `getCategories` ()
BEGIN
	SELECT nome_categoria, categoria_superiore
    FROM categoria;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure disattivaNotifichePerAnnuncio
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`disattivaNotifichePerAnnuncio`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `disattivaNotifichePerAnnuncio` (in var_utente VARCHAR(45), in var_annuncio int)
BEGIN
	DELETE FROM attiva_notifica
    WHERE utente=var_utente AND annuncio = var_annuncio;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure checkNotificheOn
-- -----------------------------------------------------

USE `bacheca_online`;
DROP procedure IF EXISTS `bacheca_online`.`checkNotificheOn`;

DELIMITER $$
USE `bacheca_online`$$
CREATE PROCEDURE `checkNotificheOn` (in var_utente VARCHAR(45), in var_annuncio int, out var_attiva BOOLEAN)
BEGIN
	DECLARE var_res int;
    
    SELECT COUNT(*) INTO var_res
    FROM attiva_notifica 
    WHERE utente=var_utente AND annuncio=var_annuncio;
    
    IF var_res > 0 THEN
		SET var_attiva = TRUE;
	else
		SET var_attiva = FALSE;
	end if;
    
END$$

DELIMITER ;

USE `bacheca_online`;

DELIMITER $$

USE `bacheca_online`$$
DROP TRIGGER IF EXISTS `bacheca_online`.`annuncio_check_data_vendita_null` $$
USE `bacheca_online`$$
CREATE TRIGGER `bacheca_online`.`annuncio_check_data_vendita_null` BEFORE INSERT ON `annuncio` FOR EACH ROW
BEGIN
	IF New.data_vendita <> null THEN
		SIGNAL SQLSTATE '45000' SET message_text = "La data di vendita non può essere impostata al momento dell'inserimento";
	END IF;
END$$


USE `bacheca_online`$$
DROP TRIGGER IF EXISTS `bacheca_online`.`annuncio_check_vendita_dopo_pubblicazione` $$
USE `bacheca_online`$$
CREATE TRIGGER `bacheca_online`.`annuncio_check_vendita_dopo_pubblicazione` BEFORE UPDATE ON `annuncio` FOR EACH ROW
BEGIN
	IF New.data_vendita < Old.data_pubblicazione THEN 
		SIGNAL SQLSTATE '45000' SET message_text = "La data di vendita deve corrispondere o essere successiva alla data di pubblicazione";
	END IF;
END$$


USE `bacheca_online`$$
DROP TRIGGER IF EXISTS `bacheca_online`.`messaggio_check_sender_different_from_receiver` $$
USE `bacheca_online`$$
CREATE TRIGGER `bacheca_online`.`messaggio_check_sender_different_from_receiver` BEFORE INSERT ON `messaggio` FOR EACH ROW
BEGIN
	IF New.mittente = New.destinatario THEN
		SIGNAL SQLSTATE '45000' SET message_text = "Il mittente del messaggio non può coincidere con il destinatario";
	END IF;
END$$


DELIMITER ;
SET SQL_MODE = '';
DROP USER IF EXISTS ba_login;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'ba_login' IDENTIFIED BY 'ba_login';

GRANT EXECUTE ON procedure `bacheca_online`.`login` TO 'ba_login';
SET SQL_MODE = '';
DROP USER IF EXISTS ba_user;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'ba_user' IDENTIFIED BY 'ba_user';

GRANT EXECUTE ON procedure `bacheca_online`.`attivaNotifichePerAnnuncio` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`contrassegnaAnnuncioVenduto` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`creaAnnuncio` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`listaAnnunciAttivi` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`listaAnnunciNotificheAttive` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`listaAnnunciPubblicatiUtente` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`scriviMessaggio` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`pubblicaCommento` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`getCategories` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`disattivaNotifichePerAnnuncio` TO 'ba_user';
GRANT EXECUTE ON procedure `bacheca_online`.`checkNotificheOn` TO 'ba_user';

SET SQL_MODE = '';
DROP USER IF EXISTS ba_admin;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'ba_admin' IDENTIFIED BY 'ba_admin';

GRANT EXECUTE ON procedure `bacheca_online`.`reportPercentualeAnnunciVendutiUtente` TO 'ba_admin';
GRANT EXECUTE ON procedure `bacheca_online`.`registraUtente` TO 'ba_admin';
GRANT EXECUTE ON procedure `bacheca_online`.`inserisciMetodiDiContatto` TO 'ba_admin';
GRANT EXECUTE ON procedure `bacheca_online`.`getCategories` TO 'ba_admin';

START TRANSACTION;
USE bacheca_online;

INSERT INTO utente (username, cf, nome, cognome, data_nascita, indirizzo_residenza, indirizzo_fatturazione) VALUES
('admin', 'ADMINCF123456789', 'Super', 'Admin', '1980-01-01', 'Via degli Amministratori, 1, Roma', NULL),
('mrossi', 'RSSMRA85M01H501Z', 'Marco', 'Rossi', '1985-06-15', 'Via Roma 10, Milano', 'Via Milano 5, Torino'),
('lbianchi', 'BNCLRA90F20H501K', 'Laura', 'Bianchi', '1990-11-20', 'Corso Italia 25, Firenze', NULL),
('gverdi', 'VRDGPP75L11F205N', 'Giuseppe', 'Verdi', '1975-12-05', 'Via Garibaldi 50, Napoli', 'Via Roma 12, Napoli'),
('aseri', 'SRILUC92A01H501C', 'Andrea', 'Seri', '1992-01-10', 'Viale Europa 120, Roma', NULL),
('mpagliani', 'PGLMAR88M15F205B', 'Martina', 'Pagliani', '1988-03-22', 'Via Dante 42, Bologna', 'Via Verdi 18, Bologna'),
('frusso', 'RSSFNC91T30L219X', 'Francesca', 'Russo', '1991-08-30', 'Piazza della Repubblica 5, Torino', NULL),
('dcapelli', 'CPLDAV79R22F205P', 'Davide', 'Capelli', '1979-04-12', 'Via Venezia 34, Genova', 'Corso Buenos Aires 77, Genova'),
('etagliabue', 'TGLENE95B15H501A', 'Elena', 'Tagliabue', '1995-02-15', 'Via Mazzini 88, Verona', NULL);

INSERT INTO categoria (nome_categoria, categoria_superiore) VALUES
('Elettronica', NULL),
  ('Computer', 'Elettronica'),
    ('Laptop', 'Computer'),
    ('Desktop', 'Computer'),
    ('Accessori PC', 'Computer'),
  ('Smartphone', 'Elettronica'),
    ('Android', 'Smartphone'),
    ('iPhone', 'Smartphone'),
    ('Accessori Smartphone', 'Smartphone'),
('Elettrodomestici', NULL),
  ('Cucina', 'Elettrodomestici'),
    ('Frigoriferi', 'Cucina'),
    ('Forni', 'Cucina'),
  ('Pulizia', 'Elettrodomestici'),
    ('Aspirapolveri', 'Pulizia'),
    ('Ferri da stiro', 'Pulizia'),
('Casa', NULL),
  ('Arredamento', 'Casa'),
    ('Mobilio', 'Arredamento'),
    ('Divani', 'Arredamento'),
('Sport', NULL),
  ('Outdoor', 'Sport'),
    ('Trekking', 'Outdoor'),
    ('Ciclismo', 'Outdoor'),
('Indoor', 'Sport'),
    ('Piscina', 'Indoor'),
    ('Pallavolo', 'Indoor'),
('Abbigliamento', NULL),
  ('Uomo', 'Abbigliamento'),
    ('Maglie', 'Uomo'),
    ('Pantaloni', 'Uomo'),
  ('Donna', 'Abbigliamento'),
    ('Vestiti', 'Donna'),
    ('Scarpe', 'Donna');

INSERT INTO annuncio (utente, titolo, descrizione, categoria, data_pubblicazione, data_vendita) VALUES
-- Annunci ancora disponibili (data_vendita NULL)
('mrossi', 'Laptop gaming RTX', 'Laptop con scheda grafica RTX 3070, 16GB RAM, SSD 1TB, perfetto per gaming.', 'Laptop', '2024-02-01', NULL),
('lbianchi', 'Smartphone Android 5G', 'Smartphone Android di ultima generazione, 256GB di memoria, dual SIM.', 'Android', '2024-01-15', NULL),
('gverdi', 'Frigorifero combinato', 'Frigorifero con congelatore, classe energetica A++, ottime condizioni.', 'Frigoriferi', '2024-01-20', NULL),
('aseri', 'Bici da corsa carbonio', 'Bicicletta professionale in carbonio, perfetta per gare e allenamenti.', 'Ciclismo', '2024-01-25', NULL),
('mpagliani', 'Divano letto 3 posti', 'Divano letto in tessuto, colore grigio, ottime condizioni.', 'Divani', '2024-02-02', NULL),
('frusso', 'Scarpe running donna', 'Scarpe da corsa nuove, misura 39, colore blu e rosa.', 'Scarpe', '2024-01-30', NULL),
('dcapelli', 'Pallone da pallavolo', 'Pallone ufficiale per tornei indoor, usato una sola volta.', 'Pallavolo', '2024-02-03', NULL),

-- Annunci già venduti
('etagliabue', 'MacBook Air M2', 'MacBook Air M2, 16GB RAM, 512GB SSD, colore grigio siderale.', 'Laptop', '2024-01-05', '2024-01-15'),
('mrossi', 'Aspirapolvere Dyson', 'Aspirapolvere senza fili Dyson V11, ottime condizioni.', 'Aspirapolveri', '2023-12-20', '2024-01-02'),
('lbianchi', 'Forno a microonde', 'Forno a microonde 800W, con grill e funzione defrost.', 'Forni', '2023-11-10', '2023-12-01'),
('gverdi', 'Maglione uomo lana', 'Maglione di lana merino, taglia L, colore blu scuro.', 'Maglie', '2023-12-15', '2024-01-05'),
('aseri', 'Set accessori PC', 'Tastiera meccanica RGB, mouse gaming, tappetino XXL.', 'Accessori PC', '2024-01-08', '2024-01-18'),
('mpagliani', 'Piscina gonfiabile', 'Piscina gonfiabile per bambini, diametro 3m, con pompa inclusa.', 'Piscina', '2023-12-22', '2024-01-07'),
('frusso', 'Zaino trekking 50L', 'Zaino da trekking impermeabile, capacità 50 litri.', 'Trekking', '2024-01-02', '2024-01-12'),
('dcapelli', 'Smartwatch fitness', 'Smartwatch con cardiofrequenzimetro, GPS, resistenza all’acqua.', 'Accessori Smartphone', '2023-12-18', '2024-01-03');

INSERT INTO login (user, password, ruolo) VALUES
('admin', MD5('admin'), 'admin'),         -- password in chiaro: admin
('mrossi', MD5('password123'), 'user'),   -- password in chiaro: password123
('lbianchi', MD5('securePass'), 'user'),  -- password in chiaro: securePass
('gverdi', MD5('giuseppe75'), 'user'),     -- password in chiaro: giuseppe75
('aseri', MD5('andrea92!'), 'user'),       -- password in chiaro: andrea92!
('mpagliani', MD5('martina88'), 'user'),  -- password in chiaro: martina88
('frusso', MD5('francesca91'), 'user'),    -- password in chiaro: francesca91
('dcapelli', MD5('davidex79'), 'user'),    -- password in chiaro: davidx79
('etagliabue', MD5('elena95top'), 'user'); -- password in chiaro: elena95top

INSERT INTO metodo_di_contatto (MDC, utente, tipo, preferenza) VALUES
('marco.rossi@email.com', 'mrossi', 'email', 1),
('0298765432', 'mrossi', 'telefono', 0),
('3391234567', 'mrossi', 'cellulare', 0),

('laura.bianchi@email.com', 'lbianchi', 'email', 0),
('0256789012', 'lbianchi', 'telefono', 1),
('3487654321', 'lbianchi', 'cellulare', 0),

('giuseppe.verdi@email.com', 'gverdi', 'email', 0),
('0212345678', 'gverdi', 'telefono', 0),
('3339876543', 'gverdi', 'cellulare', 1),

('andrea.seri@email.com', 'aseri', 'email', 1),
('0245678901', 'aseri', 'telefono', 0),

('martina.pagliani@email.com', 'mpagliani', 'email', 0),
('0398765432', 'mpagliani', 'telefono', 0),
('3401122334', 'mpagliani', 'cellulare', 1),

('francesca.russo@email.com', 'frusso', 'email', 1),
('0276543210', 'frusso', 'telefono', 0),

('davide.capelli@email.com', 'dcapelli', 'email', 0),
('0254321098', 'dcapelli', 'telefono', 1),
('3332233445', 'dcapelli', 'cellulare', 0),

('elena.tagliabue@email.com', 'etagliabue', 'email', 0),
('0223456789', 'etagliabue', 'telefono', 0),
('3499988776', 'etagliabue', 'cellulare', 1);

INSERT INTO attiva_notifica (utente, annuncio) VALUES
('lbianchi', 1),  -- Laura Bianchi segue l'annuncio di Marco Rossi
('gverdi', 1),    -- Giuseppe Verdi segue l'annuncio di Marco Rossi
('aseri', 1),     -- Andrea Seri segue l'annuncio di Marco Rossi

('mrossi', 2),    -- Marco Rossi segue l'annuncio di Laura Bianchi
('gverdi', 2),    -- Giuseppe Verdi segue l'annuncio di Laura Bianchi
('mpagliani', 2), -- Martina Pagliani segue l'annuncio di Laura Bianchi

('mrossi', 3),    -- Marco Rossi segue l'annuncio di Giuseppe Verdi
('lbianchi', 3),  -- Laura Bianchi segue l'annuncio di Giuseppe Verdi
('aseri', 3),     -- Andrea Seri segue l'annuncio di Giuseppe Verdi

('mrossi', 4),    -- Marco Rossi segue l'annuncio di Andrea Seri
('lbianchi', 4),  -- Laura Bianchi segue l'annuncio di Andrea Seri
('mpagliani', 4), -- Martina Pagliani segue l'annuncio di Andrea Seri

('mrossi', 5),    -- Marco Rossi segue l'annuncio di Martina Pagliani
('lbianchi', 5);  -- Laura Bianchi segue l'annuncio di Martina Pagliani

INSERT INTO messaggio (mittente, destinatario, annuncio, ts_messaggio, testo) VALUES
-- Messaggi per annunci ancora disponibili
('etagliabue', 'mrossi', 1, '2024-02-02 10:15:30', 'Ciao, il laptop è ancora disponibile? È possibile avere più foto?'),
('mrossi', 'etagliabue', 1, '2024-02-02 11:00:45', 'Sì, è ancora disponibile! Ti mando le foto in privato.'),
('dcapelli', 'lbianchi', 2, '2024-01-16 15:22:10', 'Ciao, sarei interessato allo smartphone. Ha segni di usura?'),
('lbianchi', 'dcapelli', 2, '2024-01-16 16:05:00', 'No, è in ottime condizioni! Posso fare uno sconto se lo prendi subito.'),
('mpagliani', 'gverdi', 3, '2024-01-21 09:30:00', 'Il frigorifero è ancora disponibile? Fai spedizioni?'),
('gverdi', 'mpagliani', 3, '2024-01-21 10:10:15', 'Sì, posso organizzare una spedizione a tue spese.'),
('aseri', 'dcapelli', 4, '2024-01-26 14:50:00', 'Ciao, la bici è perfetta per lunghe distanze? Sono molto interessato.'),
('dcapelli', 'aseri', 4, '2024-01-26 15:30:30', 'Sì, è perfetta per allenamenti su strada e gare!'),

-- Messaggi per annunci già venduti
('frusso', 'etagliabue', 8, '2024-01-06 12:40:00', 'Ciao, il MacBook è ancora disponibile? Sono molto interessata!'),
('etagliabue', 'frusso', 8, '2024-01-06 13:10:20', 'Ciao, sì! Vuoi vederlo dal vivo prima di acquistare?'),
('gverdi', 'mrossi', 9, '2023-12-21 16:15:00', 'Buongiorno, l\'aspirapolvere è nuovo o usato?'),
('mrossi', 'gverdi', 9, '2023-12-21 17:00:45', 'È usato, ma in perfette condizioni! Ti interessa?'),
('aseri', 'mpagliani', 13, '2023-12-23 11:20:00', 'Ciao! La piscina è adatta anche per adulti?'),
('mpagliani', 'aseri', 13, '2023-12-23 12:05:00', 'Sì, ma è pensata più per bambini. Ti interessa lo stesso?');

INSERT INTO COMMENTO (utente, annuncio, ts_commento, testo) VALUES
-- Commenti su annunci ancora disponibili
('etagliabue', 1, '2024-02-02 10:20:30', 'Sembra un ottimo laptop! Hai ancora la scatola originale?'),
('dcapelli', 2, '2024-01-16 15:45:00', 'Lo schermo ha graffi o segni di usura?'),
('mpagliani', 3, '2024-01-21 09:45:00', 'È possibile vedere il frigorifero prima di acquistarlo?'),
('aseri', 4, '2024-01-26 15:00:00', 'Il peso della bici è inferiore ai 9 kg?'),
('frusso', 5, '2024-02-02 14:10:00', 'Quali sono le dimensioni esatte del divano?'),
('dcapelli', 6, '2024-01-30 18:30:00', 'Le scarpe sono nuove o usate?'),
('lbianchi', 7, '2024-02-03 09:50:00', 'Il pallone è adatto per giocare su parquet?'),

-- Commenti su annunci già venduti
('frusso', 8, '2024-01-06 12:50:00', 'Fantastico MacBook! Peccato sia già stato venduto.'),
('gverdi', 9, '2023-12-21 16:20:00', 'Dyson è un’ottima marca! Il prezzo era molto conveniente.'),
('aseri', 13, '2023-12-23 11:30:00', 'Ottima piscina, perfetta per l’estate!'),
('mrossi', 10, '2023-11-15 10:20:00', 'Mi sarebbe servito proprio un forno come questo! :('),
('dcapelli', 12, '2024-01-08 09:15:00', 'Set di accessori molto utile per il gaming!'),
('mpagliani', 14, '2024-01-02 17:40:00', 'Lo zaino era davvero un affare!'),
('etagliabue', 15, '2023-12-19 14:55:00', 'Adoro gli smartwatch, peccato non averlo visto prima!');

COMMIT;

DROP FUNCTION IF EXISTS `tokenizzaMetodiDiContatto`;

DELIMITER $$

CREATE FUNCTION `tokenizzaMetodiDiContatto` (var_lista_metodi TEXT, var_posizione INT)
RETURNS TEXT
DETERMINISTIC
BEGIN
    return substring_index(substring_index(var_lista_metodi, ';', var_posizione), ';', -1);
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SET GLOBAL event_scheduler = on;

DROP EVENT IF EXISTS `bacheca_online`.`clean_annunci_10_anni`;

DELIMITER $$

CREATE EVENT IF NOT EXISTS `bacheca_online`.`clean_annunci_10_anni`
ON SCHEDULE
    EVERY 3 MONTH
ON COMPLETION PRESERVE
DO BEGIN
    DELETE FROM `annuncio` WHERE DATEDIFF(CURDATE(),'data_pubblicazione')>=3650;
END$$

DELIMITER ;

SET GLOBAL event_scheduler = on;

DROP EVENT IF EXISTS `bacheca_online`.`clean_interazioni_5_anni`;

DELIMITER $$

CREATE EVENT IF NOT EXISTS `bacheca_online`.`clean_interazioni_5_anni`
ON SCHEDULE
    EVERY 3 MONTH
ON COMPLETION PRESERVE
DO BEGIN
    DELETE FROM `messaggio` WHERE DATEDIFF(NOW(),`ts_messaggio`)>=1825;
    DELETE FROM `commento` WHERE DATEDIFF(NOW(),`ts_commento`)>=1825;
END$$

DELIMITER ;

