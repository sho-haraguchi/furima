erDiagram
    users ||--o{ products : "出品する"
    users ||--o{ buys : "購入する"
    products ||--o. buys : "購入される"
    buys ||--|| addresses : "配送先となる"

    users {
        bigint id PK
        varchar nickname
        varchar email UK
        varchar password
        varchar last_name
        varchar first_name
        varchar last_name_kana
        varchar first_name_kana
        date birthday
    }

    products {
        bigint id PK
        bigint user_id FK
        varchar name
        varchar description
        integer category_id
        integer status_id
        integer shipping_fee_id
        integer prefecture_id
        integer shipping_days_id
        bigint price
        varchar img
    }

    buys {
        bigint id PK
        bigint user_id FK
        bigint product_id FK
    }

    addresses {
        bigint id PK
        bigint buy_id FK
        varchar post_number
        integer prefecture_id
        varchar city
        varchar block
        varchar building
        varchar phone
    }
