package jp.gr.java_conf.pesk.common.datasource.dao

import jp.gr.java_conf.pesk.model.User
import scalikejdbc.{AutoSession, DBSession, _}

object UserDao extends Serializable {
  def retrieveAll()(implicit dbSession: DBSession = AutoSession): List[User] = {
    import jp.gr.java_conf.pesk.common.datasource.binder.SqlRowBinder.implicits._

    sql"""
         SELECT * FROM USER
       """.map(_.bind).list().apply()
  }

  def updateBatch(users: List[User])(implicit dbSession: DBSession = AutoSession): Unit = {
    val params: Seq[Seq[(Symbol, Any)]] =
      users.map { model =>
        Seq(
          'id -> model.id,
          'version -> model.versionNum,
          'expired -> model.expired
        )
      }

    sql"""
        UPDATE
          USER
        SET
          EXPIRED = {expired}
        WHERE
          ID = {id} AND VERSIONNUM = {version}
      """.batchByName(params: _*).apply()
  }

}
