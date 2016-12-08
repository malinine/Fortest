name := "Fortest"

version := "0.0.4"

organization := "net.liftweb"

scalaVersion := "2.11.8"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "https://oss.sonatype.org/content/repositories/releases"
                )
resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"


seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.8",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.17.v20150415"  % "container,test",
    "org.eclipse.jetty" % "jetty-plus"          % "8.1.17.v20150415"  % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.specs2"        %% "specs2-core"        % "3.6.4"           % "test",
    "com.propensive"        %% "rapture" % "2.0.0-M6",
    "org.json4s"            %% "json4s-native"          % "3.4.1",
    "org.json4s"            %% "json4s-jackson"          % "3.4.1",
    "net.liftweb" %% "lift-json" % "2.6.2",
    "io.spray" %%  "spray-json" % "1.3.2",
    "com.github.fommil" %% "spray-json-shapeless" % "1.3.0",
    "com.typesafe.play" %% "play-json" % "2.5.0",
    "net.liftweb" %% "lift-json" % "2.6.2",
    "org.julienrf" %% "play-json-derived-codecs" % "3.3",
    "io.leonard" %% "play-json-traits" % "1.0.1"
  )
}

val circeVersion = "0.6.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
"io.circe" %% "circe-jawn" ,
"io.circe" %% "circe-literal",
"io.circe" %% "circe-optics"
).map(_ % circeVersion)

scalacOptions in Test ++= Seq("-Yrangepos")
