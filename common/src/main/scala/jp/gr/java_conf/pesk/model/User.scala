package jp.gr.java_conf.pesk.model

case class User(id: Int, versionNum: Int, name: String, expired: Boolean)

object User {
  val tableName = "USER"
  val idCol = "ID"
  val versionNumCol = "VERSIONNUM"
  val nameCol = "NAME"
  val expiredCol = "EXPIRED"
}
