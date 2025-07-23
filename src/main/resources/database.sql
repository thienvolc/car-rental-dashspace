DROP DATABASE IF EXISTS car_rental_system;
CREATE DATABASE car_rental_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE car_rental_system;

-- ===============================================
-- USER & AUTHENTICATION MODULE
-- ===============================================

-- Table: user (add essential fields)
CREATE TABLE user
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    username              VARCHAR(50) UNIQUE  NOT NULL,
    email                 VARCHAR(100) UNIQUE NOT NULL,
    password_hash         VARCHAR(255)        NOT NULL,

    phone                 VARCHAR(20),
    birth_date            DATE,
    gender                ENUM ('MALE', 'FEMALE', 'OTHER'),
    avatar_url            VARCHAR(500),

    email_verified        BOOLEAN             DEFAULT FALSE,
    phone_verified        BOOLEAN             DEFAULT FALSE,

    status                ENUM ('ACTIVE', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE',
    last_login_at         TIMESTAMP           NULL,
    password_changed_at   TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,

    created_at            TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_user_email (email),
    INDEX idx_user_username (username),
    INDEX idx_user_status (status),
    INDEX idx_user_verified (email_verified, phone_verified)
);

-- Table: role (keep singular - domain entity)
CREATE TABLE role
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_role_name (name)
);

-- Table: user_role (junction table - can be plural or keep as is)
CREATE TABLE user_role
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT NOT NULL,
    role_id    INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_role (user_id, role_id),
    INDEX idx_user_role_user (user_id),
    INDEX idx_user_role_role (role_id)
);

-- Table: session (store both access and refresh tokens)
CREATE TABLE session
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    user_id               INT                 NOT NULL,
    refresh_token         TEXT                NOT NULL,
    refresh_token_expires TIMESTAMP           NOT NULL,
    device_info           VARCHAR(500),
    ip_address            VARCHAR(45),
    user_agent            TEXT,
    is_active             BOOLEAN             DEFAULT TRUE,
    last_used_at          TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    created_at            TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    INDEX idx_session_user (user_id),
    INDEX idx_session_expires (refresh_token_expires),
    INDEX idx_session_active (is_active)
);

-- Table: otp_verification (more descriptive)
CREATE TABLE otp_verification
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    code       VARCHAR(10)                                                   NOT NULL,
    email      VARCHAR(100)                                                  NOT NULL,
    purpose    ENUM ('REGISTRATION', 'FORGOT_PASSWORD', 'HOST_REGISTRATION') NOT NULL,
    status     ENUM ('PENDING', 'VERIFIED', 'FAILED', 'EXPIRED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP                                                      DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP                                                     NOT NULL,

    INDEX idx_otp_email (email),
    INDEX idx_otp_code (code),
    INDEX idx_otp_status (status),
    INDEX idx_otp_expires (expires_at)
);

-- ===============================================
-- USER VERIFICATION MODULE
-- ===============================================

-- Table: driving_license (remove redundant fields)
CREATE TABLE driving_license
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    user_id              INT                NOT NULL,
    verified_by_user_id  INT,
    verified_at          TIMESTAMP          NULL,
    license_number       VARCHAR(50) UNIQUE NOT NULL,
    full_name            VARCHAR(100)       NOT NULL,
    issue_date           DATE,
    expiry_date          DATE               NOT NULL,
    status               ENUM ('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING',
    rejection_reason     TEXT,
    front_image_url      VARCHAR(500)       NOT NULL,
    back_image_url       VARCHAR(500)       NOT NULL,
    created_at           TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (verified_by_user_id) REFERENCES user (id) ON DELETE SET NULL,
    INDEX idx_driving_license_user (user_id),
    INDEX idx_driving_license_number (license_number),
    INDEX idx_driving_license_status (status),
    INDEX idx_driving_license_expiry (expiry_date)
);

-- Table: identity_document (remove redundant fields)
CREATE TABLE identity_document
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    user_id                INT                NOT NULL,
    verified_by_user_id    INT,
    verified_at            TIMESTAMP          NULL,
    national_id_number     VARCHAR(20) UNIQUE NOT NULL,
    full_name              VARCHAR(100)       NOT NULL,
    issue_date             DATE,
    expiry_date            DATE,
    status                 ENUM ('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING',
    rejection_reason       TEXT,
    id_front_image_url     VARCHAR(500)       NOT NULL,
    selfie_with_id_url     VARCHAR(500)       NOT NULL,
    created_at             TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (verified_by_user_id) REFERENCES user (id) ON DELETE SET NULL,
    INDEX idx_identity_document_user (user_id),
    INDEX idx_identity_document_national_id (national_id_number),
    INDEX idx_identity_document_status (status)
);

