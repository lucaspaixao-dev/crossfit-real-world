CREATE TABLE company (
    id UUID PRIMARY KEY,
    legal_name VARCHAR(255) NOT NULL,
    trade_name VARCHAR(255) NOT NULL,
    tax_id VARCHAR(14) NOT NULL,
    company_type VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(10),
    cellphone VARCHAR(11) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX ux_company_legal_name ON company (legal_name);
CREATE UNIQUE INDEX ux_company_tax_id ON company (tax_id);
CREATE UNIQUE INDEX ux_company_email ON company (email);
