set -e
echo "Creating Databricks job: '$1'"
if [ -z "$1" ]
then
  echo "Error: job name is missing"
  exit 1
fi
export POOL_ID=$(./get_instance_pool_id.sh)
cat databricks/file/DbfsApp.json | envsubst > /tmp/job.json
databricks jobs create --json @/tmp/job.json
