package databricks

import com.databricks.sdk.WorkspaceClient
import com.databricks.sdk.mixin.ClustersExt
import com.databricks.sdk.service.compute.ListClustersRequest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ClustersIT extends AnyFlatSpec with Matchers {
  private val w = new WorkspaceClient
  private val clusters: ClustersExt = w.clusters

  it should "list clusters" in {
    val request = new ListClustersRequest
    val clusterList = clusters.list(request)
    clusterList.forEach(println)
  }
}