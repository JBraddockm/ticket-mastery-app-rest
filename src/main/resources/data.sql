INSERT INTO role (created_on, created_by, updated_on, updated_by, description)
VALUES (current_timestamp, 1, null, null, 'Admin'),
       (current_timestamp, 1, null, null, 'Manager'),
       (current_timestamp, 1, null, null, 'Employee');

insert into users (created_on, created_by, updated_on, updated_by, is_enabled, role_id, first_name, last_name, user_name, gender, password, confirm_password, phone_number)
values (current_timestamp , 1, null, null, false, 1, 'Ferdinand', 'Conaboy', 'fconaboy0@mac.com', 'OTHER', 'oG6<?}Qn', 'oG6<?}Qn', '+44751423456');
