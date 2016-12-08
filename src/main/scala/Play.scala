package Play

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.language.implicitConversions

/**
  * Created by malinee on 1/12/2559.
  */

case class MyPerson(firstName: String, lastName: String, age: Int)
//object MyPerson {
  //implicit val MyPersonFormat = Json.format[MyPerson]
//}

object Play extends App {
  implicit val MyPersonFormat = Json.format[MyPerson]
  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  val parseJson: JsValue = Json.parse(rawJson)
  println("##### Parse JSON #####")
  println(parseJson)

  println("##### Case Class #####")
  val personObj = parseJson.as[MyPerson]
  println(personObj)

  println("##### Case Class to JSON #####")
  println(Json.stringify(parseJson))

  println("##### Remove field #####")
  println(parseJson.as[JsObject] - "age" - "lastName")

  println("##### Add field #####")
  println(parseJson.as[JsObject] + ("nickname"->JsString("mike")))

  println("##### Transform field #####")
  val fname = __.json.update(
    (__ \ 'name).json.copyFrom( (__ \ 'firstName).json.pick )
  ) andThen ((__ \ 'firstName).json.prune)
  println(parseJson.transform(fname).get)


  println("##### Trim Value #####")
  val jsonTrim = (__ \ 'firstName ).json.update(
    __.read[JsString].map{case JsString(x) => JsString(x.trim)}
  )
  println(parseJson.transform(jsonTrim).get)


  val json: JsValue = Json.obj(
    "name" -> "Watership Down",
    "location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
    "residents" -> Json.arr(
      Json.obj(
        "name" -> "Fiver",
        "age" -> 4,
        "role" -> JsNull
      ),
      Json.obj(
        "name" -> "Bigwig",
        "age" -> 6,
        "role" -> "Owsla"
      )
    )
  )

  println("##### Traversing #####")
  println((json \ "residents")(0).get)
  println(((json \ "residents")(0) \ "age").get)
  println((parseJson \ "age").get)


  val objToJson = Json.toJson(personObj)
  println(objToJson)


  println("##### Extract nested class #####")
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
      "birthdate": "2004-09-04T18:06:22Z"
    },
    {
      "name": "Mazy",
      "age": 3
    }
  ]
}
    """

  println("##### Extract nested class #####")
  case class Person(name: String, address: Address, children: List[Child])
  case class Address(street: String, city: String)
  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])

  case class SimplePerson(name: String, address: Address, children: List[Child])

//  implicit val ChildRead: Reads[Child] = (
//    (__ \ "name").read[String] and
//      (__ \ "age").read[Int] and
//       (__ \ "birthdate").readNullable[java.util.Date]
//    )(Child)
//
//  implicit val AddressRead: Reads[Address] = (
//    (__ \ "street").read[String] and
//      (__ \ "city").read[String]
//    )(Address)
//
//  implicit val SimplePersonRead: Reads[SimplePerson] = (
//    (__ \ "name").read[String] and
//      (__ \ "address").read[Address] and
//        (__ \ "children").read[List[Child]]
//    )(SimplePerson)

  implicit val childReads = Json.reads[Child]
  implicit val addressReads = Json.reads[Address]
  implicit val simplePersonReads = Json.reads[SimplePerson]

  println(Json.parse(testJson).as[SimplePerson])
  val jsonToModel = Json.parse(testJson).as[SimplePerson]
  println(jsonToModel)

//  implicit val childWrites = new Writes[Child]{
//    override def writes(child: Child): JsValue = Json.obj(
//      "name" -> child.name,
//      "age" -> child.age,
//      "birthdate" -> child.birthdate
//    )
//  }
//
//  implicit val addressWrites = new Writes[Address]{
//    override def writes(addr: Address): JsValue = Json.obj(
//      "street" -> addr.street,
//      "city" -> addr.city
//    )
//  }
//
//  implicit val simplePersonWrites = new Writes[SimplePerson]{
//    override def writes(person: SimplePerson): JsValue = Json.obj(
//      "name" -> person.name,
//      "address" -> person.address,
//      "children" -> person.children
//    )
//  }

  implicit val childWrites = Json.writes[Child]
  implicit val addressWrites = Json.writes[Address]
  implicit val simplePersontWrites = Json.writes[SimplePerson]

  val modelToJson = Json.toJson(jsonToModel)
  println(modelToJson)


}

sealed trait Animal
case class Dog(name: String) extends Animal
case class Fish(weight: Double) extends Animal
case class Animals(animals: List[Animal])

object Hierachy extends App {

  import julienrf.json.derived


  implicit val fooOWrites: OWrites[Animal] = derived.flat.owrites((__ \ "type").write[String])
  implicit val reads: Reads[Animal] = derived.flat.reads[Animal]((__ \ "type").read[String])


  val AnimalObj = Dog("pluto")
  println(Json.toJson(AnimalObj))
  println(Json.toJson(AnimalObj).as[Animal])

}

object Leonard extends App {

  import io.leonard.TraitFormat.{ traitFormat, caseObjectFormat }
  import play.api.libs.json.Json.format


  val doggy = Dog("Woof!")
  val animalFormat = traitFormat[Animal] << format[Dog] << format[Fish]
  val doggyJson = animalFormat.writes(doggy)
  println(animalFormat.writes(doggy))

  //val ss = Json.parse("""{"s":"Woof!","type":"Dog"}""")
  val animal1: Animal = animalFormat.reads(doggyJson).get
  println(animal1)





}
