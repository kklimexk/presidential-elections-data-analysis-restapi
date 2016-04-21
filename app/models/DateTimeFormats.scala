package models

import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.json._

/**
  * Reads datetime field ($date) from mongodb and writes datetime as a string to json
  */
object DateTimeFormats {
  implicit val dateTimeRead: Reads[DateTime] =
    (JsPath \ "$date").read[Long].map { dateTime => new DateTime(dateTime, DateTimeZone.UTC) }

  implicit val dateTimeWrite: Writes[DateTime] = new Writes[DateTime] {
    def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString)
  }
}
