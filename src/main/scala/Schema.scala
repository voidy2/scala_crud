package main.scala.db

import java.util.Date
import java.sql.Timestamp
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import sjson.json._
import java.sql.Timestamp
import JsonSerialization._
import scala.reflect.BeanInfo
import jp.sf.orangesignal.csv.manager._
import java.io._

trait BaseModel extends KeyedEntity[Int] {
  var lastModeified = new Timestamp(System.currentTimeMillis)
  implicit def toOption[T](x:T) : Option[T] = Option(x)
}

@BeanInfo
case class Author(var firstName: String,
           var lastName: String,
           var email: String,
           val id: Int = 0) extends BaseModel {
  def this() = this("","","")

  def save = Library.authors.update(this)
  def create = Library.authors.insert(this)
  def destroy = Library.authors.delete(this.id)

  import Library.AuthorFormat
  def toJson = tojson(this)
  def fromJson(json: dispatch.json.JsValue) = fromjson[Author](json)

  def toCsv: String = {
    val writer = new StringWriter
    import scala.collection.JavaConversions._
    CsvManagerFactory.newCsvManager.save(List(this), classOf[Author]).to(writer)
    writer.toString
  }

  def fromCsv(csv: String) = {
    val list = new CsvColumnPositionMappingBeanManager()
      .load(classOf[Author])
      .column("firstName")
      .column("lastName")
      .column("email")
      .from(new StringReader(csv))
    list.get(0)
  }
}

// fields can be mutable or immutable 

object Library extends Schema with DefaultProtocol {
  val authors = table[Author]
  def authors_all = from(authors)(s => select(s) orderBy(s.id)).toList
  def authors_all_to_json = tojson(Library.authors_all)
  def authors_from_json(json: dispatch.json.JsValue) = fromjson[List[Author]](json)

  on(authors)(author => declare(
    author.id is (autoIncremented),
    author.email is (unique)
  ))

  implicit def AuthorFormat: Format[Author] = 
    asProduct4("firstName", "lastName", "email", "id")(Author)(Author.unapply(_).get)
}

