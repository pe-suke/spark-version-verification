package jp.gr.java_conf.pesk.conf

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}

trait DatabaseSettings {
  private val config: Config = ConfigFactory.load()

  implicit val jdbcSettings = JDBCSettings(
    jdbcUrl = config.getString("jdbcSettings.jdbcUrl"),
    jdbcProperties = {
      val properties = new Properties()
      properties.setProperty("user", config.getString("jdbcSettings.user"))
      properties.setProperty("password", config.getString("jdbcSettings.password"))
      properties
    }
  )

}

case class JDBCSettings(jdbcUrl: String, jdbcProperties: Properties) {
  def user: String = jdbcProperties.getProperty("user")
  def password: String = jdbcProperties.getProperty("password")
}
