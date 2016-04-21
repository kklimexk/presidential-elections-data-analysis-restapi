package controllers

import javax.inject._

import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.modules.reactivemongo.json._
import reactivemongo.api.Cursor
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.commands.bson.BSONCountCommand.{Count, CountResult}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.commands.bson.BSONCountCommandImplicits._

@Singleton
class UserController @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def jsonCollection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("users")
  def bsonCollection: BSONCollection = reactiveMongoApi.db.collection[BSONCollection]("users")

  import models._
  import models.UserFormats._

  def findAll = Action.async {
    val cursor: Cursor[User] = jsonCollection.find(Json.obj()).cursor[User]()

    val futureUsersList: Future[List[User]] = cursor.collect[List]()

    val futureUsersJsonArray: Future[JsValue] = futureUsersList.map { users =>
      Json.toJson(users)
    }
    futureUsersJsonArray.map { users =>
      Ok(users)
    }
  }

  def count = Action.async {
    val command = Count(BSONDocument.empty)
    val resultF: Future[CountResult] = bsonCollection.runCommand(command)
    resultF.map { res =>
      val numOfDocuments: Int = res.value
      Ok(Json.obj("numOfUsers" -> numOfDocuments))
    }
  }

}
