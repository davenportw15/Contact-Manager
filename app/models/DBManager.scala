package models

import slick.driver.PostgresDriver.simple._

trait DBManager {
	// Supply your own Postgres credentials
    lazy val db = Database.forURL(
      "jdbc:postgresql:slickcontacts",
      "username",
      "password",
      driver="org.postgresql.Driver"
    )
}