-- ===============================================
-- VEHICLE MODULE
-- ===============================================

-- Table: location (more general, reusable)
CREATE TABLE location
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    user_id         INT          NOT NULL,
    province        VARCHAR(100) NOT NULL,
    district        VARCHAR(100) NOT NULL,
    ward            VARCHAR(100) NOT NULL,
    address_detail  TEXT         NOT NULL,
    latitude        DECIMAL(10, 8),
    longitude       DECIMAL(11, 8),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    INDEX idx_location_user (user_id),
    INDEX idx_location_coordinates (latitude, longitude)
);

-- Table: vehicle (add essential fields)
CREATE TABLE vehicle
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    owner_id               INT                                               NOT NULL,
    verified_by_user_id    INT,
    location_id            INT                                               NOT NULL,
    license_plate          VARCHAR(20) UNIQUE                                NOT NULL,
    manufacture_year       INT                                               NOT NULL,
    brand                  VARCHAR(50)                                       NOT NULL,
    model                  VARCHAR(100)                                      NOT NULL,
    seat_count             INT                                               NOT NULL,
    fuel_type              ENUM ('GASOLINE', 'DIESEL', 'ELECTRIC', 'HYBRID') NOT NULL,
    transmission           ENUM ('MANUAL', 'AUTOMATIC')                      NOT NULL,
    fuel_consumption       DECIMAL(4, 2),
    daily_rate             DECIMAL(10, 2)                                    NOT NULL,
    description            TEXT,
    features               JSON,
    approval_status        ENUM ('PENDING', 'APPROVED', 'REJECTED')        DEFAULT 'PENDING',
    rejection_reason       TEXT,
    status                 ENUM ('ACTIVE', 'RENTED', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE',
    created_at             TIMESTAMP                                       DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP                                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (verified_by_user_id) REFERENCES user (id) ON DELETE SET NULL,
    FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE RESTRICT,
    INDEX idx_vehicle_owner (owner_id),
    INDEX idx_vehicle_verified (verified_by_user_id),
    INDEX idx_vehicle_location (location_id),
    INDEX idx_vehicle_license_plate (license_plate),
    INDEX idx_vehicle_status (status),
    INDEX idx_vehicle_approval_status (approval_status),
    INDEX idx_vehicle_brand_model (brand, model),
    INDEX idx_vehicle_daily_rate (daily_rate)
);

-- Table: vehicle_image (more descriptive)
CREATE TABLE vehicle_image
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id  INT          NOT NULL,
    sort_order  INT          NOT NULL,
    image_url   VARCHAR(500) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (vehicle_id) REFERENCES vehicle (id) ON DELETE CASCADE,
    UNIQUE KEY unique_vehicle_image_order (vehicle_id, sort_order),
    INDEX idx_vehicle_image_vehicle (vehicle_id),
    INDEX idx_vehicle_image_order (sort_order)
);

-- Table: vehicle_document (more descriptive)
CREATE TABLE vehicle_document
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id           INT NOT NULL,
    registration_url     VARCHAR(500),
    inspection_url       VARCHAR(500),
    insurance_url        VARCHAR(500),
    front_image_url      VARCHAR(500),
    left_image_url       VARCHAR(500),
    right_image_url      VARCHAR(500),
    back_image_url       VARCHAR(500),
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (vehicle_id) REFERENCES vehicle (id) ON DELETE CASCADE,
    INDEX idx_vehicle_document_vehicle (vehicle_id)
);

-- ===============================================
-- RENTAL MODULE
-- ===============================================

