package circe

import io.circe.parser._
import io.circe._
import io.circe.generic.semiauto._

/*
  serializing polymorphic.
*/

object Polymorphic extends App {

  import io.circe.generic.auto._

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
  println(fish)

}
