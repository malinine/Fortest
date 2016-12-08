package Spray

import spray.json._
import fommil.sjs.FamilyFormats._
import net.liftweb.json.Serialization._
import net.liftweb.json._
/**
  * Created by malinee on 1/12/2559.
  */
case class MyPerson(firstName: String, lastName: String, age: Int)

object Spray extends App {

  val rawJson: String = """{"firstName":"Leonard","lastName":"Nimoy","age":81}"""
  val jsonAst = rawJson.parseJson
  println("##### Parse JSON #####")
  println(jsonAst)

  val objPerson =  jsonAst.convertTo[MyPerson]
  println("##### Extract to Case Class #####")
  println(objPerson)
  println(objPerson.firstName)

  val objToJson = objPerson.toJson
  println("##### Case Class to JSON #####")
  println(objToJson)

  println("##### Get Field #####")
  println(jsonAst.asJsObject.getFields("age"))

  val fruits = """{ "fruits": [
    {
      "name": "apple",
      "color": "red"
    },
    {
      "name": "banana",
      "color": "yellow"
    }
    ]
  }"""

  println("##### Traversing #####")
 val fruitsAST = fruits.parseJson
  println(fruitsAST.asJsObject.getFields("fruits"))
  println(fruitsAST.asJsObject.getFields("name"))

  //fruitsAST \ ""



}

