ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "$scala_version$"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"

lazy val compilerOptions = Seq(
  "-encoding",
  "utf-8",
  "-target:jvm-1.8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-unused",
  "-Xfatal-warnings",
  "-language:higherKinds"
)

lazy val dependencies = new {
  val versions = new {
		$if(cats.truthy)$
    val cats          = "$cats_version$"
		$endif$
		$if(cats_effect.truthy)$
    val catsEffect    = "$cats_effect_version$"
    val stCatsEffect  = "1.4.0"
		$endif$
		$if(fs2.truthy)$
    val fs2           = "$fs2$"
		$endif$
    val scalatest     = "$scalatest_version$" 
  }

	$if(cats.truthy)$
  val cats          = "org.typelevel"     %% "cats-core"                     % versions.cats
	$endif$
	$if(cats_effect.truthy)$
  val catsEffect    = "org.typelevel"     %% "cats-effect"                   % versions.catsEffect
  val stCatsEffect  = "org.typelevel"     %% "cats-effect-testing-scalatest" % versions.stCatsEffect
	$endif$
	$if(fs2.truthy)$
  val fs2           = "co.fs2"            %% "fs2-core"                      % versions.fs2
	$endif$
  val scalatest     = "org.scalatest"     %% "scalatest"                     % versions.scalatest

  val runtime = Seq(
	$if(cats.truthy)$ cats, $endif$
	$if(cats_effect.truthy)$ catsEffect, $endif$
	$if(fs2.truthy)$ fs2, $endif$
  )
  val test    = Seq(
    scalatest, 
		$if(cats_effect.truthy)$ stCatsEffect $endif$
  ).map(_ % "test")
}


lazy val `$name$` = (project in file("."))
  .settings(
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= dependencies.runtime ++ dependencies.test,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
 	  addCompilerPlugin(
      ("org.typelevel" %% "kind-projector" % "0.13.2").cross(CrossVersion.full)
    ),
    $if(for_opensource.truthy)$
		coverageMinimumStmtTotal := 90,
		coverageFailOnMinimum := true
    $endif$
)
