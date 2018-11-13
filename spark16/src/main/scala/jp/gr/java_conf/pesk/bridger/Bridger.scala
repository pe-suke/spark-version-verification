package jp.gr.java_conf.pesk.bridger

import jp.gr.java_conf.pesk.model.{IncomingData, User}
import org.apache.spark.sql.{Dataset, SQLContext}

object Bridger {
  object implicits {
    implicit class UserBridger(existing: Dataset[User]) {
      def bridge(incoming: Dataset[IncomingData])(
          implicit sqlContext: SQLContext): Dataset[(User, User)] = {

        import sqlContext.implicits._

        existing
          .as("ex")
          .joinWith(incoming.as("in"), $"ex.id" === $"in.id")
          .map {
            case (ex, in) =>
              val insert = User(id = ex.id,
                                versionNum = ex.versionNum + 1,
                                name = in.name,
                                expired = false)

              val expire = ex.copy(expired = true)
              (insert, expire)
          }
      }
    }
  }

}
