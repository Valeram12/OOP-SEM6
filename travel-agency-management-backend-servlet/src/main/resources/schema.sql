CREATE TABLE IF NOT EXISTS crew_members(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tours(
    id BIGSERIAL PRIMARY KEY,
    departure_from VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP(6) NOT NULL,
    arrival_time TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS crew_members_flights(
    id BIGSERIAL PRIMARY KEY,
    fk_crew_member_id BIGINT REFERENCES crew_members(id) ON DELETE CASCADE,
    fk_flight_id BIGINT REFERENCES tours(id) ON DELETE CASCADE
)