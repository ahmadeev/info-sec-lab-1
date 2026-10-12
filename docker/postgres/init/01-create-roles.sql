\getenv application_password DB_PASSWORD

CREATE ROLE lab1_app LOGIN PASSWORD :'application_password';
CREATE SCHEMA app AUTHORIZATION lab1_app;
ALTER ROLE lab1_app SET search_path TO app;
