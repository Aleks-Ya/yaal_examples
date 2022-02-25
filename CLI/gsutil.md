# gsutil CLI (Goocle Cloud Storage)

List buckets:
```
gsutil ls
```
List objects in a bucket:
```
gsutil ls -r gs://iablokov-test-bucket/
```
Download object to a folder:
```
gsutil cp gs://iablokov-test-bucket/file1.txt ~/tmp/
```
