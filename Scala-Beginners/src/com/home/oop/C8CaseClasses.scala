package com.home.oop

object C8CaseClasses extends App {

  case class Person(name: String, age: Int)

  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim)

  val jim2 = new Person("Jim",34)

  println(jim == jim2)

  val jim3 = jim.copy() // or jim.copy(age=45)
  println(jim == jim3)

  val mary = Person("Mary",31) // calls apply method on companion object
  println(mary)

  case object UnitedKingdom {
    def name: String = "The UK of GB"
  }

}
