package com.home.oop

object C6Generics extends App {

  class MyList[+A] {
    def add[B >: A](element:B):MyList[B] = ???
  }

  val myIntegerList = new MyList[Int]
  val myStringList = new MyList[String]

  object MyList {
    //generic methods
    def empty[A]: MyList[A] = ???
  }

  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] - covariance

  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // what happens if we add animalList.add(new Dog) ??? => It should convert animalList from CovariantList[Cat] to CovariantList[Animal]

  // 2. no = INVARIANCE
  class InvariantList[A]
  val invariantList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell No ! = CONTRAVARIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal];

  // bound types

  class Cage[A <: Animal](animal:A){}
  val cage = new Cage(new Dog)




}
