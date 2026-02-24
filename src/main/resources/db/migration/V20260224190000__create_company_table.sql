CREATE TABLE company (
    id UUID PRIMARY KEY,
    legal_name VARCHAR(255) NOT NULL,
    trade_name VARCHAR(255) NOT NULL,
    tax_id VARCHAR(14) NOT NULL UNIQUE,
    company_type VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(10),
    cellphone VARCHAR(11) NOT NULL,
    active BOOLEAN NOT NULL
);
