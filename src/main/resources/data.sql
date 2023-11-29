INSERT INTO role (created_on, created_by, updated_on, updated_by, description, is_deleted)
VALUES (current_timestamp, 1, null, null, 'Admin', false),
       (current_timestamp, 1, null, null, 'Manager', false),
       (current_timestamp, 1, null, null, 'Employee', false);

INSERT INTO users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, username,
                   gender, password, confirm_password, phone_number, is_deleted)
VALUES (current_timestamp, 1, null, null, true, 1, 'Ferdinand', 'Conaboy', 'fconaboy0@mac.com', 'OTHER', '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC',
        '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC', '+447514234568', false);
INSERT INTO users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, username,
                   gender, password, confirm_password, phone_number, is_deleted)
VALUES (current_timestamp, 1, null, null, true, 2, 'John', 'Kelly', 'johnkelly@example.com', 'OTHER', '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC',
        '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC', '+447514234568', false);
INSERT INTO users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, username,
                   gender, password, confirm_password, phone_number, is_deleted)
VALUES (current_timestamp, 1, null, null, true, 3, 'James', 'Brook', 'jamesbrook@example.com', 'MALE', '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC',
        '$2a$12$n7If7QzAkZXh5IEgHAIqi.Xu5BWtaazYT.IoZqnKVWiMvD52jnhVC', '+447514234568', false);



INSERT INTO projects (project_end_date, project_start_date, created_by, created_on, manager_id, updated_by,
                      updated_on, project_code, project_detail, project_name, project_status, is_deleted)
VALUES ('2023-11-30', '2023-11-10', 1, current_timestamp, 2, null, null, 'PR001', 'Introduction to Thymeleaf',
        'Spring MVC - Thymeleaf', 'OPEN', false);

INSERT INTO tasks (assigned_date, assigned_employee_id, created_by, created_on, project_id, updated_by, updated_on,
                   status, task_detail, task_subject, is_deleted)
VALUES ('2023-12-30', 3, 1, current_timestamp, 1, null, null, 'OPEN', 'Task Detail', 'Task Sample ONE', false);

INSERT INTO tasks (assigned_date, assigned_employee_id, created_by, created_on, project_id, updated_by, updated_on,
                   status, task_detail, task_subject, is_deleted)
VALUES ('2023-12-30', 3, 1, current_timestamp, 1, null, null, 'COMPLETED', 'Task Detail', 'Task Sample TWO', false);




