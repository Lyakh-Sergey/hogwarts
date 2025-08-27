-- Ограничение на возраст студента (не менее 16 лет)
ALTER TABLE student ADD CONSTRAINT chk_student_age CHECK (age >= 16);

-- Ограничение на уникальность и не null имени студента
ALTER TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE student ADD CONSTRAINT uk_student_name UNIQUE (name);

-- Ограничение на уникальность пары название-цвет факультета
ALTER TABLE faculty ADD CONSTRAINT uk_faculty_name_color UNIQUE (name, color);

-- Установка значения по умолчанию для возраста студента (20 лет)
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;