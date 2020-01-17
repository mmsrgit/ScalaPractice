package com.home.part3fp

object C6TuplesAndMaps extends App {

  val aTuple = (2,"Hello Scala")
  println(aTuple)
  println(aTuple.copy(_2 = "Java"))
  println(aTuple.swap)

  val k = (1 to 100).toList
  println(k)

  val aMap: Map[String,Int] = Map()

  val phonebook = Map(("Jim", 123), ("Bob",789)).withDefaultValue(-1)
  println(phonebook)

  // map operations
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  println(phonebook("Mary"))

  val newPairing = "Mary" -> 486

  // Maps are immutable
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  //functionals on map

  //map, flatMap, filter

  println(phonebook.map(pair => pair._1.toLowerCase() -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(x => "902-"+x))

  println(phonebook.toList)
  println(List(("Jim" -> 123)).toMap)

  val names = List("Bob","James","Angela","Brian", "Jim","Mary")
  println(names.groupBy(s => s))
  println(names.groupBy(s => s.charAt(0)))
}
