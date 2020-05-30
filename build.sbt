name := "Aetherial"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "4.1.1_135",
  "com.typesafe" % "config" % "1.2.0",
)

resolvers ++= Seq(JCenterRepository)

enablePlugins(JavaAppPackaging)