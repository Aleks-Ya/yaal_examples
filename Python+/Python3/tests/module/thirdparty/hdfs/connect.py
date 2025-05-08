from hdfs import InsecureClient

client = InsecureClient('http://localhost:50070', user='ann')

client.write('model.json', b'abc')

# List files
fnames = client.list('/')
print(fnames)
