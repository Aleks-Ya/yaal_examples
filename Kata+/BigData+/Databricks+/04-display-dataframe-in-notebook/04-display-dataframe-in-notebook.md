# 04-display-dataframe-in-notebook

## Task
Use built-in `display()` function in a Scala Notebook to show a DataFrame in table format.

## Prerequsites
1. A Workspace is created.

## Setup
1. Create a Notebook:
	2. Command:
	```scala
	val data = Seq(("John", 25), ("Peter", 35))
	val df = data.toDF("name", "age")
	display(df)
	```

## Cleanup
1. Delete the Notebook
