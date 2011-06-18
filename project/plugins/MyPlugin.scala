import sbt._
import Keys._

object MyPlugin extends Plugin {

  override lazy val settings = Seq(commands += hello)

  lazy val hello = 
    Command.args("hello", "<args>") { (state, args) =>
      println(args)
      state
    }
}


