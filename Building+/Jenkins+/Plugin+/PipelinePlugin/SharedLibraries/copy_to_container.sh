set -x
set -e

container_git_repo=/tmp/shared-libraries-git-repo
container=jenkins2

docker exec $container rm -rf $container_git_repo
docker exec $container mkdir -p $container_git_repo
docker exec -w $container_git_repo $container git init
docker cp vars ${container}:${container_git_repo}
docker cp src ${container}:${container_git_repo}
docker cp test ${container}:${container_git_repo}
docker exec -w $container_git_repo $container git add .
docker exec -w $container_git_repo $container git config --global user.name TstScript
docker exec -w $container_git_repo $container git config --global user.email tst@mail.com
docker exec -w $container_git_repo $container git commit -m InitialCommit
