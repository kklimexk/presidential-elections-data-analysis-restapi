package controllers

import javax.inject._

import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.modules.reactivemongo.json._
import reactivemongo.api.Cursor
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kklimek on 20.04.16.
  */
@Singleton
class UserController @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("users")

  import models._
  import models.UserFormats._

  def findAll = Action.async {
    val cursor: Cursor[User] = collection.find(Json.obj()).cursor[User]()

    val futureUsersList: Future[List[User]] = cursor.collect[List]()

    val futureUsersJsonArray: Future[JsValue] = futureUsersList.map { users =>
      Json.toJson(users)
    }
    futureUsersJsonArray.map { users =>
      Ok(users)
    }
  }

}
