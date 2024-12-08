CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS statistics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    total_products INT NOT NULL,
    active_products INT NOT NULL,
    inactive_products INT NOT NULL,
    average_price DECIMAL(10, 2) NOT NULL,
    most_popular_product VARCHAR(255),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Example insertion for categories
INSERT INTO categories (name)
VALUES ('Electronics');

-- Example insertion for products
INSERT INTO products (name, category_id, price, status, created_at)
VALUES ('Smartphone', 1, 150.75, 'ACTIVE', CURRENT_TIMESTAMP);

-- Example insertion for statistics
INSERT INTO statistics (category, total_products, active_products, inactive_products, average_price, most_popular_product, last_updated)
VALUES ('Electronics', 1, 1, 0, 150.75, 'Smartphone', CURRENT_TIMESTAMP);
