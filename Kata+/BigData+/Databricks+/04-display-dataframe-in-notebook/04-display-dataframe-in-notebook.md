# 04-display-dataframe-in-notebook

## Task
Use built-in `display()` function in a Scala Notebook to show a DataFrame in table format.

## Setup
1. Create notebook
2. Command:
```scala
val data = Seq(("John", 25), ("Peter", 35))
val df = data.toDF("name", "age")
display(df)
```

## Cleanup
1. Delete Notebook
