package jp.gr.java_conf.pesk.binder

import jp.gr.java_conf.pesk.model.User
import org.apache.spark.sql.Row

object SparkRowBinder {
  object implicits {
    implicit class SparkRowToUser(val row: Row) {
      def bind: User = {
        User(
          id = row.getAs[Int](User.idCol),
          versionNum = row.getAs[Int](User.versionNumCol),
          name = row.getAs[String](User.nameCol),
          expired = row.getAs[Boolean](User.expiredCol)
        )
      }
    }

  }
}
