set -e
set -x
cd /home/aleks/pr/home/novartis
git status
aws s3 cp s3://kata-bucket-template-empty/nv.bundle ~/tmp/nv.bundle
git bundle verify ~/tmp/nv.bundle
echo "cd $PWD"
echo "git pull ~/tmp/nv.bundle main"
echo "git push"
