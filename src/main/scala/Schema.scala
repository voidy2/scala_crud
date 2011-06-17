package main.scala.db

import java.util.Date
import java.sql.Timestamp
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

class Author(val id: Long,
           val firstName: String,
           val lastName: String,
           val email: Option[String]) {
  def this() = this(0,"","",Some(""))
}

// fields can be mutable or immutable 

class Book(val id: Long, 
            var title: String,
           var authorId: Long,
           var coAuthorId: Option[Long]) {

  def this() = this(0,"",0,Some(0L))
}

class Borrowal(val id: Long,
                val bookId: Long,
                val borrowerAccountId: Long,
                val scheduledToReturnOn: Date,
                val returnedOn: Option[Timestamp],
                val numberOfPhonecallsForNonReturn: Int)

object Library extends Schema {
  val authors = table[Author]("AUTHORS")

  val books = table[Book]

  val borrowals = table[Borrowal]
}

