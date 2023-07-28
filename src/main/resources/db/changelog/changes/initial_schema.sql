CREATE TABLE IF NOT EXISTS circulation_item
(
    id                  UUID PRIMARY KEY,
    holdings_record_id    UUID,
    status              VARCHAR(256),
    material_type_id    VARCHAR(256),
    permanent_loan_type_id VARCHAR(256),
    instance_title      VARCHAR(256),
    item_barcode        VARCHAR(256),
    pickup_location     VARCHAR(256),
    created_date        TIMESTAMP default now(),
    created_by_user_id  UUID,
    created_by_username VARCHAR(100),
    updated_date        TIMESTAMP,
    updated_by_user_id  UUID,
    updated_by_username VARCHAR(100)
);
