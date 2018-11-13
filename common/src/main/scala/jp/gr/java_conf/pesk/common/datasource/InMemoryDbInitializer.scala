package jp.gr.java_conf.pesk.common.datasource

import javax.sql.DataSource

import com.zaxxer.hikari.HikariDataSource
import jp.gr.java_conf.pesk.conf.DatabaseSettings
import org.flywaydb.core.Flyway
import scalikejdbc.{ConnectionPool, DataSourceConnectionPool}

trait InMemoryDbInitializer extends DatabaseSettings {

  private lazy val dataSource: DataSource = {
    val hikariDs = new HikariDataSource()
    hikariDs.setJdbcUrl(jdbcSettings.jdbcUrl)
    hikariDs.setUsername(jdbcSettings.user)
    hikariDs.setPassword(jdbcSettings.password)
    hikariDs
  }

  private def initializeConnectionPool(dataSource: DataSource): Unit = {
    ConnectionPool.singleton(new DataSourceConnectionPool(dataSource))
  }

  private def migrate(): Unit = {
    val flyway = Flyway.configure().dataSource(dataSource).load
    flyway.migrate()
  }

  def initializeInMemoryDb(): Unit = {
    initializeConnectionPool(dataSource)
    migrate()
  }

}
