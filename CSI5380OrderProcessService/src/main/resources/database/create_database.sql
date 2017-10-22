DROP DATABASE cd_store;
CREATE SCHEMA cd_store;

-- NOTE: May need to execute below statement if database connection fails in project
-- Where 'test123' is your root password

-- GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'test123';

/************************************
Drop tables if they exist
************************************/

# DROP TABLE IF EXISTS CD;
# DROP TABLE IF EXISTS Address;
# DROP TABLE IF EXISTS PO;
# DROP TABLE IF EXISTS POItem;
# DROP TABLE IF EXISTS VisitEvent;
# DROP TABLE IF EXISTS Customer;
# DROP TABLE IF EXISTS ShippingInfo;

/************************************
 Create database tables
************************************/

/* CD Table
*
* id: Unique identifier of CD (like ISBN for books)
* title: Title of CD
* artist: CD composer or performer or band or orchestra or selection
* year: Year the CD was released.
* description: Short description of the CD.
* price: Unit price WHEN ordered.
* label: Record Label (like MOTOWN, SONY ...)
* category: Genre of music.
* img_url: URL of the CD album art.
*/
CREATE TABLE CD (
  id          VARCHAR(20)                     NOT NULL,
  title       VARCHAR(60)                     NOT NULL,
  artist      VARCHAR(255)                    NOT NULL,
  year        YEAR                            NOT NULL,
  description TEXT                            NOT NULL,
  price       DECIMAL(15, 2)                  NOT NULL,
  label       VARCHAR(255)                    NOT NULL,
  category    ENUM ('COUNTRY', 'POP', 'ROCK') NOT NULL,
  img_url     VARCHAR(1000)                   NOT NULL,
  PRIMARY KEY (id)
);

/* VisitEvent Table
 * - Represents a log event when the user visits the CD store website.
 *
 * date: Datetime of visit.
 * cd_id:	Unique identifier of CD
 * eventtype:	Status of purchase
 */
CREATE TABLE VisitEvent (
  date      DATETIME                          NOT NULL,
  cd_id     VARCHAR(20)                       NOT NULL,
  eventtype ENUM ('VIEW', 'CART', 'PURCHASE') NOT NULL,
  FOREIGN KEY (cd_id) REFERENCES CD (id)
    ON DELETE CASCADE
);

/* Customer Table
 *
 * id : customer id
 * last_name : last name of customer
 * first_name : first name of customer
 * password : customer's account password (encoded in Base64).
 * email : email addressList of customer (this should be the username field on the UI)
 * default_shipping_address_id: ID of default billing address
 * default_billing_address_id: ID of default shipping address
 */
CREATE TABLE Customer (
  id                          INT UNSIGNED NOT NULL AUTO_INCREMENT,
  last_name                   VARCHAR(100) NOT NULL,
  first_name                  VARCHAR(100) NOT NULL,
  password                    VARCHAR(255) NOT NULL,
  email                       VARCHAR(255) NOT NULL,
  default_shipping_address_id INT UNSIGNED NOT NULL,
  default_billing_address_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (default_shipping_address_id)
  REFERENCES Address (id)
    ON DELETE CASCADE,
  FOREIGN KEY (default_billing_address_id)
  REFERENCES Address (id)
    ON DELETE CASCADE
);

/* Address Table
 *
 * id: Unique address id.
 * full_name: Full name of the customer who this address belongs to:
 * E.g.: First Name + Last Name and can include titles such as (Mr, Mrs, Miss, Dr, etc.).
 *
 * address_line_1: Main address line (street number, street)
 * address_line_2: Secondary address line (Unit/apt #)
 * city: City
 * province: Province
 * country: Country - defaults to Canada
 * zip: ZIP/Postal Code
 * phone: Contact number for this customer's address.
 * type: Type of address (billing or shipping).
 */
