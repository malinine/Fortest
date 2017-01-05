package Lift


import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST.{JField, JObject, JValue}
import net.liftweb.json.Serialization.{read, write}
import net.liftweb.json


/**
  * Created by malinee on 1/12/2559.
  */
case class MyPerson(firstName: String, lastName: String, age: Int)

object Lift extends App {
  implicit val formats = net.liftweb.json.DefaultFormats

  val rawJson: String = """{"firstName":"Leonard ","lastName":"Nimoy","age":81}"""

  println("##### Parse JSON #####")
  val parseJson = parse(rawJson)


  println("##### Case Class #####")
  val obj = parse(rawJson).extract[MyPerson]
  println(obj)

  println("##### Case Class to JSON #####")
  println(Extraction.decompose(obj))

  println("##### Remove field #####")
  val b = parse(rawJson) transform {
    case JField("lastName",JString(x)) => JNothing
  }
  println(parse(compactRender(b)))

  println("##### Add field #####")
  val newJval: JValue =  ("sex" -> "M")
  println(parseJson.merge(newJval))


  println("##### Transform field #####")
  val newVal =   parseJson transform {
    case JField("firstName",x) => JField("name",x)
  }
  println(newVal)

  println("##### Trim value #####")
  val trimVal =   parseJson transform {
    case JField(z,JString(x)) => JField(z,JString(x.trim))
  }
  println(trimVal)


  println("##### Extract nested class #####")
  case class Address(street: String, city: String)
  case class Child(name: String, age: Int, birthdate: Option[java.time.Instant])
  case class SimplePerson(name: String, address: Address,children: List[Child])


  val testJson =parse("""
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
  }""")


  println(testJson.extract[SimplePerson])

  println((testJson \\ "children")(0))

  println("##### Flatten JSON #####")
  println(Extraction.flatten(testJson))

  println("##### UnFlatten JSON #####")
  println(Extraction.unflatten( Extraction.flatten(testJson)))




}

sealed trait Animal
case class Dog(name: String) extends Animal
case class Fish(weight: Double) extends Animal
case class Animals(animals: List[Animal])
object Hierachy extends App {


  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Dog], classOf[Fish])))
  // implicit val formats = Serialization.formats(FullTypeHints(List(classOf[Dog], classOf[Fish])))

//  //implicit val formats = net.liftweb.json.DefaultFormats + new JsonBoxSerializer
//  val ser = write(Animals(Dog("pluto") :: Fish(1.2) :: Nil))
//  val ser2  = """{"animals":[{"jsonClass":"Hierachy$Dog","name":"pluto"},{"jsonClass":"Hierachy$Fish","weight":1.2}]}"""
//  println(ser)
//  println(parse(ser2).extract[Animals])
//  println(parse(ser).extract[Animals])

  val doggy = write(Dog("pluto"))
  println(doggy.getBytes())
 // val doggy = """{"jsonClass":"Dog","name":"pluto"}"""

  val jdoggy = """{"Dog":"aaaa"}"""

  println(doggy)
  println(parse(doggy).extract[Animal])
  //println(parse(jdoggy).extract[Animal])
}
