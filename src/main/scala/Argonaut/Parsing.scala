package Argonaut

import argonaut.{Json, Parse}

import scalaz._, Scalaz._
import argonaut._, Argonaut._


object Parsing extends App {

  /* === parse valid json === */
  val rawJson: String = """
      {
        "firstName":"Leonard ",
        "lastName":"Nimoy",
        "age":81
      } """

  val result  = Parse.parseOption(rawJson)
  println(result)

}
