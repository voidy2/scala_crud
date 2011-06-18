package main.scala.db

import java.util.Date
import java.sql.Timestamp
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

case class Author(var firstName: String,
           var lastName: String,
           var email: Option[String],
           val id: Int= 0) extends KeyedEntity[Int] {
  def this() = this("","",Some(""))

  def save = Library.authors.update(this)
}

// fields can be mutable or immutable 

object Library extends Schema {
  val authors = table[Author]
}

