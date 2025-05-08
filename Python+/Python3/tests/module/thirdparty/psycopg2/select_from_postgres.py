# Work with PostgresQL
#
# Run Postgres:
# "docker run --rm -it --network host -e POSTGRES_USER=pguser -e POSTGRES_PASSWORD=pgpass postgres:latest"
#
# Check connection:
# "PGPASSWORD=pgpass psql -h localhost -p 5432 -U pguser"

import psycopg2

# Database connection parameters
db_params = {
    'dbname': 'postgres',
    'user': 'pguser',
    'password': 'pgpass',
    'host': 'localhost',
    'port': '5432'
}

# Connect to the database
conn = psycopg2.connect(**db_params)
cursor = conn.cursor()

# Check if the "greetings" table exists
check_table_sql = """
SELECT EXISTS (
    SELECT FROM information_schema.tables 
    WHERE table_name = 'greetings'
);
"""
cursor.execute(check_table_sql)
table_exists = cursor.fetchone()[0]

# If the table doesn't exist, create it and insert a sample greeting
if not table_exists:
    create_table_sql = """
    CREATE TABLE greetings (
        id SERIAL PRIMARY KEY,
        message TEXT NOT NULL
    );
    """
    cursor.execute(create_table_sql)

    insert_sql = """
    INSERT INTO greetings (message) VALUES ('Hello, World!');
    """
    cursor.execute(insert_sql)

    # Commit the changes
    conn.commit()

# Perform the SELECT operation
select_sql = "SELECT message FROM greetings;"
cursor.execute(select_sql)

# Fetch results
results = cursor.fetchall()
for row in results:
    print(row[0])

# Close the cursor and connection
cursor.close()
conn.close()
