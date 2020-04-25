package com.example.transform

import org.apache.spark.sql.functions.callUDF
import org.apache.spark.sql.{QueryTest, Row}
import org.apache.spark.sql.test.SharedSparkSession


class WordCountTransformerTest extends QueryTest with SharedSparkSession {

  import testImplicits._

  test("test WordCount Transformer") {

    val df = Seq(
      "Hello this is my favourite test",
      "This is cool",
      "Doing things right and doing the right thing").toDF("input_col")

    val tr = new WordCountTransformer()
    tr.setInputCol("input_col")
    tr.setOutputCol("count")

    val out_df = tr.transform(df)

    checkAnswer(out_df.select("count"), Seq(Row(6), Row(3), Row(8)))

    assert(3 == out_df.count())

  }
}