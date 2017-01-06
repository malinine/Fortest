package circe

import io.circe.parser._
import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._


object Polymorphic extends App {


  @JsonCodec sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
  object Animal

  val rawJson = """
    {
      "Fish" : {
        "weightd" : 2.2
      }
    } """

  //implicit val amimalDecoder: Decoder[Animal] = deriveDecoder
  //implicit val animalEncoder: Encoder[Animal] = deriveEncoder

  val fish = decode[Animal](rawJson)
  println(fish)

}
