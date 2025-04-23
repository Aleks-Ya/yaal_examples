from typing import Iterator

from databricks.sdk import WorkspaceClient
from databricks.sdk.service.compute import ClusterDetails, S3StorageInfo
from databricks.sdk.service.jobs import Run, JobCluster, RunTask, BaseRun

w: WorkspaceClient = WorkspaceClient()


def test_get_job_run():
    job_run_id: int = 516947917366561
    job_run: Run = w.jobs.get_run(job_run_id)
    print(f"\n\nRun: {job_run}")

    clusters: list[JobCluster] = job_run.job_clusters
    cluster: JobCluster = clusters[0]
    print(f"\n\nCluster: {cluster}")

    tasks: list[RunTask] = job_run.tasks
    task: RunTask = tasks[0]
    cluster_id: str = task.cluster_instance.cluster_id
    cluster_details: ClusterDetails = w.clusters.get(cluster_id)
    print(f"\n\nCluster details: {cluster_details}")

    s3_storage_info: S3StorageInfo = cluster_details.cluster_log_conf.s3
    print(f"\n\nS3 Storage Info: {s3_storage_info}")

    destination: str = s3_storage_info.destination
    s3_log_path: str = f"{destination}/{cluster_id}"
    print(f"\n\nS3 Log Path: {s3_log_path}")


def test_list_job_runs():
    job_id: int = 852050414675796
    job_runs: Iterator[BaseRun] = w.jobs.list_runs(job_id=job_id)
    for job_run in job_runs:
        print(f"\nRun: {job_run}")
        print(f"Run start time: {job_run.start_time}")
        print(f"Run end time: {job_run.end_time}")
