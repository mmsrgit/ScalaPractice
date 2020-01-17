package com.home.exercises

/*
  1. Generic trait MyPredicate[T] which have a method to test whether T passes that condition
     test(T):Boolean

     e.g "class EvenPredicate extends MyPredicate[Int]" , then test method will check if the number is even
          def test(element:Int):Boolean

  2. Generic trait MyTransformer[A,B] which have a method to convert from type A to type B

     e.g "class StringToIntTransformer extends MyTransformer[String,Int]" then
          def transform(element: String):Int

  3. Implement following functions on MyList
     - map() => which takes MyTransformer returns MyList of different type
     - filter() => which takes MyPredicate returns MyList
     - flatMap() => which takes transformer from A to MyList[B] and returns MyList[B]

     [1,2,3].map(doubleTransformer) => [2,4,6]
     [1,2,3].filter(oddPredicate)   => [1,3]
     [1,2,3].flatMap(n => [n,n+1])  => [[1,2,2,3,3,4]]


  ***** hint - MyPredicate[-T] is contravariant  and   MyTransformer[-A,B]   A is contravariant

 */

abstract class MyListPG[+A] {

  def head:A
  def tail:MyListPG[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListPG[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

  def map[B](transformer:MyTransformer[A,B]):MyListPG[B]
  def flatMap[B](transformer:MyTransformer[A,MyListPG[B]]):MyListPG[B]
  def filter(predicate:MyPredicate[A]):MyListPG[A]

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

  def map[B](transformer:MyTransformer[Nothing,B]):MyListPG[B] = EmptyPG
  def flatMap[B](transformer:MyTransformer[Nothing,MyListPG[B]]):MyListPG[B] = EmptyPG
  def filter(predicate:MyPredicate[Nothing]):MyListPG[Nothing] = EmptyPG

  def ++[B>:Nothing](list:MyListPG[B]):MyListPG[B] = list

}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsPG[+A](h:A, t:MyListPG[A]) extends MyListPG[A] {

  def head: A = h
  def tail: MyListPG[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListPG[B] = new ConsPG(element, this)
  def printElements(): String = {
    // s"${this.h},${this.t}"
    if (t.isEmpty) "" + h else h + "," + t.printElements()
  }

  def map[B](transformer:MyTransformer[A,B]):MyListPG[B] =
    new ConsPG(transformer.transform(h),t.map(transformer))

  //def flatMap[B](transformer:MyTransformer[A,MyListPG[B]]):MyListPG[B] =

  def filter(predicate:MyPredicate[A]):MyListPG[A] =
    if(predicate.test(h)) new ConsPG(h,t.filter(predicate))
    else t.filter(predicate)

  def ++[B>:A](list:MyListPG[B]):MyListPG[B] = new ConsPG(h,t ++ list)

  def flatMap[B](transformer:MyTransformer[A,MyListPG[B]]):MyListPG[B] =
    transformer.transform(h) ++ t.flatMap(transformer)
}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

object TestMyPredicateGenerics extends App {

  var listOfIntegers:MyListPG[Int] = EmptyPG;
  var listOfStrings:MyListPG[String] = EmptyPG;

  listOfIntegers = listOfIntegers.add(1).add(2).add(3)
  listOfStrings = listOfStrings.add("This").add("is").add("Spartan")
  println(listOfIntegers.toString())
  println(listOfStrings.toString())

  listOfIntegers = listOfIntegers.map(new MyTransformer[Int,Int] {
    override def transform(element: Int): Int = element+2
  })

  println(listOfIntegers.toString())

  listOfIntegers = listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element%2==0
  })

  println(listOfIntegers.toString())

  listOfIntegers =  EmptyPG.add(1).add(2).add(3)

  var anotherListOfIntegers:MyListPG[Int] = EmptyPG.add(4).add(5).add(6)

  println(listOfIntegers ++ anotherListOfIntegers)

  anotherListOfIntegers = anotherListOfIntegers.flatMap(new MyTransformer[Int,MyListPG[Int]] {
    override def transform(element: Int): MyListPG[Int] = new ConsPG[Int](element, new ConsPG[Int](element+1, EmptyPG))
  })

  println(anotherListOfIntegers.toString())



}
