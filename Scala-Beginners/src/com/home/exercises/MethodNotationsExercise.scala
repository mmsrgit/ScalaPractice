package com.home.exercises

object MethodNotationsExercise extends App {

  /**
    *  1. Overload + operator which gives nickname like below
    *
    *  mary + "rockstar" => new person "Mary (the rockstar)"
    *
    *  2. Add age to the Person class
    *
    *  Add a unary + operator => new person with the age + 1
    *  +mary => returns mary with the age incremented
    *
  *  3. Add a learns method in the Person class which takes a string => returns "Mary learns <string>"
    *
    *  Add a learnsScala method which do not take any parameters, calls learns method with "Scala" as a parameter. Use it in postfix notation
    *
    *  4. Overload apply method
    *
    *  mary.apply(2) => returns "Mary watched Inception 2 times"
    */

  class Person(val name: String, favoriteMovie: String, val age: Int = 20){
    def +(person:Person):String = s"${name} is hanging out with ${person.name}";
    def +(nickname:String):String = s"${name} (the ${nickname})"

    def unary_+ : Person = {
      new Person(this.name, this.favoriteMovie, this.age+1)
    }

    def learns(skill: String) :String = s"${name} learns ${skill}"
    def learnsScala : String = learns("Scala")

    def apply():String = s"hello ${name}"
    def apply(number:Int):String = s"${name} watched ${favoriteMovie} ${number} times"


  }
  val mary = new Person("Mary","Interstellar")
  println(mary + "rockstar")
  println((+mary).age)
  println(mary learns "Scala")
  println(mary learnsScala)
  println(mary apply 2)
  println(mary())

}
