package jp.gr.java_conf.pesk.conf

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkSessionBuilder {
  implicit lazy val session = buildSession(createConf)

  def createConf: SparkConf = {
    new SparkConf()
      .setMaster("local[2]")
      .setAppName("test")
      .set("spark.ui.enabled", "false")
      .set("spark.app.id", "test")
      .set("spark.sql.shuffle.partitions", "2")
      .set("spark.ui.showConsoleProgress", "false")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.driver.maxResultSize", "256m")
      .set("spark.driver.memory", "512m")
      .set("spark.executor.memory", "512m")
  }

  def buildSession(conf: SparkConf): SparkSession = {
    SparkSession.builder().config(conf).getOrCreate()
  }

}
