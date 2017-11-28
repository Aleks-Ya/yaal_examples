# ALTER command

## Add column to table
Simple:
`ALTER TABLE t1 ADD b varchar`
Auto-generated ID:
`ALTER TABLE t1 ADD ID INT IDENTITY`

## Drop column
`ALTER TABLE t1 DROP COLUMN b`

## Rename table
`sp_rename 'old_table_name', 'new_table_name'`
