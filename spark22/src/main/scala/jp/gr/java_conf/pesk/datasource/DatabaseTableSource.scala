package jp.gr.java_conf.pesk.datasource

import jp.gr.java_conf.pesk.conf.JDBCSettings
import jp.gr.java_conf.pesk.model.User
import org.apache.spark.sql._
import org.apache.spark.sql.execution.datasources.jdbc.{JDBCOptions, JdbcUtils}

import scala.reflect.ClassTag

object DatabaseTableSource extends Serializable {

  def loadUserTable(tableName: String)()(
      implicit jdbcSettings: JDBCSettings,
      session: SparkSession,
      encoder: Encoder[User]): Dataset[User] = {

    import jp.gr.java_conf.pesk.binder.SparkRowBinder.implicits._

    session.read
      .jdbc(jdbcSettings.jdbcUrl, tableName, jdbcSettings.jdbcProperties)
      .map(_.bind)
  }

  def saveTable[T: ClassTag](tableName: String, persistData: Dataset[T])(
      implicit session: SparkSession,
      jdbcSettings: JDBCSettings,
      encoder: Encoder[T]): Unit = {

    val tempDf = persistData.toDF()
    val properties = Map(
      "user" -> jdbcSettings.user,
      "password" -> jdbcSettings.password
    )
    val jdbcOptions =
      new JDBCOptions(url = jdbcSettings.jdbcUrl, table = tableName, properties)

    // we assume column names in the database are upper case
    val schema = tempDf.toDF(tempDf.columns.map(_.toUpperCase): _*).schema

    JdbcUtils.saveTable(df = tempDf,
                        tableSchema = Some(schema),
                        isCaseSensitive = false,
                        options = jdbcOptions)
  }

}
