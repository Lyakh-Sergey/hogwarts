-- liquibase formatted sql

-- changeset sergeylyakh:1
CREATE INDEX student_name_index ON student (name);

-- changeset sergeylyakh:2
CREATE INDEX faculty_nc_index ON faculty (name, color);