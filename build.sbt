name := "Aetherial"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "4.1.1_135",
  "com.typesafe" % "config" % "1.2.0",
  "org.tpolecat" %% "doobie-core" % "0.8.8",
  "org.tpolecat" %% "doobie-hikari" % "0.8.8",
  "org.postgresql" % "postgresql"% "42.2.14"
)