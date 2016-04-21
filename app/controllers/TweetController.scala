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
class TweetController @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def jsonCollection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("tweets")
  def bsonCollection: BSONCollection = reactiveMongoApi.db.collection[BSONCollection]("tweets")

  import models._
  import models.TweetFormats._

  def findAll = Action.async {
    val cursor: Cursor[Tweet] = jsonCollection.find(Json.obj()).cursor[Tweet]()

    val futureTweetsList: Future[List[Tweet]] = cursor.collect[List]()

    val futureTweetsJsonArray: Future[JsValue] = futureTweetsList.map { tweets =>
      Json.toJson(tweets)
    }
    futureTweetsJsonArray.map { tweets =>
      Ok(tweets)
    }
  }

  def count = Action.async {
    val command = Count(BSONDocument.empty)
    val resultF: Future[CountResult] = bsonCollection.runCommand(command)
    resultF.map { res =>
      val numOfDocuments: Int = res.value
      Ok(Json.obj("numOfTweets" -> numOfDocuments))
    }
  }

}
