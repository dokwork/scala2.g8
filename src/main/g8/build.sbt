lazy val `$name$` = (project in file("."))
  .settings(		
		organization := "$organization$",
		scalaVersion := "$scala_version$",
		scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Ypartial-unification"),
		libraryDependencies ++= Seq(
			$if(cats.truthy)$
			"org.typelevel" %% "cats-core" % "$cats_version$",
			$endif$
			$if(cats_effect.truthy)$
		  "org.typelevel" %% "cats-effect" % "$cats_effect_version$",
			$endif$
			$if(fs2.truthy)$
			"co.fs2" %% "fs2-core" % "$fs2_version$",
			$endif$
			// tests:
			"org.scalatest" %% "scalatest" % "3.0.0" % "test"
		)
 )
	.settings(
    coverageMinimum := 90,
    coverageFailOnMinimum := true
  )

