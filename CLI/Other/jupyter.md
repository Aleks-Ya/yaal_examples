# jupyter CLI

Install JupyterLab: `pip install jupyterlab`
Install Jupyter Notebook: `pip install notebook`

Run JupyterLab: `jupyter lab`
List installed kernels: `jupyter kernelspec list`
Install IJava kernel:
```
#Download and unpack ijava-1.3.0.zip
jupyter kernelspec install --user java
#Replace "@KERNEL_INSTALL_DIRECTORY@" with "/home/aleks/.local/share/jupyter/kernels/java"
```
Show config, data and runtime folder locations: `jupyter --paths`
Run Console with Java kernel: `jupyter console --kernel=java`
