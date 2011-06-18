resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

//Following means libraryDependencies += "com.github.siasia" %% "xsbt-web-plugin" % <sbt version>
libraryDependencies <<= (libraryDependencies, appConfiguration) {
  (deps, app) =>
  deps :+ "com.github.siasia" %% "xsbt-web-plugin" % app.provider.id.version
}

libraryDependencies ++= Seq(
  "org.fusesource.scalate" % "maven-scalate-plugin" % "1.3.2"
)
