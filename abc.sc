val numbers = List(5,4,8,6,2)
numbers.fold(2){(z,i) =>
  z + i
}

class Foo(val name: String, val age: Int, val sex: Symbol)
object Foo {
  def apply(name: String, age: Int, sex: Symbol) = new Foo(name, age, sex)
}
val fooList = Foo("Hugh Jass", 25, 'male) ::
  Foo("Biggus Dickus", 43, 'male) ::
  Foo("Incontinentia Buttocks", 37, 'female) :: Nil

val stringList = fooList.foldLeft(List[String]()) { (z, f) =>
  val title = f.sex match {
    case 'male => "Mr."
    case 'female => "Ms."
  }
  z :+ s"$title ${f.name}, ${f.age}"
}
