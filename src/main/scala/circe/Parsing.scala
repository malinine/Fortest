package circe
import io.circe._, io.circe.parser._

object Parsing extends App {

  /* === parse valid json === */
  val rawJson: String = """
      {
        "firstName":"Leonard ",
        "lastName":"Nimoy",
        "age":81
      }
    """

  val goodResult = parse(rawJson)
  println(goodResult)
  println(goodResult.right.get)

  /* === parse invalid json === */
  val badJson: String = "bad"
  val badResult = parse(badJson)
  println(badResult)
  println(badResult.left.get)

  /* === using getOrElse (an extension method provided by Cats) === */
  import cats.syntax.either._
  val json: Json = parse(rawJson).getOrElse(Json.Null)
  println(json)

}
