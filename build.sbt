name := "zombie"

version := "0.1"

scalaVersion := "2.9.1"

resolvers += "Local SBT Repository" at 
	  "file://"+Path.userHome.absolutePath+"/.ivy2/local"

resolvers += "AFABL Repository" at "http://repo.afabl.org"

libraryDependencies += "org.afabl" %% "afabl" % "0.1-SNAPSHOT"
