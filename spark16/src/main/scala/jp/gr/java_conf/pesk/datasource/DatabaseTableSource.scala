package jp.gr.java_conf.pesk.datasource

import jp.gr.java_conf.pesk.conf.JDBCSettings
import jp.gr.java_conf.pesk.model.User
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils
import org.apache.spark.sql.{Dataset, Encoder, SQLContext}

import scala.reflect.ClassTag

object DatabaseTableSource extends Serializable {

  def loadUserTable(tableName: String)()(
      implicit jdbcSettings: JDBCSettings,
      sqlContext: SQLContext,
      encoder: Encoder[User]): Dataset[User] = {

    import jp.gr.java_conf.pesk.binder.SparkRowBinder.implicits._
    import sqlContext.implicits._

    sqlContext.read
      .jdbc(jdbcSettings.jdbcUrl, tableName, jdbcSettings.jdbcProperties)
      .map(_.bind)
      .toDS()
  }

  def saveTable[T: ClassTag](tableName: String, persistData: Dataset[T])(
      implicit sqlContext: SQLContext,
      jdbcSettings: JDBCSettings,
      encoder: Encoder[T]): Unit = {

    JdbcUtils.saveTable(persistData.toDF(),
                        jdbcSettings.jdbcUrl,
                        tableName,
                        jdbcSettings.jdbcProperties)
  }

}
