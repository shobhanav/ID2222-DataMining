name := "assignment1"

organization := "se.kth.spark"

version := "1.0"

scalaVersion := "2.11.1"

//resolvers += Resolver.mavenLocal
resolvers += "Kompics Snapshots" at "http://kompics.sics.se/maven/snapshotrepository/"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.1" % "compile"
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.1" % "compile"
//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.0.1" % "compile"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.0.1" % "compile"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "2.0.1" % "compile"
libraryDependencies += "org.log4s" %% "log4s" % "1.3.3" % "provided"
libraryDependencies += "se.kth.spark" %% "lab1_lib" % "1.0-SNAPSHOT"

mainClass in assembly := Some("se.kth.spark.assignment1.Main")

run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
