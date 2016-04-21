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

@Singleton
class TweetController @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("tweets")

  import models._
  import models.TweetFormats._

  def findAll = Action.async {
    val cursor: Cursor[Tweet] = collection.find(Json.obj()).cursor[Tweet]()

    val futureTweetsList: Future[List[Tweet]] = cursor.collect[List]()

    val futureTweetsJsonArray: Future[JsValue] = futureTweetsList.map { tweets =>
      Json.toJson(tweets)
    }
    futureTweetsJsonArray.map { tweets =>
      Ok(tweets)
    }
  }

}
