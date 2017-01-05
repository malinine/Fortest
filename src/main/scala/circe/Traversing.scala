package circe
import java.time.Instant

import cats.syntax.either._
import io.circe._
import io.circe.parser._

object Traversing extends App{

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
          "age": 8,
          "birthdate": "2004-09-04T18:06:22Z"
        },
        {
          "name": "Rosa",
          "age": 5,
          "birthdate": "2012-03-24T14:20:12Z"
        },
        {
          "name": "Mazy",
          "age": 3
        }
      ]
    }
  """

  val doc = parse(rawJson).getOrElse(Json.Null)

  // define HCursor with the focus at the document's root.
  val cursor: HCursor = doc.hcursor

  // move the focus to the field as specified and decode the field as a string.
  val name = cursor.downField("name").as[String]
  println(name)

  // Focus to JSON Array , move to the element as specified index and decode the field as JSON.
  val firstChild = cursor.downField("children").downN(0).as[Json]
  println(firstChild)

  val firstChildAge = cursor.downField("children").downN(0).downField("age").as[Int]
  println(firstChildAge)

  // Focus to a field that doesn't exist , then return error message and the history of operations.
  val nickname = cursor.downField("children").downN(0).downField("nickname").as[String]
  println(nickname)

  //=== Modifying JSON ===//

  println(cursor.downField("children").focus.flatMap(_.asArray).get.flatMap(_.cursor.get[Int]("age").toOption))
//  val reversedNameCursor =  cursor.downField("name").withFocus(_.mapString(_.reverse))
//  println(reversedNameCursor.top)
//
//  // Delete the focus
//  val delete = cursor.downField("address").delete.focus
//
//  // After deleted the element at the given index, then move the cursor to the first element in JSON Array.
//  val deleteGoFirst = cursor.downField("children").downN(1).deleteGoFirst.focus
//  println(deleteGoFirst)
//
//  // After deleted the element at the given index, then move the cursor to the next element in JSON Array.
//  val deleteGoRight = cursor.downField("children").downN(1).deleteGoRight.focus
//  println(deleteGoRight)
//
//  // After deleted the focus , then move the cursor to the specified field.
//  val deleteGoField = cursor.downField("children").deleteGoField("name").as[String]



}
