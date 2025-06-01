CREATE TABLE subscriptions
(
    id           UUID PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    user_id      UUID         NOT NULL,
    created_at   TIMESTAMPTZ,

    CONSTRAINT fk_subscriptions_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_subscriptions_user_id ON subscriptions (user_id);
CREATE INDEX idx_subscriptions_service_name ON subscriptions (service_name);