# Execute Python UnitTests by Maven

## Run unit tests

From CLI: `PYTHONPATH=src/main/python python -m unittest discover -p *_test.py -s src/test/python`

Run from CLI:

```shell
mvn exec:exec \
-Dexec.executable="pytest" \
-Dexec.environmentVariables="PYTHONPATH=src/main/python" \
-Dexec.args="-m unittest discover -p *_test.py -s src/test/python"
```

Run from the project: `mvn exec:exec`
