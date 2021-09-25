/**
 * Connect to a HDFS cluster with Kerberos.
 * 1. Run Hadoop cluster ("Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop2+/HdfsKerberosCluster")
 * 2. Copy files from the cluster: "./copy_from_docker.sh"
 * <p>
 * Known errors:
 * 1. Receive timed out
 * Message: "org.apache.hadoop.security.KerberosAuthException: Login failure for user: client@HADOOPCLUSTER.LOCAL from keytab /tmp/client.keytab javax.security.auth.login.LoginException: Receive timed out"
 * Reason: KDC in Docker container is not available.
 * Solution: invoke "sudo ./update_hosts.sh" script.
 * 2. Checksum failed
 * Message: "org.apache.hadoop.security.KerberosAuthException: Login failure for user: client_super@HADOOPCLUSTER.LOCAL from keytab /tmp/client_super.keytab javax.security.auth.login.LoginException: Checksum failed"
 * Reason: /tmp/client_super.keytab is obsolete
 * Solution: invoke "./copy_from_docker.sh"
 * 3. Not a super user
 * Message: "User client_super is not a super user (non-super user cannot change owner)."
 * Reason: HDFS nodes have no "client_super:supergroup" user
 * Solution: create "client_super" user in group "supergroup" on HDFS nodes
 */
package hdfs.cluster.kerberos;