INSERT INTO item (id, name, price, description) VALUES (1, 'Razer Basilisk V3 Mouse', 3499.00, 'Ergonomic mouse mappable buttons');
INSERT INTO item (id, name, price, description) VALUES (2, 'Keychron Mechanical Keyboard', 12999.00, 'RGB backlit mechanical keyboard with Cherry MX blue switches');
INSERT INTO item (id, name, price, description) VALUES (3, 'Samsung USB-C Charger', 1999.00, 'Fast charging USB-C wall adapter, 45W');
INSERT INTO item (id, name, price, description) VALUES (4, 'Sony WH1000XM6', 39999.00, 'Over-ear headphones with active noise cancellation');
INSERT INTO item (id, name, price, description) VALUES (5, 'Canon Z6', 120000.00, 'Full frame DSLR camera');
ALTER TABLE item ALTER COLUMN id RESTART WITH 6;