package com.home.part3fpExercises

/*
  1. Define a function which takes 2 strings and concatenates them
  2. transform MyPredicate and MyTransformer into function types
  3. define a function which takes an argument Int, and returns another function which takes an Int and returns an Int
     - what is the type of the function
     - how to do it
   */

abstract class MyListPG[+A] {

  def head:A
  def tail:MyListPG[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListPG[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

  def map[B](transformer:(A => B)):MyListPG[B]
  def flatMap[B](transformer:(A=>MyListPG[B])):MyListPG[B]
  def filter(predicate:(A => Boolean)):MyListPG[A]

  // concatenation
  def ++[B >: A](list:MyListPG[B]):MyListPG[B]


}

// ******* Empty should be proper substiture of MyList of any type
object EmptyPG extends MyListPG[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListPG[Nothing] = throw new NoSuchElementException
  def isEmpty:Boolean = true
  def add[B >: Nothing](element:B):MyListPG[B] = new ConsPG(element, EmptyPG)
  def  printElements():String = "Empty"

  def map[B](transformer:((Nothing) => B)):MyListPG[B] = EmptyPG
  def flatMap[B](transformer:((Nothing) => MyListPG[B])):MyListPG[B] = EmptyPG
  def filter(predicate:(Nothing => Boolean)):MyListPG[Nothing] = EmptyPG

  def ++[B>:Nothing](list:MyListPG[B]):MyListPG[B] = list

}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsPG[+A](h:A, t:MyListPG[A]) extends MyListPG[A] {

  def head: A = h
  def tail: MyListPG[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListPG[B] = new ConsPG(element, this)
  def printElements(): String = {
    if (t.isEmpty) "" + h else h + "," + t.printElements()
  }

  def map[B](transformer:(A => B)):MyListPG[B] =
    new ConsPG(transformer(h),t.map(transformer))

  //def flatMap[B](transformer:MyTransformer[A,MyListPG[B]]):MyListPG[B] =

  def filter(predicate:(A => Boolean)):MyListPG[A] =
    if(predicate(h)) new ConsPG(h,t.filter(predicate))
    else t.filter(predicate)

  def ++[B>:A](list:MyListPG[B]):MyListPG[B] = new ConsPG(h,t ++ list)

  def flatMap[B](transformer:(A => MyListPG[B])):MyListPG[B] =
    transformer(h) ++ t.flatMap(transformer)
}

/*trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}*/

object Exercise1 extends App {

  val concatenate: ((String,String) => String) = new Function2[String,String, String] {
    override def apply(v1: String, v2: String): String = v1.concat(v2)
  }
  println(concatenate("This"," is Spartan"))

  var listOfIntegers:MyListPG[Int] = EmptyPG;
  var listOfStrings:MyListPG[String] = EmptyPG;

  listOfIntegers = listOfIntegers.add(1).add(2).add(3)
  listOfStrings = listOfStrings.add("This").add("is").add("Spartan")

  listOfIntegers = listOfIntegers.map(new Function1[Int,Int] {
    override def apply(element: Int): Int = element+2
  })
  println("NonAnonymous "+listOfIntegers)

  // or above Transformer can be replaced by Anonymous function or Lambda
  listOfIntegers = listOfIntegers.map((x:Int) => x+2)
  println("Anonymous "+listOfIntegers)

  listOfIntegers = listOfIntegers.filter(new Function1[Int,Boolean] {
    override def apply(element: Int): Boolean = element%2==0
  })
  println("NonAnonymous "+listOfIntegers)
  //or
  listOfIntegers = listOfIntegers.filter((x:Int) => x%2==0)
  println("Anonymous "+listOfIntegers)

  // Since it takes one parameter and returns one parameter. The function type is Function1
  // Function1[Int, Function[Int,Int]]

  val superAdder:Function1[Int,Function1[Int,Int]] = new Function1[Int,Function1[Int,Int]] {
    override def apply(v1: Int): Int => Int = new Function[Int,Int] {
      override def apply(y: Int): Int = v1+y;
    }
  }

  val adder = superAdder(3) // v1=3
  println(adder(4)) // y=4
  // we can also merge above 2 statements
  println(superAdder(3)(4))

  /*def hof(a:Int): (Int => Int) = {
    val functional = new Function1[Int, Int] {
      override def apply(v1: Int): Int = v1+2
    }
    functional
  }*/
}
