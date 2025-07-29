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
