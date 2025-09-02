-- Insertar compras para el cliente ABC123
INSERT INTO purchases (purchase_id, description, price, status, created, created_time, updated, updated_time, customer_id) VALUES
(RANDOM_UUID(), 'Compra de productos electrónicos', 150.75, 'PENDING', CURRENT_DATE - 0, '10:30:00', CURRENT_DATE - 0, '10:30:00', 'ABC123'),
(RANDOM_UUID(), 'Compra de ropa', 80.50, 'PENDING', CURRENT_DATE - 1, '11:00:00', CURRENT_DATE - 1, '11:00:00', 'ABC123'),
(RANDOM_UUID(), 'Compra de libros', 45.00, 'PENDING', CURRENT_DATE - 2, '12:15:00', CURRENT_DATE - 2, '12:15:00', 'ABC123'),
(RANDOM_UUID(), 'Compra de muebles', 300.00, 'PAID', CURRENT_DATE - 5, '09:45:00', CURRENT_DATE - 5, '09:45:00', 'ABC123'),
(RANDOM_UUID(), 'Compra de electrodomésticos', 500.00, 'PAID', CURRENT_DATE - 6, '14:20:00', CURRENT_DATE - 6, '14:20:00', 'ABC123'),
(RANDOM_UUID(), 'Compra de juguetes', 25.00, 'CANCELLED', CURRENT_DATE - 15, '16:00:00', CURRENT_DATE - 15, '16:00:00', 'ABC123');

-- Insertar compras para el cliente DEF456
INSERT INTO purchases (purchase_id, description, price, status, created, created_time, updated, updated_time, customer_id) VALUES
(RANDOM_UUID(), 'Compra de herramientas', 120.00, 'PENDING', CURRENT_DATE - 0, '10:45:00', CURRENT_DATE - 0, '10:45:00', 'DEF456'),
(RANDOM_UUID(), 'Compra de alimentos', 60.00, 'PENDING', CURRENT_DATE - 1, '11:30:00', CURRENT_DATE - 1, '11:30:00', 'DEF456'),
(RANDOM_UUID(), 'Compra de bebidas', 30.00, 'PENDING', CURRENT_DATE - 2, '12:45:00', CURRENT_DATE - 2, '12:45:00', 'DEF456'),
(RANDOM_UUID(), 'Compra de accesorios', 200.00, 'PAID', CURRENT_DATE - 5, '09:30:00', CURRENT_DATE - 5, '09:30:00', 'DEF456'),
(RANDOM_UUID(), 'Compra de calzado', 150.00, 'PAID', CURRENT_DATE - 6, '14:00:00', CURRENT_DATE - 6, '14:00:00', 'DEF456'),
(RANDOM_UUID(), 'Compra de artículos deportivos', 75.00, 'CANCELLED', CURRENT_DATE - 15, '15:30:00', CURRENT_DATE - 15, '15:30:00', 'DEF456');

-- Insertar compras para el cliente GHI789
INSERT INTO purchases (purchase_id, description, price, status, created, created_time, updated, updated_time, customer_id) VALUES
(RANDOM_UUID(), 'Compra de software', 250.00, 'PENDING', CURRENT_DATE - 0, '10:15:00', CURRENT_DATE - 0, '10:15:00', 'GHI789'),
(RANDOM_UUID(), 'Compra de hardware', 400.00, 'PENDING', CURRENT_DATE - 1, '11:15:00', CURRENT_DATE - 1, '11:15:00', 'GHI789'),
(RANDOM_UUID(), 'Compra de servicios', 100.00, 'PENDING', CURRENT_DATE - 2, '12:00:00', CURRENT_DATE - 2, '12:00:00', 'GHI789'),
(RANDOM_UUID(), 'Compra de muebles de oficina', 350.00, 'PAID', CURRENT_DATE - 5, '09:15:00', CURRENT_DATE - 5, '09:15:00', 'GHI789'),
(RANDOM_UUID(), 'Compra de suministros', 90.00, 'PAID', CURRENT_DATE - 6, '13:45:00', CURRENT_DATE - 6, '13:45:00', 'GHI789'),
(RANDOM_UUID(), 'Compra de decoración', 60.00, 'CANCELLED', CURRENT_DATE - 15, '15:00:00', CURRENT_DATE - 15, '15:00:00', 'GHI789');

