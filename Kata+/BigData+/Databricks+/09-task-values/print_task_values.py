name = dbutils.jobs.taskValues.get(taskKey = "task_a", key = "age", default = 42, debugValue = 0)
age = dbutils.jobs.taskValues.get(taskKey = "task_a", key = "name", default = "Jane Doe")
print(f'Name: {name}')
print(f'Age: {age}')
