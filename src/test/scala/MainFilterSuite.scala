package com.example

import org.scalatra._
import org.scalatra.test.specs._

class MainFilterSuite extends ScalatraSpecification {

  "index page test" should {
    "status => 200" in {
      get("/") {
        status mustEqual 200
      }
    }
  }
}
