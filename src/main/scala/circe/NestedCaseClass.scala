package circe

import java.time.Instant

import io.circe.parser._
import io.circe.{Decoder, Encoder}


object NestedCaseClass extends App {

  import io.circe.generic.auto._
  import cats.syntax.either._

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
          "age": 3,
          "birthdate": "2007-10-09T18:06:22Z"
        }
      ]
    }
  """

  case class Address(street: String, city: String)
  case class Child(name: String, age: Int, birthdate: Option[java.time.Instant])
  case class SimplePerson(name: String, address: Address, children: List[Child])

  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str => Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant") }

  val person = decode[SimplePerson](rawJson)

}
