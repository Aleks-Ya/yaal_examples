-- List all roles
SELECT rolname FROM pg_roles;

-- Create a login role (user)
CREATE USER john;

-- Create a group role
CREATE ROLE mary;

-- Create a group role (group)
CREATE ROLE peter;

-- Drop a login role (user)
DROP USER john;