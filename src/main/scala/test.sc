

case class Address(street: String, city: String)
case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
case class SimplePerson(name: String, address: Address,children: List[Child])

" val testJson =parse(""""""
  { ""name"": ""joe"",
  ""address"": {
    ""street"": ""Bulevard"",
    ""city"": ""Helsinki""
  },
  ""children"": [
    {
      ""name"": ""Mary"",
      ""age"": 5,
      ""birthdate"": ""2004-09-04T18:06:22Z""
    },
    {
      ""name"": ""Mazy"",
      ""age"": 3
    }
  ]
  }"""""")"

implicit val formats = net.liftweb.json.DefaultFormats
testJson.extract[SimplePerson]
>> SimplePerson(joe,Address(Bulevard,Helsinki),List(Child(Mary,5,Some(Sun Sep 05 01:06:22 ICT 2004)), Child(Mazy,3,None)))