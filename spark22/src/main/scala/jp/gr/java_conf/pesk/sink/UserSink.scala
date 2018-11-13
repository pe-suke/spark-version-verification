package jp.gr.java_conf.pesk.sink

import jp.gr.java_conf.pesk.common.datasource.InMemoryDbInitializer
import jp.gr.java_conf.pesk.common.datasource.dao.UserDao
import jp.gr.java_conf.pesk.conf.JDBCSettings
import jp.gr.java_conf.pesk.model.User
import org.apache.spark.sql.{Dataset, SparkSession}

object UserSink extends Serializable with InMemoryDbInitializer {

  def update(expired: Dataset[User])(implicit session: SparkSession,
                                     jDBCSettings: JDBCSettings): Unit = {
    expired.foreachPartition { i =>
      initializeInMemoryDb()

      UserDao.updateBatch(i.toList)
    }
  }
}
