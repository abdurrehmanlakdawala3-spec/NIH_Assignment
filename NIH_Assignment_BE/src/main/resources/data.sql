-- -- Disable foreign key constraints to allow truncation
-- Courses (20 records)
INSERT INTO course (id, course_code, title, description, created_at)
VALUES (1, 'CS-101', 'Introduction to Computer Science', 'Fundamentals of programming and computer science.', NOW()),
       (2, 'MATH-201', 'Calculus I', 'An introduction to differential calculus.', NOW()),
       (3, 'HIST-101', 'World History', 'A survey of world history from ancient times to the present.', NOW()),
       (4, 'CS-202', 'Object-Oriented Programming', 'Concepts of object-oriented programming using Java.', NOW()),
       (5, 'MATH-301', 'Linear Algebra', 'An introduction to linear algebra and its applications.', NOW()),
       (6, 'CS-301', 'Data Structures', 'Study of fundamental data structures in computer science.', NOW()),
       (7, 'CS-405', 'Algorithms', 'Design and analysis of algorithms.', NOW()),
       (8, 'CS-450', 'Artificial Intelligence', 'Introduction to the theory and practice of AI.', NOW()),
       (9, 'MATH-202', 'Calculus II', 'A continuation of Calculus I, covering integral calculus.', NOW()),
       (10, 'MATH-330', 'Differential Equations', 'Solving ordinary differential equations.', NOW()),
       (11, 'HIST-250', 'American History', 'A survey of the history of the United States.', NOW()),
       (12, 'LIT-201', 'English Literature', 'Survey of major works in English literature.', NOW()),
       (13, 'LIT-310', 'Shakespeare', 'A study of Shakespeare''s major plays.', NOW()),
       (14, 'PHYS-101', 'General Physics I', 'Mechanics, heat, and sound.', NOW()),
       (15, 'PHYS-102', 'General Physics II', 'Electricity, magnetism, and light.', NOW()),
       (16, 'ART-100', 'Art History', 'A survey of Western art history.', NOW()),
       (17, 'ART-220', 'Drawing I', 'Fundamentals of drawing.', NOW()),
       (18, 'ECON-101', 'Principles of Microeconomics', 'Study of individual economic behavior.', NOW()),
       (19, 'ECON-102', 'Principles of Macroeconomics', 'Study of the economy as a whole.', NOW()),
       (20, 'PHIL-205', 'Introduction to Logic', 'An introduction to formal logic.', NOW());

-- Students
INSERT INTO student (id, first_name, last_name, email, date_of_birth, created_at)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', '2002-05-15', NOW()),
       (2, 'Jane', 'Smith', 'jane.smith@example.com', '2003-01-20', NOW()),
       (3, 'Peter', 'Jones', 'peter.jones@example.com', '2002-09-10', NOW()),
       (4, 'Mary', 'Williams', 'mary.williams@example.com', '2003-03-25', NOW()),
       (5, 'David', 'Brown', 'david.brown@example.com', '2002-11-30', NOW()),
       (6, 'Sarah', 'Miller', 'sarah.miller@example.com', '2003-07-12', NOW()),
       (7, 'Michael', 'Davis', 'michael.davis@example.com', '2002-02-18', NOW()),
       (8, 'Jessica', 'Garcia', 'jessica.garcia@example.com', '2003-08-22', NOW()),
       (9, 'Chris', 'Rodriguez', 'chris.rodriguez@example.com', '2002-12-01', NOW()),
       (10, 'Amanda', 'Wilson', 'amanda.wilson@example.com', '2003-04-05', NOW()),
       (11, 'James', 'Martinez', 'james.martinez@example.com', '2002-06-19', NOW()),
       (12, 'Linda', 'Anderson', 'linda.anderson@example.com', '2003-10-30', NOW()),
       (13, 'Robert', 'Taylor', 'robert.taylor@example.com', '2002-01-15', NOW()),
       (14, 'Patricia', 'Thomas', 'patricia.thomas@example.com', '2003-05-21', NOW()),
       (15, 'Daniel', 'Hernandez', 'daniel.hernandez@example.com', '2002-08-14', NOW()),
       (16, 'Jennifer', 'Moore', 'jennifer.moore@example.com', '2003-11-09', NOW()),
       (17, 'William', 'Martin', 'william.martin@example.com', '2002-04-02', NOW()),
       (18, 'Elizabeth', 'Jackson', 'elizabeth.jackson@example.com', '2003-09-17', NOW()),
       (19, 'Joseph', 'Thompson', 'joseph.thompson@example.com', '2002-07-28', NOW()),
       (20, 'Susan', 'White', 'susan.white@example.com', '2003-12-24', NOW());