-- Insertar detalles para las compras del cliente ABC123
INSERT INTO purchase_details (detail_id, purchase_id, item_name, unit_price, quantity, total_price) VALUES
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de productos electrónicos' AND customer_id = 'ABC123'), 'Laptop', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de productos electrónicos' AND customer_id = 'ABC123'), 'Mouse', 25.00, 1, 25.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de productos electrónicos' AND customer_id = 'ABC123'), 'Cargador', 25.75, 1, 25.75),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de ropa' AND customer_id = 'ABC123'), 'Camisa', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de ropa' AND customer_id = 'ABC123'), 'Pantalón', 40.50, 1, 40.50),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de ropa' AND customer_id = 'ABC123'), 'Cinturón', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de libros' AND customer_id = 'ABC123'), 'Libro de Java', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de libros' AND customer_id = 'ABC123'), 'Libro de Python', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de libros' AND customer_id = 'ABC123'), 'Libro de SQL', 5.00, 1, 5.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles' AND customer_id = 'ABC123'), 'Silla', 150.00, 1, 150.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles' AND customer_id = 'ABC123'), 'Mesa', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles' AND customer_id = 'ABC123'), 'Lámpara', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de electrodomésticos' AND customer_id = 'ABC123'), 'Refrigerador', 300.00, 1, 300.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de electrodomésticos' AND customer_id = 'ABC123'), 'Lavadora', 200.00, 1, 200.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de electrodomésticos' AND customer_id = 'ABC123'), 'Microondas', 0.00, 1, 0.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de juguetes' AND customer_id = 'ABC123'), 'Carro', 15.00, 1, 15.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de juguetes' AND customer_id = 'ABC123'), 'Muñeca', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de juguetes' AND customer_id = 'ABC123'), 'Rompecabezas', 0.00, 1, 0.00);

-- Insertar detalles para las compras del cliente DEF456
INSERT INTO purchase_details (detail_id, purchase_id, item_name, unit_price, quantity, total_price) VALUES
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de herramientas' AND customer_id = 'DEF456'), 'Martillo', 40.00, 1, 40.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de herramientas' AND customer_id = 'DEF456'), 'Destornillador', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de herramientas' AND customer_id = 'DEF456'), 'Sierra', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de alimentos' AND customer_id = 'DEF456'), 'Arroz', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de alimentos' AND customer_id = 'DEF456'), 'Frijoles', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de alimentos' AND customer_id = 'DEF456'), 'Carne', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de bebidas' AND customer_id = 'DEF456'), 'Agua', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de bebidas' AND customer_id = 'DEF456'), 'Jugo', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de bebidas' AND customer_id = 'DEF456'), 'Refresco', 10.00, 1, 10.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de accesorios' AND customer_id = 'DEF456'), 'Collar', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de accesorios' AND customer_id = 'DEF456'), 'Pulsera', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de accesorios' AND customer_id = 'DEF456'), 'Anillo', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de calzado' AND customer_id = 'DEF456'), 'Zapatos', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de calzado' AND customer_id = 'DEF456'), 'Tenis', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de calzado' AND customer_id = 'DEF456'), 'Sandalias', 0.00, 1, 0.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de artículos deportivos' AND customer_id = 'DEF456'), 'Pelota', 25.00, 1, 25.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de artículos deportivos' AND customer_id = 'DEF456'), 'Raqueta', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de artículos deportivos' AND customer_id = 'DEF456'), 'Red', 0.00, 1, 0.00);

-- Insertar detalles para las compras del cliente GHI789
INSERT INTO purchase_details (detail_id, purchase_id, item_name, unit_price, quantity, total_price) VALUES
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de software' AND customer_id = 'GHI789'), 'Licencia Windows', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de software' AND customer_id = 'GHI789'), 'Licencia Office', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de software' AND customer_id = 'GHI789'), 'Antivirus', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de hardware' AND customer_id = 'GHI789'), 'Disco Duro', 200.00, 1, 200.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de hardware' AND customer_id = 'GHI789'), 'Memoria RAM', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de hardware' AND customer_id = 'GHI789'), 'Procesador', 100.00, 1, 100.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de servicios' AND customer_id = 'GHI789'), 'Mantenimiento', 50.00, 1, 50.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de servicios' AND customer_id = 'GHI789'), 'Instalación', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de servicios' AND customer_id = 'GHI789'), 'Configuración', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles de oficina' AND customer_id = 'GHI789'), 'Escritorio', 200.00, 1, 200.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles de oficina' AND customer_id = 'GHI789'), 'Silla ergonómica', 150.00, 1, 150.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de muebles de oficina' AND customer_id = 'GHI789'), 'Archivador', 0.00, 1, 0.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de suministros' AND customer_id = 'GHI789'), 'Papel', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de suministros' AND customer_id = 'GHI789'), 'Tinta', 40.00, 1, 40.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de suministros' AND customer_id = 'GHI789'), 'Folder', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de decoración' AND customer_id = 'GHI789'), 'Cuadro', 30.00, 1, 30.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de decoración' AND customer_id = 'GHI789'), 'Florero', 20.00, 1, 20.00),
(RANDOM_UUID(), (SELECT purchase_id FROM purchases WHERE description = 'Compra de decoración' AND customer_id = 'GHI789'), 'Planta', 10.00, 1, 10.00);