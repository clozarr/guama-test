-- Crear tabla purchases
CREATE TABLE IF NOT EXISTS purchases (
    purchase_id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created DATE NOT NULL,
    created_time TIME NOT NULL,
    updated DATE NOT NULL,
    updated_time TIME NOT NULL,
    customer_id VARCHAR(50) NOT NULL
);

-- Crear tabla purchase_details
CREATE TABLE IF NOT EXISTS purchase_details (
    detail_id UUID PRIMARY KEY,
    purchase_id UUID NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id)
);