-- Users
INSERT INTO users (id, username, password, role, student_id, active)
VALUES (1, 'john.doe@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT', 1,
        true),
       (2, 'jane.smith@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT', 2,
        true),
       (3, 'peter.jones@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT', 3,
        true),
       (4, 'mary.williams@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        4, true),
       (5, 'david.brown@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT', 5,
        true),
       (6, 'sarah.miller@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        6, true),
       (7, 'michael.davis@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        7, true),
       (8, 'jessica.garcia@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        8, true),
       (9, 'chris.rodriguez@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 9, true),
       (10, 'amanda.wilson@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        10, true),
       (11, 'james.martinez@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 11, true),
       (12, 'linda.anderson@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 12, true),
       (13, 'robert.taylor@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        13, true),
       (14, 'patricia.thomas@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 14, true),
       (15, 'daniel.hernandez@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 15, true),
       (16, 'jennifer.moore@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 16, true),
       (17, 'william.martin@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 17, true),
       (18, 'elizabeth.jackson@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 18, true),
       (19, 'joseph.thompson@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2',
        'ROLE_STUDENT', 19, true),
       (20, 'susan.white@example.com', '$2a$10$o7JLytbCJqhWrM1TjnhUaOS5bA4LkOTnsA5R2cEBml7v7ZmAJhXO2', 'ROLE_STUDENT',
        20, true);


-- Enrollments
INSERT INTO enrollment (id, student_id, course_id, created_at)
VALUES (1, 1, 1, NOW()),   -- John Doe in CS-101
       (2, 1, 2, NOW()),   -- John Doe in MATH-201
       (3, 2, 4, NOW()),   -- Jane Smith in CS-202
       (4, 2, 5, NOW()),   -- Jane Smith in MATH-301
       (5, 3, 1, NOW()),   -- Peter Jones in CS-101
       (6, 4, 3, NOW()),   -- Mary Williams in HIST-101
       (7, 5, 6, NOW()),   -- David Brown in CS-301
       (8, 6, 7, NOW()),   -- Sarah Miller in CS-405
       (9, 7, 8, NOW()),   -- Michael Davis in CS-450
       (10, 8, 9, NOW()),  -- Jessica Garcia in MATH-202
       (11, 9, 10, NOW()), -- Chris Rodriguez in MATH-330
       (12, 10, 11, NOW()),-- Amanda Wilson in HIST-250
       (13, 11, 12, NOW()),-- James Martinez in LIT-201
       (14, 12, 13, NOW()),-- Linda Anderson in LIT-310
       (15, 13, 14, NOW()),-- Robert Taylor in PHYS-101
       (16, 14, 15, NOW()),-- Patricia Thomas in PHYS-102
       (17, 15, 16, NOW()),-- Daniel Hernandez in ART-100
       (18, 16, 17, NOW()),-- Jennifer Moore in ART-220
       (19, 17, 18, NOW()),-- William Martin in ECON-101
       (20, 18, 19, NOW());
-- Elizabeth Jackson in ECON-102

-- Update the sequence generators to avoid primary key conflicts with new records.
-- This ensures the next auto-generated ID will be 21.
ALTER TABLE course
    ALTER COLUMN id RESTART WITH 21;
ALTER TABLE student
    ALTER COLUMN id RESTART WITH 21;
ALTER TABLE users
    ALTER COLUMN id RESTART WITH 21;
ALTER TABLE enrollment
    ALTER COLUMN id RESTART WITH 21;
