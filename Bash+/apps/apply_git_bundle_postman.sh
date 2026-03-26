set -e
set -x
cd /home/aleks/pr/home/postman
git status
aws s3 cp s3://kata-bucket-template-empty/postman.bundle ~/tmp/postman.bundle
git bundle verify ~/tmp/postman.bundle
git pull ~/tmp/postman.bundle main
git push
