ALTER TABLE user_orders
ADD COLUMN remaining_volume bigint not null default 0;