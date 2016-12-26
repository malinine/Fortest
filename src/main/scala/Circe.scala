package Circe


import cats.syntax.either._
import java.time.Instant
import io.circe.generic.auto._
import io.circe.parser._
import io.circe._

import io.circe.syntax._
import io.circe.optics.JsonPath._


/**
  * Created by malinee on 1/12/2559.
  */

case class MyPerson(firstName: String, lastName: String, age: Int)
object Circe extends App {



  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  println("##### Parse JSON #####")
  val parseFoo = parse(rawJson)
  println(parseFoo)

  println("##### Case Class #####")
  val obj = decode[MyPerson](rawJson)
  println(obj)

  println("##### Case Class to JSON #####")
  val s = (obj.right.get).asJson.noSpaces
  println(s)

  println("### Traversing ####")
  println(root.age.int.getOption(parse(rawJson).right.get).get)

  val cursor: HCursor = (parseFoo.right.get).hcursor
  println("##### Remove field #####")
  val delField = cursor.downField("age").delete
  println(delField.top.get)

  println("##### Add field #####")
  println(parse(rawJson).right.get.deepMerge(parse("""{"NickName":"A"}""").right.get))


  println("##### trim field #####")
  val trimName: ACursor = cursor.downField("firstName").withFocus(_.mapString(_.trim))
  println(trimName.top.get)


  println("#### Nested case class ####")
   case class Address(street: String, city: String)
   case class Child(name: String, age: Int, birthDate: Option[java.time.Instant])
   case class SimplePerson(name: String, address: Address, children: List[Child])

  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str => Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant") }


//  val personObj = SimplePerson("joe",Address("Bulevard","Helsinki"),List(Child("Mazy",3,Some(new Date()))))
//  println(personObj.asJson)
//
//
  val testJson =
    """
{ "name": "joe",
  "address": {
    "street": "Bulevard",
    "city": "Helsinki"
  },
  "children": [
    {
      "name": "Mary",
      "age": 5,
      "birthdate": "12345678L"
    },
    {
      "name": "Mazy",
      "age": 3
    }
  ]
}"""

  println(decode[SimplePerson](testJson))




}

object Hierachy extends App {
  sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
 // case class Animals(animals: List[Animal])

  //val animals =  Animals(List(Fish(2.2),Dog("bobby")))
  //val animals =Animals(Dog("444"))
    //println(animals.asJson)
  val dog = Dog("pluto")
  println(dog.asJson)

  val dogStr = """
    {
      "Dog" : {
        "name" : "444"
      }
    }
    """
  println(decode[Animal](dogStr))


}

