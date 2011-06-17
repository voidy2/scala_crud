name := "Restfull CRUD Web Application"

version := "1.0"

resolvers ++= Seq(
  "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases",
  "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots",
  "Morphia Repo at Google Code" at "http://morphia.googlecode.com/svn/mavenrepo"
)

libraryDependencies ++= Seq(
  "org.squeryl" %% "squeryl" % "0.9.4-RC7",
  "com.h2database" % "h2" % "1.2.127",
  "net.debasishg" %% "sjson" % "0.12",
  "junit" % "junit" % "4.8" % "test",
  "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT",
  "org.scalatra" %% "scalatra-scalate" % "2.0.0-SNAPSHOT",
  "ch.qos.logback" % "logback-classic" % "0.9.25",
  "org.fusesource.scalate" % "scalate-core" % "1.5.0-SNAPSHOT",
  "org.mortbay.jetty" % "jetty" % "6.1.26" % "test",
  "com.google.code.morphia" % "morphia" % "1.00-SNAPSHOT",
  "javax.servlet" % "servlet-api" % "2.5"
)

//jetty-run
seq(WebPlugin.webSettings :_*)

scalacOptions += "-deprecation"

scalaVersion := "2.9.0"

crossPaths := false

fork := true

javaOptions += "-Xmx1G"

ivyLoggingLevel := UpdateLogging.Full

logLevel := Level.Info
