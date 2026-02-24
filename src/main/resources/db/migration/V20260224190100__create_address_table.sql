CREATE TABLE address (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50),
    complement VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(2) NOT NULL,
    postal_code VARCHAR(8) NOT NULL,
    country VARCHAR(100) NOT NULL,
    CONSTRAINT fk_address_company
        FOREIGN KEY (id)
        REFERENCES company (id)
        ON DELETE CASCADE
);
