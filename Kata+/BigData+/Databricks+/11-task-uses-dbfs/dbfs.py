ls = dbutils.fs.ls("/")
print(f"ls /:\n{ls}")

tmp_dir = "/tmp/work-with-dbfs"
print(f"Creating dir: {tmp_dir}")
dbutils.fs.mkdirs(tmp_dir)

data_file = tmp_dir + "/data.txt"
print(f"Save text to a file: {data_file}")
dbutils.fs.put(data_file, "Hello, Databricks!", True)

info_file = tmp_dir + "/info.txt"
print(f"Copy file {data_file} to {info_file}")
dbutils.fs.cp(data_file, info_file)
content = dbutils.fs.head(info_file, 25)
print(f"File content:\n{content}")

print(f"Deleting file: {data_file}")
dbutils.fs.rm(data_file)

print(f"Deleting non-empty dir: {tmp_dir}")
dbutils.fs.rm(tmp_dir, True)
