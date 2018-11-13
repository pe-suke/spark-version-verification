package jp.gr.java_conf.pesk.job

import jp.gr.java_conf.pesk.common.datasource.InMemoryDbInitializer
import jp.gr.java_conf.pesk.common.datasource.dao.UserDao
import jp.gr.java_conf.pesk.conf.SparkContextBuilder
import jp.gr.java_conf.pesk.datasource.DatabaseTableSource
import jp.gr.java_conf.pesk.model.{IncomingData, User}

object Main extends App with SparkContextBuilder with InMemoryDbInitializer {
  import DatabaseTableSource._
  import UserDao._
  import jp.gr.java_conf.pesk.bridger.Bridger.implicits._
  import jp.gr.java_conf.pesk.sink.UserSink._
  import sqlContext.implicits._

  initializeInMemoryDb()

  // Load data from HSQL
  val existing = loadUserTable(User.tableName).cache()

  // Incoming data
  private val incoming = Seq(IncomingData(1, "UPDATED NAME")).toDS()

  // Join and create inserting record and updating record
  private val persistData = existing bridge incoming

  // Insert new record
  saveTable(User.tableName, persistData.map(_._1))

  // Update record for expiring existing record
  update(persistData.map(_._2))

  // Assert
  private val set = retrieveAll()
  set.foreach(println(_))

  private val expired = set.filter(_.versionNum == 1).head
  assert(expired.id == 1)
  assert(expired.versionNum == 1)
  assert(expired.name == "TEST1")
  assert(expired.expired)

  private val inserted = set.filter(_.versionNum == 2).head
  assert(inserted.id == 1)
  assert(inserted.versionNum == 2)
  assert(inserted.name == "UPDATED NAME")
  assert(!inserted.expired)
}
