package com.home.oop

object C3Objects extends App{

  object Person {
    val N_EYES = 2
    def isAlive:Boolean = true
    def apply(person1:Person, person2:Person):Person = new Person("Bobby")
  }

  class Person (val name: String){

  }

  println(Person.N_EYES)
  println(Person.isAlive)

  val mary = new Person("Mary")
  val john = new Person("John")

  val bobby = Person(mary,john)



}
