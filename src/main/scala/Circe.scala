package Circe

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.{ACursor, HCursor, Json}
import io.circe.literal._

/**
  * Created by malinee on 1/12/2559.
  */

case class MyPerson(firstName: String, lastName: String, age: Int)
object Circe extends App {

  //implicit val myPersonDecoder: Decoder[MyPerson] = deriveDecoder
 // implicit val myPersonEncoder: Encoder[MyPerson] = deriveEncoder

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

  val cursor: HCursor = (parseFoo.right.get).hcursor
  println("##### Remove field #####")
  val delField = cursor.downField("age").delete
  println(delField.top.get)

  println("##### Add field #####")

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



}

object Hierachy extends App {
  sealed trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal
  case class Animals(animals: List[Animal])

  val animals =  Animals(List(Fish(2.2),Dog("bobby")))
  println(animals.asJson)
  val dog = ("pluto")
  println(dog.asJson)
  //de(dog.asJson)
}

