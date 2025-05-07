from databricks.sdk import WorkspaceClient
from databricks.sdk.service.jobs import Job, Task, TaskDependency

w: WorkspaceClient = WorkspaceClient()


def test_get_task_type():
    job_id: int = 674092143404945
    job: Job = w.jobs.get(job_id)
    tasks: list[Task] = job.settings.tasks
    for task in tasks:
        print(f"{task.task_key}")
        is_python_script_task: bool = task.spark_python_task is not None
        print(f"Is Python script task: {is_python_script_task}")
        is_condition_task: bool = task.condition_task is not None
        print(f"Is Condition task: {is_condition_task}")
        is_run_job_task: bool = task.run_job_task is not None
        print(f"Is Run Job task: {is_run_job_task}")
        assert sum([is_python_script_task, is_condition_task, is_run_job_task]) == 1  # only one is True
        print()


def test_list_task_dependencies():
    job_id: int = 674092143404945
    job: Job = w.jobs.get(job_id)
    tasks: list[Task] = job.settings.tasks
    for task in tasks:
        dependencies: list[TaskDependency] = task.depends_on
        task_keys: list[str] = [dependency.task_key for dependency in dependencies]
        print(f"{task.task_key} depends on {task_keys}")


def test_list_task_dependencies_of_job_run_type():
    job_id: int = 674092143404945
    job: Job = w.jobs.get(job_id)
    tasks: list[Task] = job.settings.tasks
    task_dict: dict[str, Task] = {task.task_key: task for task in tasks}
    for task in tasks:
        dependencies: list[TaskDependency] = task.depends_on
        job_run_dependencies: list[TaskDependency] = []
        for dependency in dependencies:
            dependency_task_key: str = dependency.task_key
            dependency_task: Task = task_dict[dependency_task_key]
            if dependency_task.run_job_task is not None:
                job_run_dependencies.append(dependency)
        task_keys: list[str] = [dependency.task_key for dependency in job_run_dependencies]
        print(f"{task.task_key} depends on a Job Run tasks {task_keys}")
