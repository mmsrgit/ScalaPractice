package com.home.oop

object C5AbstractDataTypes extends App{

  abstract class Animal{
    val creatureType: String
    def eat:Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "K9"
    override def eat:Unit = println("crunchcrunch")
  }

  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait ColdBlooded {}

  class Croc extends Animal with Carnivore with ColdBlooded {
    def eat(animal: Animal): Unit = println(s"I am Croc and I am eating ${animal.creatureType}")
    def eat:Unit = println("num num")
    val creatureType:String = "croc"

  }

  val dog = new Dog
  val croc = new Croc
  croc.eat(dog)

}
