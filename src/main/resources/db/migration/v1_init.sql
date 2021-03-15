DROP TABLE IF EXISTS groups CASCADE;

CREATE TABLE groups (
    id SERIAL PRIMARY KEY,
    name CHAR(5) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS students CASCADE;

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
	group_id INT REFERENCES groups (id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS courses CASCADE;

CREATE TABLE courses (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

DROP TABLE IF EXISTS students_courses CASCADE;

CREATE TABLE students_courses (
    student_id INT NOT NULL REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id INT NOT NULL REFERENCES courses (id) ON UPDATE CASCADE,
    PRIMARY KEY (student_id, course_id)
);
