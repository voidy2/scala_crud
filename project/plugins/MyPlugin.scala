import sbt._
import Keys._

import org.fusesource.scalate.{TemplateSource, Binding, TemplateEngine}
import org.fusesource.scalate.servlet.ServletRenderContext
import org.fusesource.scalate.util.{FileResourceLoader, IOUtil}

object MyPlugin extends Plugin {

  override lazy val settings = Seq(commands += hello)

  lazy val hello = 
    Command.args("hello", "<args>") { (state, args) =>
      println(args)
      state
    }
}

