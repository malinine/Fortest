package circe

import io.circe.{Decoder, Encoder}
import io.circe.parser._


object PolymorphicLists extends App {


  import io.circe.generic.auto._
  import io.circe.generic.semiauto._

  sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
  case class Animals(animals: List[Animal])

  val rawJson = """
    {
      "animals" : [
        {
          "Fish" : {
            "weight" : 2.2
          }
        },
        {
          "Dog" : {
            "name" : "bobby"
          }
        }
      ]
    } """

  implicit val amimalDecoder: Decoder[Animal] = deriveDecoder
  implicit val animalEncoder: Encoder[Animal] = deriveEncoder

  val animalList = decode[Animals](rawJson)

}

