package com.clarify.transform

import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.{Param, ParamMap}
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.functions.{col, udf}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset}


class WordCountTransformer(override val uid: String) extends Transformer {
  final val inputCol = new Param[String](this, "inputCol", "The input column")
  final val outputCol = new Param[String](this, "outputCol", "The output column");

  def setInputCol(value: String): this.type = set(inputCol, value)

  def setOutputCol(value: String): this.type = set(outputCol, value)

  def this() = this(Identifiable.randomUID("wordcounttransformer"))

  def copy(extra: ParamMap): WordCountTransformer = {
    defaultCopy(extra)
  }

  override def transformSchema(schema: StructType): StructType = {
    // Check that the input type is a string
    val idx = schema.fieldIndex($(inputCol))
    val field = schema.fields(idx)
    if (field.dataType != StringType) {
      throw new Exception(s"Input type ${field.dataType} did not match input type StringType")
    }
    // Add the return field
    schema.add(StructField($(outputCol), IntegerType, false))
  }

  def transform(df: Dataset[_]): DataFrame = {
    val wordcount = udf { in: String => in.split(" ").length }
    df.select(col("*"), wordcount(df.col($(inputCol))).as($(outputCol)))
  }
}
