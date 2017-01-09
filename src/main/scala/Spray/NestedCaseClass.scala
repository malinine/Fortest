package Spray

import java.time.Instant
import java.time.format.DateTimeFormatter

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import spray.json._


object NestedCaseClass extends App {


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
          "age": 3,
          "birthdate": "2007-10-09T18:06:22Z"
        }
      ]
    }
  """

  case class Address(street: String, city: String)
  case class Child(name: String, age: Int, birthdate: Option[DateTime])
  case class SimplePerson(name: String, address: Address, children: List[Child])

  object SimplePersonFormat extends DefaultJsonProtocol {

    implicit object DateJsonFormat extends RootJsonFormat[DateTime] {

      val formatter = ISODateTimeFormat.dateTimeNoMillis

      override def write(obj: DateTime) = JsString(formatter.print(obj))

      override def read(json: JsValue): DateTime = json match {
        case JsString(s) => formatter.parseDateTime(s)
        case _ => throw new DeserializationException("Error info you want here ...")
      }
    }
  }

  val person = rawJson.parseJson.convertTo[SimplePerson]
  println(person)

}
