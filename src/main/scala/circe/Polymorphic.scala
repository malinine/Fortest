package circe

import io.circe.parser._
import io.circe._


object PolymorphicUsingSemiauto extends App {

  import io.circe.generic.semiauto._

  sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal

  val rawJson = """
    {
      "Fish" : {
        "weight" : 2.2
      }
    } """

  implicit val amimalDecoder: Decoder[Animal] = deriveDecoder
  implicit val animalEncoder: Encoder[Animal] = deriveEncoder

  val fish = decode[Animal](rawJson)

}

object PolymophicUsingJsonCodec extends App {

  import io.circe.generic.JsonCodec

  @JsonCodec sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
  object Animal

  val rawJson = """
  {
    "Dog" : {
      "name" : "poppy"
    }
  } """

  val dog = decode[Animal](rawJson)

}
