CREATE TABLE IF NOT EXISTS circulation_item (
    id UUID PRIMARY KEY,

    holdings_record_id        UUID    NOT NULL,
    status                    VARCHAR NOT NULL,
    material_type_id          VARCHAR NOT NULL,
    permanent_loan_type_id    VARCHAR NOT NULL,
    instance_title            VARCHAR NOT NULL,
    item_barcode              VARCHAR NOT NULL,
    pickup_location           VARCHAR NOT NULL,

    created_by                UUID,
    created_date              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    --created_by_username VARCHAR(100),
    updated_by                UUID,
    updated_date              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    --updated_by_username VARCHAR(100)
);
