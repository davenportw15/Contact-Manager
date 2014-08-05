package models.schemas

import scala.slick.driver.PostgresDriver.simple._
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Person(name: String, address: String, age: Int, id: Option[Int] = None)

class ContactsSchema(tag: Tag) extends Table[Person](tag, "contacts") {

	def name = column[String]("name", O.NotNull)

	def address = column[String]("address", O.NotNull)

	def age = column[Int]("age", O.NotNull)

	def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.NotNull)

	def * = (name, address, age, id.?) <> (Person.tupled, Person.unapply)
}