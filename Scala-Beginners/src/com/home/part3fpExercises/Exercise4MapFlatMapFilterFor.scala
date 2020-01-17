package com.home.part3fpExercises

object Exercise4MapFlatMapFilterFor extends App {

  /*

     1. Check if MyList supports for-comprehensions
     2. Implement small collection of at most ONE element - Maybe[+T]
        - implement map, flatMap, filter for this collection
   */

  val listInts:MyListF[Int] = EmptyF.add(3).add(8).add(4).add(5).add(7)
  val listStrings:MyListF[String] = EmptyF.add("a").add("b").add("c").add("d").add("e")

  val combined = for {
    i <- listInts
    s <- listStrings
  } yield i+s

  println(combined)
  println(combined.getClass)
  

}

abstract class MyListF[+A] {

  def head:A
  def tail:MyListF[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListF[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

  def map[B](transformer:(A => B)):MyListF[B]
  def flatMap[B](transformer:(A=>MyListF[B])):MyListF[B]
  def filter(predicate:(A => Boolean)):MyListF[A]

  // concatenation
  def ++[B >: A](list:MyListF[B]):MyListF[B]

  //exercise
  def foreach(function: A=>Unit):Unit
  def sort(compare: (A,A) => Int):MyListF[A]
  def zipWith[B,C](list:MyListF[B], zip: (A,B) => C):MyListF[C]
  def fold[B](start:B)(function: (B,A) => B):B

}

// ******* Empty should be proper substiture of MyList of any type
object EmptyF extends MyListF[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListF[Nothing] = throw new NoSuchElementException
  def isEmpty:Boolean = true
  def add[B >: Nothing](element:B):MyListF[B] = new ConsF(element, EmptyF)
  def  printElements():String = "Empty"

  def map[B](transformer:((Nothing) => B)):MyListF[B] = EmptyF
  def flatMap[B](transformer:((Nothing) => MyListF[B])):MyListF[B] = EmptyF
  def filter(predicate:(Nothing => Boolean)):MyListF[Nothing] = EmptyF

  def ++[B>:Nothing](list:MyListF[B]):MyListF[B] = list

  def foreach(function:Nothing => Unit) = ()
  def sort(compare: (Nothing,Nothing) => Int):MyListF[Nothing] = EmptyF
  def zipWith[B,C](list:MyListF[B], zip: (Nothing,B) => C):MyListF[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have same length")
    else EmptyF
  def fold[B](start:B)(function: (B,Nothing) => B):B = start

}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsF[+A](h:A, t:MyListF[A]) extends MyListF[A] {

  def head: A = h
  def tail: MyListF[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListF[B] = new ConsF(element, this)
  def printElements(): String = {
    if (t.isEmpty) "" + h else h + "," + t.printElements()
  }

  def map[B](transformer:(A => B)):MyListF[B] =
    new ConsF(transformer(h),t.map(transformer))



  def filter(predicate:(A => Boolean)):MyListF[A] =
    if(predicate(h)) new ConsF(h,t.filter(predicate))
    else t.filter(predicate)



  def ++[B>:A](list:MyListF[B]):MyListF[B] = new ConsF(h,t ++ list)

  def flatMap[B](transformer:(A => MyListF[B])):MyListF[B] =
    transformer(h) ++ t.flatMap(transformer)

  def foreach(function: A => Unit): Unit = {
    function(h)
    t.foreach(function)
  }

  def sort(func:(A,A)=> Int):MyListF[A] = {
    def insert(x:A, sortedList:MyListF[A]):MyListF[A] = {
      if(sortedList.isEmpty) new ConsF(x,EmptyF)
      else if(func(x,sortedList.head) <= 0) new ConsF(x,sortedList)
      else new ConsF(sortedList.head, new ConsF(x,sortedList.tail))
    }
    val sorted = tail.sort(func)
    insert(h,sorted)
  }

  def zipWith[B,C](list:MyListF[B], zip: (A,B) => C):MyListF[C] =
  //if(t.isEmpty) throw new RuntimeException("Lists do not have same length")
    new ConsF(zip(h,list.head),t.zipWith(list.tail,zip))
  def fold[B](start:B)(function: (B,A) => B):B =
    t.fold(function(start,h))(function)
  /*if(t.isEmpty) function(start,head)
  else t.fold(function(start,head))(function)*/

  def toCurry(func: (Int, Int) => Int): Int => Int => Int =
    x => y => func(x,y)

  def fromCurry(func: Int => Int => Int):(Int,Int) => Int =
    (x,y) => func(x)(y)

  def compose[A,B,T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A,B,T](f: A => B, g: B => T): A => T =
    x => g(f(x))





}


