/************************************
 Insert data for database tables
************************************/

/* Dumping data for table 'CD' */
INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd001', '16 Biggest Hits', 'Johnny Cash', 1999, 'The album is made of the biggest hits of Cash''s career like "Ring of Fire",
"Understand Your Man", and "A Boy Named Sue". The album also contains several songs which weren''t hits such as "I Still Miss Someone", and "The Legend of John Henry''s Hammer".',
        15.99, 'Sony', 'COUNTRY', 'https://images-na.ssl-images-amazon.com/images/I/5188ENWBRAL._SX355_.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd002', 'Greatest Hits (& Some That Will Be)', 'Willie Nelson', 1981,
        'Greatest Hits (& Some That Will Be) is a compilation album by country artist Willie Nelson. It was released in 1981 as a double-LP. It has sold 6 million copies worldwide.',
        15.99, 'Columbia', 'COUNTRY',
        'https://upload.wikimedia.org/wikipedia/en/9/95/Willie_Nelson_-_Greatest_Hits_%28%26_Some_That_Will_Be%29.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd003', 'Showcase', 'Patsy Cline', 1961,
        'Showcase is a studio album by American country music singer Patsy Cline, recorded with The Jordanaires and released November 27, 1961.
        It was Cline''s second studio album and her first since Patsy Cline in 1957.',
        15.99, 'Decca', 'COUNTRY', 'https://upload.wikimedia.org/wikipedia/en/0/03/Patsy_Cline-_Original_Showcase.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd004', 'Fully Completely', 'Tragically Hip', 1992, 'Fully Completely is the third studio album by Canadian rock band The Tragically Hip.
The album was released in October 1992 and produced by Chris Tsangarides. The cover art was designed by Dutch artist Lieve Prins. It produced six singles:
"Locked in the Trunk of a Car", "Fifty Mission Cap", "Courage (For Hugh MacLennan)", "At the Hundredth Meridian", "Looking for a Place to Happen",
 and "Fully Completely".', 20.00, 'MCA', 'ROCK',
        'https://upload.wikimedia.org/wikipedia/en/thumb/c/c6/Fully_Completely.jpg/220px-Fully_Completely.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd005', 'The White Album', 'The Beatles', 1968, 'The Beatles, also known as the White Album, is the ninth studio album by English rock
group the Beatles, released on 22 November 1968. A double album, its plain white sleeve has no graphics or text other than the band''s name
embossed,[a] which was intended as a direct contrast to the vivid cover artwork of the band''s earlier Sgt. Pepper''s Lonely Hearts Club Band.',
        20.00, 'Apple', 'ROCK', 'https://upload.wikimedia.org/wikipedia/commons/2/20/TheBeatles68LP.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd006', 'Forty Licks', 'Rolling Stones', 2002, 'Forty Licks is a double compilation album by The Rolling Stones. A 40-year career-spanning
retrospective, Forty Licks is notable for being the first retrospective to combine their formative Decca/London era of the 1960s, now licensed by
ABKCO Records (on disc one), with their self-owned post-1970 material, distributed at the time by Virgin/EMI but now distributed by ABKCO''s own
distributor Universal Music Group (on mostly disc two).', 20.00, 'Virgin/ABKCO/Decca', 'ROCK',
        'https://upload.wikimedia.org/wikipedia/en/d/d1/Rollingstonesfortylicks.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd007', 'The Immaculate Collection', 'Madonna', 1990,
        'The Immaculate Collection is the first greatest hits album by American singer and songwriter Madonna. It was released on November 9, 1990,
        by Sire and Warner Bros. Records. It contains new remixes of fifteen of her hit singles from 1983 to 1990, as well as two new songs, "Justify My Love" and "Rescue Me".',
        17.99, 'Sire/Warner Bros.', 'POP',
        'https://upload.wikimedia.org/wikipedia/en/5/51/Madonna_-_The_Immaculate_Collection.png');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd008', 'Jagged Little Pill', 'Alannis Morissette', 1995, 'agged Little Pill is the third album by Canadian singer Alanis Morissette,
released in 1995 through Maverick. It was Morissette''s first album released outside Canada. Morissette began work on her next album after
moving from her hometown, Ottawa, to Toronto; she made little progress until she traveled to Los Angeles, where she met producer Glen Ballard.
Morissette and Ballard had an instant connection and began co-writing and experimenting with sounds.', 17.99,
        'Maverick/Reprise', 'POP',
        'https://upload.wikimedia.org/wikipedia/en/4/47/Alanis_Morissette_-_Jagged_Little_Pill.jpg');

