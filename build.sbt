name := "spark-demo"

version := "0.1"

scalaVersion := "2.11.12"


val sparkVersion = "2.4.1"

scalacOptions := Seq("-unchecked", "-deprecation")

// remove version-specific scala dirs
crossPaths := false


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-hive" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided
)
