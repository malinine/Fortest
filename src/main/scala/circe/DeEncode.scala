package circe

import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import io.circe.parser.decode

object Encoding extends App {

  val enc = List(1, 2, 3).asJson
  val dec = enc.as[List[Int]]

}

object SemiAutomatic extends App {

  import io.circe.generic.semiauto._

  case class MyPerson(firstName: String, lastName: String, age: Int)
  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  implicit val myPersonDecoder: Decoder[MyPerson] = deriveDecoder
  implicit val myPersonEncoder: Encoder[MyPerson] = deriveEncoder

  val myPerson = decode[MyPerson](rawJson)

}

object JsonCodec extends App {

  import io.circe.generic.JsonCodec

  @JsonCodec case class MyPerson(firstName: String, lastName: String, age: Int)
  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  val myPerson = decode[MyPerson](rawJson)

}

object FullyAutomatic extends App {

  import io.circe.generic.auto._

  case class MyPerson(firstName: String, lastName: String, age: Int)
  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  val myPerson = decode[MyPerson](rawJson)

}
