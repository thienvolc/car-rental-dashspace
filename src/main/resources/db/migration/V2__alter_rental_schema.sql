alter table `rental`
    rename column `notes` to `note`,
    modify column `rental_code` varchar(50) unique not null,
    add column `currency` varchar(3)     not null,
    add column `subtotal` decimal(12, 2) not null,
    drop column `daily_rate`,
    modify column `status` enum ('PAYMENT_PROCESSING', 'PENDING', 'APPROVED', 'REJECTED', 'ACTIVE', 'COMPLETED', 'CANCELLED');

create table `vehicle_availability`
(
    vehicle_id int    not null,
    date       DATE   not null,
    price      double not null,
    status     enum ('AVAILABLE', 'HELD', 'RENTER'),

    primary key (vehicle_id, date)
)