CREATE TABLE Address (
  id             INT UNSIGNED                 NOT NULL AUTO_INCREMENT,
  full_name      VARCHAR(255)                 NOT NULL,
  address_line_1 VARCHAR(255)                 NOT NULL,
  address_line_2 VARCHAR(255)                 NOT NULL,
  city           VARCHAR(20)                  NOT NULL,
  province       VARCHAR(20)                  NOT NULL,
  country        VARCHAR(20)                  NOT NULL DEFAULT 'Canada',
  zip            VARCHAR(20)                  NOT NULL,
  phone          VARCHAR(20),
  type           ENUM ('BILLING', 'SHIPPING') NOT NULL,
  PRIMARY KEY (id)
);

/* Shipping Info Table
 *
 * id: ID of the shipping method.
 * company: Company that will ship order (Canada Post, UPS, FedEx).
 * type: Shipping delivery type (standard, express, one-day)
 * price: Price of the shipping delivery type.
 */
CREATE TABLE ShippingInfo (
  id      INT UNSIGNED   NOT NULL AUTO_INCREMENT,
  company VARCHAR(100)   NOT NULL,
  type    VARCHAR(100)   NOT NULL,
  price   DECIMAL(15, 2) NOT NULL,
  PRIMARY KEY (id)
);

/* Purchase Order Table
 *
 * id: ID of purchase order
 * customer_id: ID of customer who is purchasing this order.
 * address_id: ID of the Address that the customer is using to ship this order.
 * shipping_type_id: ID of the ShippingInfo type the customer has selected to ship their order with.
 * date: Current datetime of when the user ordered this purchase order.
 * status: Status of purchase (ordered, processed - order was approved, or denied).
 * sub_total: Total amount for the cost of all the CDs in the order before taxes and shipping applied.
 * grand_total: Total amount for the cost of the order including cost of CDs + shipping cost + tax.
 * tax_total: Total amount of tax applied to the CDs amd shipping cost.
 */
CREATE TABLE PO (
  id                  INT UNSIGNED                            NOT NULL AUTO_INCREMENT,
  customer_id         INT UNSIGNED                            NOT NULL,
  shipping_address_id INT UNSIGNED                            NOT NULL,
  billing_address_id  INT UNSIGNED                            NOT NULL,
  shipping_type_id    INT UNSIGNED                            NOT NULL,
  date                DATETIME                                NOT NULL,
  status              ENUM ('ORDERED', 'PROCESSED', 'DENIED') NOT NULL DEFAULT 'ORDERED',
  sub_total           DECIMAL(15, 2)                          NOT NULL,
  grand_total         DECIMAL(15, 2)                          NOT NULL,
  tax_total           DECIMAL(15, 2)                          NOT NULL,
  PRIMARY KEY (id),
  INDEX (customer_id),
  FOREIGN KEY (customer_id) REFERENCES Customer (id)
    ON DELETE CASCADE,
  INDEX (shipping_address_id),
  FOREIGN KEY (shipping_address_id) REFERENCES Address (id)
    ON DELETE CASCADE,
  INDEX (billing_address_id),
  FOREIGN KEY (billing_address_id) REFERENCES Address (id)
    ON DELETE CASCADE,
  INDEX (shipping_type_id),
  FOREIGN KEY (shipping_type_id) REFERENCES ShippingInfo (id)
    ON DELETE CASCADE
);

/* Items on order Table
 *
 * po_id: Purchase order id
 * cd_id: Unique identifier of CD
 * unit_price: The price per CD at time of order.
 * num_ordered: Number of this specific CD ordered
 */
CREATE TABLE POItem (
  po_id       INT UNSIGNED   NOT NULL,
  cd_id       VARCHAR(20)    NOT NULL,
  unit_price  DECIMAL(15, 2) NOT NULL,
  num_ordered TINYINT        NOT NULL,
  PRIMARY KEY (po_id, cd_id),
  INDEX (po_id),
  FOREIGN KEY (po_id)
  REFERENCES PO (id)
    ON DELETE CASCADE,
  INDEX (cd_id),
  FOREIGN KEY (cd_id)
  REFERENCES CD (id)
    ON DELETE CASCADE
);

