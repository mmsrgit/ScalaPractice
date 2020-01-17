package com.home.oop

object C4Inheritance extends App {

  // constructors

  class Person(val name: String, val age: Int){
    def this(name: String) = this(name, 20)
  }

  class Adult(name:String, age: Int) extends Person(name){}


  class Animal {
    val creatureType: String = "wild"
    def eat : String = "nomnom"
  }

  class Dog extends Animal {
    override val creatureType: String = "domestic"

    override def eat: String = "crunchcrunch"
  }
  val dog = new Dog
  println(dog.eat + " && "+ dog.creatureType)

  class Cat(override val creatureType: String = "domestic") extends Animal {}

}
