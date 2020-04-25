package com.example.udf

import org.apache.spark.sql.{QueryTest, Row}
import org.apache.spark.sql.test.SharedSparkSession
import org.apache.spark.sql.functions.callUDF

class WordCountTest extends QueryTest with SharedSparkSession {

  import testImplicits._

  test("test WordCount UDF") {

    val df = Seq(
      "Hello this is my favourite test",
      "This is cool",
      "Doing things right and doing the right thing").toDF("input_col")

    val wordCount = new WordCount().call _
    spark.udf.register("wordCount", wordCount)

    val out_df = df.withColumn("count", callUDF("wordCount", $"input_col"))

    checkAnswer(out_df.select("count"), Seq(Row(6), Row(3), Row(8)))

    assert(3 == out_df.count())

  }
}
