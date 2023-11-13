INSERT INTO role (created_on, created_by, updated_on, updated_by, description)
VALUES (current_timestamp, 1, null, null, 'Admin'),
       (current_timestamp, 1, null, null, 'Manager'),
       (current_timestamp, 1, null, null, 'Employee');

INSERT INTO users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, username, gender, password, confirm_password, phone_number)
VALUES (current_timestamp , 1, null, null, true, 1, 'Ferdinand', 'Conaboy', 'fconaboy0@mac.com', 'OTHER', 'oG6<?}Qn', 'oG6<?}Qn', '+447514234568');
INSERT INTO users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, username, gender, password, confirm_password, phone_number)
VALUES (current_timestamp , 1, null, null, true, 2, 'John', 'Kelly', 'johnkelly@example.com', 'OTHER', 'oG6<?}Qn', 'oG6<?}Qn', '+447514234568');

INSERT INTO projects (project_end_date, project_start_date, created_by, created_on, manager_id, updated_by,
                      updated_on, project_code, project_detail, project_name, project_status)
VALUES ('2023-11-30', '2023-11-10', 1, current_timestamp, 2, null, null, 'PR001','Introduction to Thymeleaf', 'Spring MVC - Thymeleaf','OPEN');




