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
import java.lang.reflect.ParameterizedType

trait BaseModel extends KeyedEntity[Int] {
}

@BeanInfo
case class Author(var firstName: String,
           var lastName: String,
           var email: String,
           val id: Int = 0) extends BaseModel {
  def this() = this("","","")

  def create = Library.authors.insert(this)
  def destroy = Library.authors.delete(this.id)

  def paramNameList = List("firstName", "lastName", "email", "id")

  import Library.AuthorFormat
  //ぶっちゃけこのへん使わないかも
  def toJson = tojson(this)
  def fromJson(json: dispatch.json.JsValue) = fromjson[Author](json)

}

// fields can be mutable or immutable 

object Library extends Schema with DefaultProtocol {

  val authors = table[Author]
  //このへんはhelperでいい気がしてきた
  def authors_all = from(authors)(s => select(s) orderBy(s.id)).toList
  //これもいらんかも
  //コメントでOK
  def authors_all_to_json = tojson(Library.authors_all)
  //これはいらない気がしてきた
  //コメントに書くとつかいやすい？
  def authors_from_json(json: dispatch.json.JsValue) = fromjson[List[Author]](json)

  on(authors)(author => declare(
    author.id is (autoIncremented),
    author.email is (unique)
  ))

  implicit def AuthorFormat: Format[Author] = 
    asProduct4("firstName", "lastName", "email", "id")(Author)(Author.unapply(_).get)

}


object Helper {

  def paramNameList(c: java.lang.Class[_]) = 
    c.getDeclaredFields.map(_.getName).filter(!_.startsWith("_")).toList

  def toCsv[T <: BaseModel](list: List[T], paramNames: Option[List[String]] = None)
    (implicit m: ClassManifest[T]): String = {
      val c = m.erasure.asInstanceOf[java.lang.Class[T]]
      val writer = new StringWriter
      import scala.collection.JavaConversions._
      val saveColumn = new CsvColumnPositionMappingBeanManager().save(list, c)
      paramNames.getOrElse(paramNameList(c)).foldLeft(saveColumn) { (l, p) => l.column(p) }.to(writer)
      writer.toString
  }

  def fromCsv[T <: BaseModel](csv: String, paramNames: Option[List[String]] = None)
    (implicit m: ClassManifest[T]) = {
      val c = m.erasure.asInstanceOf[java.lang.Class[T]]
      val loadColomn = new CsvColumnPositionMappingBeanManager().load(c)
      paramNames.getOrElse(paramNameList(c)).foldLeft(loadColomn) { (l, p) => l.column(p) }
        .from(new StringReader(csv))
  }

}

