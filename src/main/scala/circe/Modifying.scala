package circe

import cats.syntax.either._
import io.circe.{ACursor, Cursor, HCursor, Json}
import io.circe.parser._


object Modifying extends App {

  val rawJson: String = """
    {
      "name": "joe",
      "address": {
        "street": "Bulevard",
        "city": "Helsinki"
      },
      "children": [
        {
          "name": "Mary",
          "age": 5,
          "birthdate": "2004-09-04T18:06:22Z"
        },
        {
          "name": "Mazy",
          "age": 3
        }
      ]
    }
  """

  val doc = parse(rawJson).getOrElse(Json.Null)
  val cursor  = doc.hcursor
  val reversedNameCursor =  cursor.downField("name").withFocus(_.mapString(_.reverse))
  println(reversedNameCursor.top)

}
