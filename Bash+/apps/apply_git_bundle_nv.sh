set -e
set -x
cd /home/aleks/pr/home/novartis
git status
aws s3 cp s3://kata-bucket-template-empty/nv.bundle ~/tmp/nv.bundle
git bundle verify ~/tmp/nv.bundle
git pull --rebase ~/tmp/nv.bundle main
git push
