package controllers

import java.sql.ResultSet

class RsIterator(rs: ResultSet) extends Iterator[ResultSet] {
  def hasNext: Boolean = rs.next()

  def next(): ResultSet = rs
}
