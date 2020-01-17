package com.home.generics

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
     [1,2,3].flatMap(n => [n,n+1])  => [1,2,2,3,3,4]


  ***** hint - MyPredicate[-T] is contravariant  and   MyTransformer[-A,B]   A is contravariant

 */

abstract class MyListGF[+A] {

  def head: A
  def tail: MyListGF[A]
  def add[B>:A](element:B):MyListGF[B]
  def isEmpty:Boolean
  def printElements:String

  override def toString(): String = "["+printElements+"]"

  def map[B](transformer:MyTransformer[A,B]):MyListGF[B]
  def filter(predicate:MyPredicate[A]):MyListGF[A]
  def flatMap[B](transformer:MyTransformer[A,MyListGF[B]]):MyListGF[B]
  def ++[B>:A](list:MyListGF[B]):MyListGF[B]

}

object EmptyGF extends MyListGF[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyListGF[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B>:Nothing](element:B):MyListGF[B] = new ConsGF(element, EmptyGF)
  def printElements: String = "Empty"

  def ++[B >: Nothing](list: MyListGF[B]): MyListGF[B] = list

  def map[B](transformer:MyTransformer[Nothing,B]): MyListGF[B] = EmptyGF
  def flatMap[B](transformer: MyTransformer[Nothing, MyListGF[B]]): MyListGF[B] = EmptyGF
  def filter(predicate:MyPredicate[Nothing]) : MyListGF[Nothing] = EmptyGF



}


class ConsGF[+A](h:A,t:MyListGF[A]) extends MyListGF[A] {

  def head:A = h
  def tail:MyListGF[A] = t
  def isEmpty:Boolean = false
  def add[B>:A](element:B):MyListGF[B] = new ConsGF(element, this)

  def printElements: String = if(t.isEmpty) h+"" else h+" "+t.printElements

  def map[B](transformer: MyTransformer[A, B]): MyListGF[B] =
    new ConsGF(transformer.transform(h) , t.map(transformer))

  def filter(predicate: MyPredicate[A]): MyListGF[A] =
    if(predicate.test(h)) new ConsGF(h, t.filter(predicate))
    else t.filter(predicate)

  def ++[B >: A](list: MyListGF[B]): MyListGF[B] =
    new ConsGF[B](h, t ++ list)

  def flatMap[B](transformer: MyTransformer[A, MyListGF[B]]): MyListGF[B] =
    transformer.transform(h) ++ t.flatMap(transformer)

}

trait MyPredicate[-T] {
  def test(element:T):Boolean
}

trait MyTransformer[-A,B] {
  def transform(element: A): B
}

object TestMyListGF extends App {
  val listOfIntegers = new ConsGF[Int](1, new ConsGF[Int](2, new ConsGF[Int](3, EmptyGF)))
  val anotherListI =  new ConsGF[Int](4, new ConsGF[Int](5, new ConsGF[Int](6, EmptyGF)))
  val concatenatedList = listOfIntegers ++ anotherListI

  println(concatenatedList)

  val transformer = new MyTransformer[Int, String] {
    override def transform(element: Int): String = element*2+""
  }
  val mapString = listOfIntegers.map(transformer).toString()
  println(mapString)

  val filtered = concatenatedList.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element%2==0
  })
  println(filtered)

  val transformer2 = new MyTransformer[Int,MyListGF[Int]] {
    def transform(element: Int): MyListGF[Int] = new ConsGF[Int](element, new ConsGF[Int](element*2, EmptyGF))
  }
  val flatMapList = concatenatedList.flatMap(transformer2)
  println(flatMapList.toString())
}