INSERT INTO CD (id, title, artist, year, description, price, label, category, img_url)
VALUES ('cd009', 'The Age of Plastic', 'The Buggles', 1980, 'The Age of Plastic is the debut studio album by the British new wave duo The Buggles,
composed of Trevor Horn and Geoff Downes. The name of the record was conceived from the group''s intention of being a "plastic group".
The album has lyrical themes of nostalgia and anxiety about the possible effects of modern technology.',
        17.99, 'Island', 'POP',
        'https://upload.wikimedia.org/wikipedia/en/0/04/Video_Killed_the_Radio_Star_single_cover.jpg');

/* Dumping data for table 'Address' */
INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (1, 'Liam Peyton', '123 King Edward', '', 'Ottawa', 'ON', 'Canada', 'K1E 6T5', '613-123-4567', 'BILLING');

INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (2, 'Liam Peyton', '123 King Edward', '', 'Ottawa', 'ON', 'Canada', 'K1E 6T5', '613-123-4567', 'SHIPPING');

INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (3, 'Peter White', '34 Rue St-Dominique', '', 'Gatineau', 'QC', 'Canada', 'K2E 6K5', '514-123-8569', 'BILLING');

INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (4, 'Peter White', '34 Rue St-Dominique', '', 'Gatineau', 'QC', 'Canada', 'K2E 6K5', '514-123-8569', 'SHIPPING');

INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (5, 'Andy Adler', '99 Main St.', '', 'Ottawa', 'ON', 'Canada', 'K6E 9T5', '613-123-9568', 'BILLING');

INSERT INTO Address (id, full_name, address_line_1, address_line_2, city, province, country, zip, phone, type)
VALUES (6, 'Andy Adler', '99 Main St.', '', 'Ottawa', 'ON', 'Canada', 'K6E 9T5', '613-123-9568', 'SHIPPING');

/* Dumping data for table 'Customer' */
INSERT INTO Customer (id, first_name, last_name, password, email, default_shipping_address_id, default_billing_address_id)
VALUES (1, 'Liam', 'Peyton', 'cGFzc3dvcmQ=', 'liam_peyton@gmail.com', 2, 1);
INSERT INTO Customer (id, first_name, last_name, password, email, default_shipping_address_id, default_billing_address_id)
VALUES (2, 'Peter', 'White', 'cGFzc3dvcmQ=', 'peter_white@gmail.com', 4, 3);
INSERT INTO Customer (id, first_name, last_name, password, email, default_shipping_address_id, default_billing_address_id)
VALUES (3, 'Andy', 'Adler', 'cGFzc3dvcmQ=', 'andy_adler@gmail.com', 6, 5);

/* Insert data into the Shipping table */
INSERT INTO ShippingInfo (id, company, type, price) VALUES (1, 'Canada Post', 'Priority', 20.00);
INSERT INTO ShippingInfo (id, company, type, price) VALUES (2, 'Canada Post', 'Xpresspost', 15.00);
INSERT INTO ShippingInfo (id, company, type, price) VALUES (3, 'Canada Post', 'Regular', 10.00);

/* Dumping data for table 'PO' - Purchase Order */
INSERT INTO PO (id, customer_id, shipping_address_id, billing_address_id, shipping_type_id, date, status, sub_total, grand_total, tax_total)
VALUES (1, 1, 2, 1, 3, '2017-10-01 09:19:00', 'PROCESSED', 15.99, 29.37, 3.38);

INSERT INTO PO (id, customer_id, shipping_address_id, billing_address_id, shipping_type_id, date, status, sub_total, grand_total, tax_total)
VALUES (2, 2, 4, 3, 1, '2017-10-02 08:20:00', 'DENIED', 20.00, 45.20, 5.20);

INSERT INTO PO (id, customer_id, shipping_address_id, billing_address_id, shipping_type_id, date, status, sub_total, grand_total, tax_total)
VALUES (3, 3, 6, 5, 2, '2017-10-03 10:38:00', 'ORDERED', 17.99, 37.28, 4.29);

/* Dumping data for table 'POitem' - Purchase Order Item */
INSERT INTO POItem (po_id, cd_id, unit_price, num_ordered) VALUES (1, 'cd001', 15.99, 1);
INSERT INTO POItem (po_id, cd_id, unit_price, num_ordered) VALUES (2, 'cd002', 20.00, 1);
INSERT INTO POItem (po_id, cd_id, unit_price, num_ordered) VALUES (3, 'cd003', 17.99, 1);

/* Dumping data for table 'VisitEvent' */
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-01 09:16:00', 'cd001', 'VIEW');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-01 09:18:00', 'cd001', 'CART');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-01 09:19:00', 'cd001', 'PURCHASE');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-02 08:18:00', 'cd002', 'VIEW');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-02 08:19:00', 'cd002', 'CART');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-02 08:20:00', 'cd002', 'PURCHASE');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-03 10:18:00', 'cd003', 'VIEW');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-03 10:28:00', 'cd003', 'CART');
INSERT INTO VisitEvent (date, cd_id, eventtype) VALUES ('2017-10-03 10:38:00', 'cd003', 'PURCHASE');
