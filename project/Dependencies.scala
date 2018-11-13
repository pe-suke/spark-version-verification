import sbt._

object Dependencies {
  lazy val spark16Version = "1.6.1"
  lazy val spark16Core = "org.apache.spark" %% "spark-core" % spark16Version
  lazy val spark16Sql = "org.apache.spark" %% "spark-sql" % spark16Version
  lazy val spark16Libs = Seq(spark16Core, spark16Sql)

  private lazy val spark22Version = "2.2.2"
  private lazy val spark22Core = "org.apache.spark" %% "spark-core" % spark22Version
  private lazy val spark22Sql = "org.apache.spark" %% "spark-sql" % spark22Version
  private lazy val spark22Libs = Seq(spark22Core, spark22Sql)

  lazy val hsqldb = "org.hsqldb" % "hsqldb" % "2.4.0"
  lazy val hikariCp = "com.zaxxer" % "HikariCP" % "2.5.1"
  lazy val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % "2.5.0"
  lazy val flyway = "org.flywaydb" % "flyway-core" % "5.2.1"

  lazy val typesafeConfig = "com.typesafe" % "config" % "1.3.2"

  lazy val commonDependencies = Seq(hsqldb, hikariCp, scalikejdbc, flyway, typesafeConfig)
  lazy val spark16Dependencies = spark16Libs
  lazy val spark22Dependencies = spark22Libs

}
