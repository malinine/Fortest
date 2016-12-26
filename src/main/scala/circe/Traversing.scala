package circe
import java.time.Instant

import cats.syntax.either._
import io.circe._
import io.circe.parser._

object Traversing extends App{

  val rawJson: String = """
    {
      "name": "joe",
      "address": {
        "street": "Bulevard",
        "city": "Helsinki"
      },
      "children": [
        {
          "name": "Mary",
          "age": 5,
          "birthdate": "2004-09-04T18:06:22Z"
        },
        {
          "name": "Mazy",
          "age": 3
        }
      ]
    }
  """

  val doc = parse(rawJson).getOrElse(Json.Null)
  val cursor: HCursor = doc.hcursor

  val name = cursor.downField("name").as[String]
  println(name)

  val firstChild = cursor.downField("children").downN(0).as[Json]
  println(firstChild)

  val firstChildAge = cursor.downField("children").downN(0).downField("age").as[Int]
  println(firstChildAge)

  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str => Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant") }
  val firstChildBirthdate = cursor.downField("children").downN(0).downField("birthdate").as[java.time.Instant]
  println(firstChildBirthdate)

  val json = parse(rawJson).right.get

  val listage = cursor.downField("children").focus.flatMap(_.asArray).get.flatMap(_.cursor.get[Int]("age").toOption)
  println(listage)

}
