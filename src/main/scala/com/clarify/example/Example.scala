package com.clarify.example


import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Example {

  def main(args: Array[String]): Unit = {

    // Running in simple standalone mode for POC and providing all the cores
    // Production mode master will be set to YARN or MESOS // Distributed resource manager scheduler
    val sparkConf =
    new SparkConf()
      .setMaster("local[*]")
      .setAppName("example")

    val sparkSession: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()


    val someDF = sparkSession.read
      .option("header", "true")
      .option("inferSchema", "false")
      .csv("/Users/alvin/PycharmProjects/mlflow-demo/data/airbnb-cleaned-mlflow.csv")

    someDF.show()

  }
}