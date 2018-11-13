package jp.gr.java_conf.pesk.common.datasource.binder

import jp.gr.java_conf.pesk.model.User
import scalikejdbc.WrappedResultSet

object SqlRowBinder {
  object implicits {
    implicit class SqlRowToUser(val row: WrappedResultSet) {
      def bind: User = {
        User(
          id = row.int(User.idCol),
          versionNum = row.int(User.versionNumCol),
          name = row.string(User.nameCol),
          expired = row.boolean(User.expiredCol)
        )
      }
    }
  }

}
