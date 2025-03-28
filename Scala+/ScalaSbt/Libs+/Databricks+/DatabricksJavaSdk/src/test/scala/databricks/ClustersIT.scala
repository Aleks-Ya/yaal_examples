package databricks

import com.databricks.sdk.WorkspaceClient
import com.databricks.sdk.mixin.ClustersExt
import com.databricks.sdk.service.compute.ListClustersRequest
import org.scalatest.flatspec.AnyFlatSpec

class ClustersIT extends AnyFlatSpec {
  private val w = new WorkspaceClient
  private val clusters: ClustersExt = w.clusters

  it should "list clusters" in {
    val request = new ListClustersRequest
    val clusterList = clusters.list(request)
    clusterList.forEach(println)
  }
}