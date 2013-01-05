import AssemblyKeys._ // put this at the top of the file

assemblySettings

jarName in assembly := "zombie.jar"

mainClass in assembly := Some("pureslick.SimpleGame")

name := "zombie"

version := "0.1"

scalaVersion := "2.9.2"

seq(lwjglSettings: _*)

resolvers += "Local SBT Repository" at
	  "file://"+Path.userHome.absolutePath+"/.ivy2/local"

resolvers += "AFABL Repository" at "http://repo.afabl.org"

libraryDependencies += "org.afabl" %% "afabl" % "0.1-SNAPSHOT"

libraryDependencies += "com.github.scopt" %% "scopt" % "2.1.0"