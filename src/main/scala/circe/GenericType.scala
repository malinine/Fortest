package circe

import io.circe.parser.decode

object GenericType extends App {

  import io.circe.generic.auto._

  case class Color(color: String)
  case class NamedList[A](name: String, items: List[A])

  val rawJson =
    """
      {
        "name":"My Favorite Color",
        "items":[
          {"color":"Blue"},
          {"color":"Yellow"}
        ]
      }"""

  val namedList = decode[NamedList[Color]](rawJson)

}
