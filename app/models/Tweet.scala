package models

import org.joda.time.DateTime
import twitter4j.GeoLocation

case class Tweet(
                  crawledDate: DateTime,
                  tweetId: Long,
                  createdAt: DateTime,
                  text: String,
                  inReplyToStatusId: Long,
                  inReplyToUserId: Long,
                  source: String,
                  isRetweet: Boolean,
                  geoLocation: Option[GeoLocation],
                  retweetCount: Int,
                  favouriteCount: Int,
                  hashtags: List[String],
                  userId: Long
                )

object TweetFormats {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import DateTimeFormats._

  implicit val geoLocationRead: Reads[GeoLocation] =
    (JsPath \ "geoLocation").readNullable[GeoLocation].map { geoLocationOpt =>
      new GeoLocation(geoLocationOpt.get.getLatitude, geoLocationOpt.get.getLongitude)
    }

  implicit val geoLocationWrite: Writes[GeoLocation] = new Writes[GeoLocation] {
    def writes(geolocation: GeoLocation): JsValue = JsString(geolocation.toString)
  }

  val tweetReads: Reads[Tweet] = (
      (JsPath \ "crawledDate").read[DateTime] and
      (JsPath \ "_id").read[Long] and
      (JsPath \ "createdAt").read[DateTime] and
      (JsPath \ "text").read[String] and
      (JsPath \ "inReplyToStatusId").read[Long] and
      (JsPath \ "inReplyToUserId").read[Long] and
      (JsPath \ "source").read[String] and
      (JsPath \ "isRetweet").read[Boolean] and
      (JsPath \ "geoLocation").readNullable[GeoLocation] and
      (JsPath \ "retweetCount").read[Int] and
      (JsPath \ "favouriteCount").read[Int] and
      (JsPath \ "hashtags").read[List[String]] and
      (JsPath \ "userId").read[Long]
    )(Tweet.apply _)

  val tweetWrites: Writes[Tweet] = (
      (JsPath \ "crawledDate").write[DateTime] and
      (JsPath \ "_id").write[Long] and
      (JsPath \ "createdAt").write[DateTime] and
      (JsPath \ "text").write[String] and
      (JsPath \ "inReplyToStatusId").write[Long] and
      (JsPath \ "inReplyToUserId").write[Long] and
      (JsPath \ "source").write[String] and
      (JsPath \ "isRetweet").write[Boolean] and
      (JsPath \ "geoLocation").writeNullable[GeoLocation] and
      (JsPath \ "retweetCount").write[Int] and
      (JsPath \ "favouriteCount").write[Int] and
      (JsPath \ "hashtags").write[List[String]] and
      (JsPath \ "userId").write[Long]
    )(unlift(Tweet.unapply))

  implicit val userFormat: Format[Tweet] =
    Format(tweetReads, tweetWrites)
}
