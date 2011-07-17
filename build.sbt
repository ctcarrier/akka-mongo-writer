name := "scalatra-mongodb-project"

version := "1.0"

scalaVersion := "2.9.0-1"

seq(WebPlugin.webSettings :_*)

libraryDependencies ++= Seq(
  "com.mongodb.casbah" %% "casbah" % "2.1.5.0",
  "org.slf4j" % "slf4j-nop" % "1.6.0" % "runtime",
  "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT",
  "org.scalatra" %% "scalatra-specs" % "2.0.0-SNAPSHOT" % "test",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "se.scalablesolutions.akka" % "akka-actor" % "1.1.3",
  "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
  "net.liftweb" %% "lift-json" % "2.4-M2",
  "org.squeryl" %% "squeryl" % "0.9.4",
  "postgresql" % "postgresql" % "8.4-701.jdbc4",
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.0.M2" % "test",
  "org.mortbay.jetty" % "servlet-api" % "3.0.20100224" % "provided"
)

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Web plugin repo" at "http://siasia.github.com/maven2",
  "Akka Repo" at "http://akka.io/repository",
  "repo.novus rels" at "http://repo.novus.com/releases/",
  "repo.novus snaps" at "http://repo.novus.com/snapshots/"
)

