package models

import slick.driver.PostgresDriver.simple._
import slick.jdbc.meta.MTable
import play.api.libs.json.Json
import play.api.libs.functional.syntax._

import schemas.{ContactsSchema, Person}

object Contacts extends DBManager {

  lazy val contacts = TableQuery[ContactsSchema]

  db withSession { implicit session =>
    if (MTable.getTables("contacts").list.isEmpty) {
      contacts.ddl.create
    }
  }

  def exists(id: Int): Boolean = {
    db withSession { implicit session => !contacts.filter(_.id === id).list.isEmpty }
  }

/*  def get(id: Int): Option[Person] = {
    db withSession { implicit session => {
        if (exists(id)) {
          Some(contacts.filter(_.id === id))
        } else {
          None
        }
      }
    }
  }
*/

  def insert(name: String, address: String, age: Int): Unit = {
    val person = Person(name, address, age)

    db withSession { implicit session =>
      contacts += person
    }
  }

  def insert(person: Person): Unit = {
    db withSession { implicit session =>
      contacts += person
    }
  }

  def list: List[Person] = {
    db withSession { implicit session =>
      contacts.list
    }
  }

  def delete(id: Int): Unit = {
    db withSession { implicit session =>
      if (exists(id)) {
        contacts.filter(_.id === id).delete
      }
    }
  }

}