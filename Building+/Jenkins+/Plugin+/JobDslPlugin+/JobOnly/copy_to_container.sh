workspace=/tmp/job-only-workspace
container=jenkins2
docker exec $container rm -rf $workspace
docker exec $container mkdir -p $workspace
docker cp jobs ${container}:${workspace}
docker cp examples ${container}:${workspace}
docker cp gradle ${container}:${workspace}
