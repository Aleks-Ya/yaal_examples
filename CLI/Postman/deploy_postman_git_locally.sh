set -e

cd /home/aleks/pr/home/io.github.yaal.PostmanGit

BEFORE=$(git rev-parse HEAD)
git pull -q --rebase cloned master
AFTER=$(git rev-parse HEAD)

if [ "$BEFORE" != "$AFTER" ]; then
  echo
  echo "NEW POSTMAN VERSION FOUND"
  echo
  git push -f
  flatpak-builder --force-clean --user --repo=repo --install build io.github.yaal.PostmanGit.yaml
else
  echo "NO POSTMAN UPDATES FOUND"
fi
