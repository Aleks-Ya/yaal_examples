from typing import Iterator

from databricks.sdk import WorkspaceClient
from databricks.sdk.service.compute import Library
from databricks.sdk.service.jobs import Job, Task, SparkJarTask, BaseJob
from databricks.sdk.service.jobs import JobCluster

w: WorkspaceClient = WorkspaceClient()


def test_get_job_by_id():
    job_id: int = 852050414675796
    job: Job = w.jobs.get(job_id)
    print(job)

    creator: str = job.creator_user_name
    print(f"\n\nCreator: {creator}")

    print("\n\nTask libraries:")
    tasks: list[Task] = job.settings.tasks
    for task in tasks:
        libraries: list[Library] = task.libraries
        for library in libraries:
            print(library)

    print("\n\nSpark Jar Task parameters:")
    tasks: list[Task] = job.settings.tasks
    for task in tasks:
        jar_task: SparkJarTask = task.spark_jar_task
        parameters: list[str] = jar_task.parameters
        print(parameters)

    clusters: list[JobCluster] = job.settings.job_clusters
    print(f"\n\nJob clusters: {clusters}")


def test_get_job_by_name():
    job_name: str = "cdas-dev-import-fairhub-study-drx"
    base_jobs: Iterator[BaseJob] = w.jobs.list(name=job_name)

    for base_job in base_jobs:
        job: Job = w.jobs.get(base_job.job_id)
        print("\n\n========= JOB =========")
        creator: str = job.creator_user_name
        print(f"Creator: {creator}")

        print("\nTask libraries:")
        tasks: list[Task] = job.settings.tasks
        for task in tasks:
            libraries: list[Library] = task.libraries
            for library in libraries:
                print(library)

        print("\nSpark Jar Task parameters:")
        tasks: list[Task] = job.settings.tasks
        for task in tasks:
            jar_task: SparkJarTask = task.spark_jar_task
            parameters: list[str] = jar_task.parameters
            print(parameters)

        clusters: list[JobCluster] = job.settings.job_clusters
        print(f"\nJob clusters: {clusters}")
