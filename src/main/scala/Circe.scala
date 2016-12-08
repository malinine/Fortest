package Circe

import java.util.Date

import io.circe.generic.auto._
import io.circe.parser._
import io.circe._
import io.circe.literal._
import io.circe.generic.JsonCodec
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
 // println(parse(rawJson).right.get).deepMerge(parse(rawJson).right.get))
  //Json.fromJsonObject(parse(rawJson).right.get)
  println(parse(rawJson).right.get.deepMerge(parse("""{"NickName":"A"}""").right.get))
  //cursor.last.
//  val a1: Json = json"""[{"id": 1}, {"id": 2}, {"id": 3}]"""
//  val a2: Json = json"""[{"id": 4}, {"id": 5}, {"id": 6}]"""

//  println(a1.deepMerge(a2))
//
//  val jmerge =  for {
//    a1s <- a1.asArray;
//    a2s <- a2.asArray
//  } yield Json.fromValues(a1s++a2s)
//  println(jmerge)

  println("##### trim field #####")
  val trimName: ACursor = cursor.downField("firstName").withFocus(_.mapString(_.trim))
  println(trimName.top.get)

  println("#### Nested case class ####")
   case class Address(street: String, city: String)
   case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
   case class SimplePerson(name: String, address: Address, children: List[Child])


  val personObj = SimplePerson("joe",Address("Bulevard","Helsinki"),List(Child("Mazy",3,Option(new Date()))))
  println(personObj.asJson)


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
      "age": 5
    },
    {
      "name": "Mazy",
      "age": 3
    }
  ]
}"""

  //println(decode[SimplePerson](testJson))




}

object Hierachy extends App {
  sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
  case class Animals(animals: List[Animal])

//  val animals =  Animals(List(Fish(2.2),Dog("bobby")))
//  println(animals.asJson)
  val dog = ("pluto")
  println(dog.asJson)
  decode[Animal](dog.asJson.toString())
}

