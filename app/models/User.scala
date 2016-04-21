package models

import org.joda.time.DateTime

case class User(
                 crawledDate: DateTime,
                 userId: Long,
                 createdAt: DateTime,
                 name: String,
                 screenName: String,
                 numOfTweets: Int,
                 numOfFollowers: Int,
                 language: String,
                 location: Option[String],
                 followersIds: List[Long]
               )

object UserFormats {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val dateTimeRead: Reads[DateTime] =
    (JsPath \ "$date").read[Long].map { dateTime => new DateTime(dateTime) }

  implicit val dateTimeWrite: Writes[DateTime] = new Writes[DateTime] {
    def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString)
  }

  val userReads: Reads[User] = (
        (JsPath \ "crawledDate").read[DateTime] and
        (JsPath \ "_id").read[Long] and
        (JsPath \ "createdAt").read[DateTime] and
        (JsPath \ "name").read[String] and
        (JsPath \ "screenName").read[String] and
        (JsPath \ "numOfTweets").read[Int] and
        (JsPath \ "numOfFollowers").read[Int] and
        (JsPath \ "language").read[String] and
        (JsPath \ "location").readNullable[String] and
        (JsPath \ "followersIds").read[List[Long]]
    )(User.apply _)

  val userWrites: Writes[User] = (
        (JsPath \ "crawledDate").write[DateTime] and
        (JsPath \ "_id").write[Long] and
        (JsPath \ "createdAt").write[DateTime] and
        (JsPath \ "name").write[String] and
        (JsPath \ "screenName").write[String] and
        (JsPath \ "numOfTweets").write[Int] and
        (JsPath \ "numOfFollowers").write[Int] and
        (JsPath \ "language").write[String] and
        (JsPath \ "location").writeNullable[String] and
        (JsPath \ "followersIds").write[List[Long]]
    )(unlift(User.unapply))

  implicit val userFormat: Format[User] =
    Format(userReads, userWrites)
}
