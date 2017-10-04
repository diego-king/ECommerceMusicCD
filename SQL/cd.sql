/** cdid:     unique identifier of CD (like ISBN for books)
* title:    title of CD
* price:    unit price WHEN ordered
* label:    label (like MOTOWN, SONY ...)
* artist:   composer or performer or band or orchestra or selection
* category: as specified
*/
DROP TABLE IF EXISTS CD;
CREATE TABLE CD (
 cdid      VARCHAR(20) NOT NULL,
 title     VARCHAR(60) NOT NULL,
 price     INT UNSIGNED NOT NULL,
 category  ENUM('COUNTRY','POP','ROCK') NOT NULL,
 PRIMARY KEY(cdid)
) ;

#
# Dumping data for table 'CD'
#

INSERT INTO CD (cdid, title, price, category) VALUES ('cd001', 'Johnny Cash Greatest Hits', 1599, 'COUNTRY');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd002','Willy Nelson Greatest', 1599, 'COUNTRY');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd003','Patsy Cline Im So Lonely' ,1599,'COUNTRY');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd004', 'Tragically Hip Fully Completely', 2000, 'ROCK');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd005', 'Beatles White Album', 2000, 'ROCK');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd006', 'Rolling Stones Forty Licks', 2000, 'ROCK');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd007', 'Madonna Greatest Hits', 1799, 'POP');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd008', 'Alannis Morissette Jagged Little Pill', 1799, 'POP');
INSERT INTO CD (cdid, title, price, category) VALUES ('cd009', 'Video Killed the Radio Star', 1799, 'POP');
#
#



/* Adress
 * id:	address id
 * 
 */
DROP TABLE IF EXISTS Address;
CREATE TABLE Address (
 id 	     INT UNSIGNED NOT NULL AUTO_INCREMENT,
 street    VARCHAR(100) NOT NULL,
 province  VARCHAR(20)  NOT NULL,
 country   VARCHAR(20)  NOT NULL,
 zip       VARCHAR(20)  NOT NULL,
 phone     VARCHAR(20),
 PRIMARY KEY(id)
) ;

#
# Dumping data for table 'address'
#

INSERT INTO Address (id, street, province, country, zip, phone) VALUES (1, '123 King adward', 'ON', 'Canada', 'K1E 6T5' ,'613-123-4567');
INSERT INTO Address (id, street, province, country, zip, phone) VALUES (2, '34 Rue St-Dominiqu', 'QC', 'Canada', 'K2E 6K5' ,'514-123-8569');
INSERT INTO Address (id, street, province, country, zip, phone) VALUES (3, '99 Main ave.', 'ON', 'Canada', 'K6E 9T5' ,'613-123-9568');
#
#



/* purchase order
 * lname:     	last name
 * fname:    	first name
 * id:	     	purchase order id
 * status:	status of purchase
 */
DROP TABLE IF EXISTS PO;
CREATE TABLE PO (
 id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
 lname     VARCHAR(20) NOT NULL,
 fname     VARCHAR(20) NOT NULL,
 status    ENUM('ORDERED','PROCESSED','DENIED') NOT NULL,
 address   INT UNSIGNED NOT NULL,
 PRIMARY KEY(id),
 INDEX (address),
 FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE
) ;

#
# Dumping data for table 'PO'
#
INSERT INTO PO (id, lname, fname, status, address) VALUES (1, 'Liam', 'Peyton', 'PROCESSED', '1');
INSERT INTO PO (id, lname, fname, status, address) VALUES (2, 'Peter', 'White', 'DENIED', '2');
INSERT INTO PO (id, lname, fname, status, address) VALUES (3, 'Andy',  'ADLER', 'ORDERED', '3');
#
#



/* Items on order
 * id :	  purchase order id
 * cdid:  unique identifier of CD	
 * price: unit price 
 */
DROP TABLE IF EXISTS POItem;
CREATE TABLE POItem (
 id       INT UNSIGNED NOT NULL,
 cdid     VARCHAR(20) NOT NULL,
 price    INT UNSIGNED NOT NULL,
 PRIMARY KEY(id,cdid),
 INDEX (id),
 FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
 INDEX (cdid),
 FOREIGN KEY(cdid) REFERENCES CD(cdid) ON DELETE CASCADE
) ;

#
# Dumping data for table 'POitem'
#
INSERT INTO POItem (id, cdid, price) VALUES (1, 'cd001',  '1599');
INSERT INTO POItem (id, cdid, price) VALUES (2, 'cd002',  '2000');
INSERT INTO POItem (id, cdid, price) VALUES (3, 'cd003',  '1799');
#
#

/* visit to website
 * day:		date
 * cdid:	unique identifier of CD
 * eventtype:	status of purchase
 */ 
DROP TABLE IF EXISTS VisitEvent;
CREATE TABLE VisitEvent (
 day 		varchar(8) NOT NULL,
 cdid 	varchar(20) not null REFERENCES CD.cdid,
 eventtype 	ENUM('VIEW','CART','PURCHASE') NOT NULL,	
 FOREIGN KEY(cdid) REFERENCES CD(cdid)
) ;

#
# Dumping data for table 'VisitEvent'
#
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('12202002', 'cd001', 'VIEW');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('12242002', 'cd001', 'CART');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('12252002', 'cd001', 'PURCHASE');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('10202002', 'cd002', 'VIEW');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('12242002', 'cd002', 'CART');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('12252002', 'cd002', 'PURCHASE');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('01202002', 'cd003', 'VIEW');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('01242002', 'cd003', 'CART');
INSERT INTO VisitEvent (day, cdid, eventtype) VALUES ('02252002', 'cd003', 'PURCHASE');
#
#
