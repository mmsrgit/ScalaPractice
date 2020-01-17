package com.home.oop

object C7AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  class LAnimal extends Animal {
    def eat: Unit = println("laughlaughlaugh")
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("hahahaha")
  }

  val laughingAnimal = new LAnimal

  println(funnyAnimal.getClass)
  println(laughingAnimal.getClass)
  sayHello

}
