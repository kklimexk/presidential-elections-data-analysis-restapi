package models

import java.util.Date

import twitter4j.GeoLocation

case class Tweet(
                  crawledDate: Date,
                  tweetId: Long,
                  createdAt: Date,
                  text: String,
                  inReplyToStatusId: Long,
                  inReplyToUserId: Long,
                  source: String,
                  isRetweet: Boolean,
                  geoLocation: GeoLocation,
                  retweetCount: Int,
                  favouriteCount: Int,
                  hashtags: List[String],
                  userId: Long
                )

object TweetFormats {

  import play.api.libs.json.Json

  //implicit val geoLocationFormat = Json.format[GeoLocation]
  //implicit val tweetFormat = Json.format[Tweet]
}
