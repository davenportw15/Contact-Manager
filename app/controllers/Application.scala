package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

// Models
import models.Contacts
import models.schemas.Person


object Application extends Controller {

  // Implicits
  implicit val personToJson = Json.writes[Person]

  // Forms
  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "age" -> number,
      "id" -> ignored[Option[Int]](None)
    )(Person.apply)(Person.unapply) 
  )

  def index = Action {
    Redirect(routes.Application.contacts)
  }

  def contacts = Action {
    Ok(views.html.contacts())
  }

  def newContact = Action {
    Ok(views.html.newContact(userForm))
  }

  def addContact = Action { implicit request =>
    val data = userForm.bindFromRequest.data
    Contacts.insert(data("name"), data("address"), data("age").toInt)
    Redirect(routes.Application.contacts)
  }

  def deleteContact(id: Int) = Action { implicit request =>
    Contacts.delete(id)
    Redirect(routes.Application.contacts)
  }

  // JSON API

  def jsonContacts = Action {
    Ok(Json.toJson(Contacts.list))
  }

  def jsonNewContact = Action(parse.json) { implicit request =>
    val name = (request.body \ "name").as[String]
    val address = (request.body \ "address").as[String]
    val age = (request.body \ "age").as[Int]
    Contacts.insert(name, address, age)
    Ok(Json.toJson(Map("status" -> true))).withHeaders(CONTENT_TYPE -> "application/json")
  }

}