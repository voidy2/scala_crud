package com.example

import org.scalatra._
import java.net.URL
import scalate.ScalateSupport
import main.scala.db._
import org.squeryl.PrimitiveTypeMode._
import sjson.json._

class MainFilter extends ScalatraFilter with ScalateSupport {

def startDatabaseSession():Unit = {
  import org.squeryl.Session
  import org.squeryl.SessionFactory
  import org.squeryl.adapters._
 
  Class.forName("org.h2.Driver")
  SessionFactory.concreteFactory = Some(() =>
    Session.create(
      java.sql.DriverManager.getConnection("jdbc:h2:mem:db1"),
        new H2Adapter))
}

  
    get("/") {
    <html>
      <body>
        <h1>Hello, world!!!</h1>
        <pre>{
          startDatabaseSession()
          transaction {
            Library.create
            val a1 = Author("do", "Mr", "foo@bar.com").create
            val a2 = Author("do", "Ms", "hoge@bar.com").create
            val json = Library.authors_all_to_json
            //Library.authors_from_json(json)
            //a1.toCsv
            //a1.toJson
            val csv = Helper.toCsv(List(a1))
            csv
            Helper.fromCsv[Author]("aa,bb,cc,1")
            a2
          }
        }
        </pre>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
/*
  notFound {
    // If no route matches, then try to render a Scaml template
    val templateBase = requestPath match {
      case s if s.endsWith("/") => s + "index"
      case s => s
    }
    val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".scaml"
    servletContext.getResource(templatePath) match {
      case url: URL => 
        contentType = "text/html"
        templateEngine.layout(templatePath)
      case _ => 
        filterChain.doFilter(request, response)
    } 
  }
  */
}


