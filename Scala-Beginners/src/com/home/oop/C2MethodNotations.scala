package com.home.oop

object C2MethodNotations extends App{

  class Person(val name: String, favoriteMovie:String) {
    def likes(movie: String):Boolean = movie == favoriteMovie
    def +(person:Person):String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"what the heck ${name}!!"
    def isAlive : Boolean = true

    def apply(): String = s"Hello ${name}"
  }

  val mary = new Person("Mary","Avatar")
  println(mary likes "Avatar")
  val jose = new Person("Jose","Interstellar")
  println(mary + jose)
  println(mary.+(jose))

  //prefix notations - unary operators are methods with unary_ prefixed

  val x = -1 // equivalent with 1.unary_-
  val y = 1.unary_-

  println(!mary)
  println(mary.unary_!)

  //postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  println(mary.apply())
  println(mary())


}