-- Table: rental (add essential business fields)
CREATE TABLE rental
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    rental_code           VARCHAR(20) UNIQUE NOT NULL,
    vehicle_id            INT                NOT NULL,
    renter_id             INT                NOT NULL,
    pickup_date           DATETIME           NOT NULL,
    return_date           DATETIME           NOT NULL,
    actual_return_date    DATETIME           NULL,
    daily_rate            DECIMAL(10, 2)     NOT NULL,
    total_amount          DECIMAL(12, 2)     NOT NULL,
    deposit_amount        DECIMAL(10, 2)     NOT NULL DEFAULT 0,
    status                ENUM ('PENDING', 'APPROVED', 'REJECTED', 'ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    approved_at           TIMESTAMP          NULL,
    approved_by_user_id   INT                NULL,
    pickup_location       TEXT,
    return_location       TEXT,
    additional_charges    DECIMAL(10, 2)     DEFAULT 0,
    notes                 TEXT,
    created_at            TIMESTAMP          DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (vehicle_id) REFERENCES vehicle (id) ON DELETE RESTRICT,
    FOREIGN KEY (renter_id) REFERENCES user (id) ON DELETE RESTRICT,
    FOREIGN KEY (approved_by_user_id) REFERENCES user (id) ON DELETE SET NULL,
    INDEX idx_rental_code (rental_code),
    INDEX idx_rental_vehicle (vehicle_id),
    INDEX idx_rental_renter (renter_id),
    INDEX idx_rental_status (status),
    INDEX idx_rental_dates (pickup_date, return_date),

    CONSTRAINT chk_rental_dates CHECK (return_date > pickup_date),
    CONSTRAINT chk_rental_amount CHECK (total_amount >= 0),
    CONSTRAINT chk_rental_deposit CHECK (deposit_amount >= 0)
);

-- Table: rental_cancellation (more descriptive)
CREATE TABLE rental_cancellation
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    rental_id            INT NOT NULL,
    cancelled_by_user_id INT NOT NULL,
    cancelled_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason               TEXT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (rental_id) REFERENCES rental (id) ON DELETE CASCADE,
    FOREIGN KEY (cancelled_by_user_id) REFERENCES user (id) ON DELETE RESTRICT,
    INDEX idx_rental_cancellation_rental (rental_id),
    INDEX idx_rental_cancellation_user (cancelled_by_user_id)
);

-- Add essential missing tables

-- Table: payment (essential for car rental business)
CREATE TABLE payment
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    rental_id         INT                                                     NOT NULL,
    payment_type      ENUM ('DEPOSIT', 'RENTAL_FEE', 'ADDITIONAL_CHARGE', 'REFUND') NOT NULL,
    amount            DECIMAL(10, 2)                                          NOT NULL,
    payment_method    ENUM ('CASH', 'BANK_TRANSFER', 'CREDIT_CARD', 'E_WALLET') NOT NULL,
    payment_status    ENUM ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')    DEFAULT 'PENDING',
    transaction_id    VARCHAR(100),
    payment_gateway   VARCHAR(50),
    paid_at           TIMESTAMP                                               NULL,
    created_at        TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (rental_id) REFERENCES rental (id) ON DELETE CASCADE,
    INDEX idx_payment_rental (rental_id),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_transaction (transaction_id)
);

-- Table: review (important for rental marketplace)
CREATE TABLE review
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    rental_id   INT           NOT NULL,
    reviewer_id INT           NOT NULL,
    rating      INT           NOT NULL,
    comment     TEXT,
    created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (rental_id) REFERENCES rental (id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES user (id) ON DELETE CASCADE,
    INDEX idx_review_rental (rental_id),
    INDEX idx_review_reviewer (reviewer_id),
    INDEX idx_review_rating (rating),

    CONSTRAINT chk_review_rating CHECK (rating >= 1 AND rating <= 5),
    CONSTRAINT unique_review_per_rental UNIQUE (rental_id, reviewer_id)
);

-- Table: notification (essential for user communication)
CREATE TABLE notification
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT                                      NOT NULL,
    title      VARCHAR(255)                             NOT NULL,
    messageContact    TEXT                                     NOT NULL,
    type       ENUM ('INFO', 'WARNING', 'SUCCESS', 'ERROR') NOT NULL,
    is_read    BOOLEAN                                  DEFAULT FALSE,
    created_at TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    INDEX idx_notification_user (user_id),
    INDEX idx_notification_read (is_read),
    INDEX idx_notification_type (type)
);

-- ===============================================
-- INITIAL DATA
-- ===============================================

-- Add default roles
INSERT INTO role (name, description)
VALUES ('ADMIN', 'Administrator with full system access'),
       ('HOST', 'Vehicle owner who can list and manage vehicles'),
       ('RENTER', 'User who can rent vehicles');

-- Add default admin user
-- Password: Thien123456
INSERT INTO user (email, password_hash, username, status)
VALUES ('thienvolc@gmail.com', '$2a$10$DMDGPmWAKn/js5uv.2OivuISa5oOdT68vEr/gEGYPEcWDGF4O7SUO', 'thiendeptraivocung', 'ACTIVE');

-- Assign admin role to default user
INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM user WHERE email = 'thienvolc@gmail.com'), (SELECT id FROM role WHERE name = 'ADMIN'));
