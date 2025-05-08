from databricks.sdk import WorkspaceClient
from databricks.sdk.service.compute import ListClustersFilterBy, ClusterSource, State, ClusterDetails

w: WorkspaceClient = WorkspaceClient()


def test_list_all_clusters():
    for c in w.clusters.list():
        print(c.cluster_name)


def test_list_job_clusters():
    job_clusters_filter: ListClustersFilterBy = ListClustersFilterBy(cluster_sources=[ClusterSource.JOB])
    for c in w.clusters.list(filter_by=job_clusters_filter):
        print(f"{c.cluster_name} - {c.cluster_source}")


def test_list_running_clusters():
    running_clusters_filter: ListClustersFilterBy = ListClustersFilterBy(cluster_states=[State.RUNNING])
    for c in w.clusters.list(filter_by=running_clusters_filter):
        print(f"{c.cluster_name} - {c.state}")


def test_get_cluster_by_name():
    cluster_name: str = "job-605116929334139-run-572977947923982-cluster-small"
    clusters: list[ClusterDetails] = [c for c in w.clusters.list() if c.cluster_name.lower() == cluster_name.lower()]
    for c in clusters:
        print(f"{c.cluster_name} - {c.cluster_id}")
