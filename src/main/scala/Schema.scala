package main.scala.db

import java.util.Date
import java.sql.Timestamp
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import sjson.json._
import java.sql.Timestamp
import JsonSerialization._
import scala.reflect._
import sjson.json.Serializer._

trait BaseModel extends KeyedEntity[Int] {
  var lastModeified = new Timestamp(System.currentTimeMillis)
}

@BeanInfo 
case class Author(var firstName: String,
          var lastName: String,
          var email: Option[String],
          val id: Int = 0) extends BaseModel {
  def this() = this("","",Some(""))

  def save = Library.authors.update(this)
  def create = Library.authors.insert(this)
  def destroy = Library.authors.delete(this.id)

  //import Library.AuthorFormat
  def toJson = JSON.out(this)
  def fromJson(bytes: Array[Byte]) = JSON.in(bytes)
}

// fields can be mutable or immutable 

object Library extends Schema with DefaultProtocol {
  val authors = table[Author]
  def authors_all = from(authors)(s => select(s) orderBy(s.id)).toList
  def authors_all_to_json = tojson(Library.authors_all)

  on(authors)(author => declare(
    author.id is (autoIncremented),
    author.email is (unique)
  ))

}

