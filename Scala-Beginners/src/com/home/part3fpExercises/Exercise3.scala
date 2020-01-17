package com.home.part3fpExercises

/*
   1. Expand MyList
      - foreach method - which takes a function (A => Unit) and applies to all elements in the list    A => Unit
      - sort which receives a function (A,A) => Int and returns MyList    ((A,A) => Int) => MyList
      - zipWith function which takes another list and zip function (list , (A,B) => C) => MyList[C]
      - fold function curried

      e.g.
      [1 2 3].foreach(x => println(x))
      [4 2 7].sort((x,y) => y-x) will return [7 4 2]
      [1 2 3].zipWith([4 5 6], y*x) will return [1*4 2*5 3*6]
      fold(startValue)(function) => a value
      [1 2 3].fold(0)(x+y) = 6


   2. toCurry takes a function (f: (Int,Int) => Int)  and   returns "Int => Int => Int"
      fromCurry (f: Int => Int => Int) and returns (Int,Int) => Int

   3. compose(f,g) => x => f(g(x))
      andThen(f,g) => x => g(f(x))


 */

abstract class MyListH[+A] {

  def head:A
  def tail:MyListH[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListH[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

  def map[B](transformer:(A => B)):MyListH[B]
  def flatMap[B](transformer:(A=>MyListH[B])):MyListH[B]
  def filter(predicate:(A => Boolean)):MyListH[A]

  // concatenation
  def ++[B >: A](list:MyListH[B]):MyListH[B]

  //exercise
  def foreach(function: A=>Unit):Unit
  def sort(compare: (A,A) => Int):MyListH[A]
  def zipWith[B,C](list:MyListH[B], zip: (A,B) => C):MyListH[C]
  def fold[B](start:B)(function: (B,A) => B):B

}

// ******* Empty should be proper substiture of MyList of any type
object EmptyH extends MyListH[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListH[Nothing] = throw new NoSuchElementException
  def isEmpty:Boolean = true
  def add[B >: Nothing](element:B):MyListH[B] = new ConsH(element, EmptyH)
  def  printElements():String = "Empty"

  def map[B](transformer:((Nothing) => B)):MyListH[B] = EmptyH
  def flatMap[B](transformer:((Nothing) => MyListH[B])):MyListH[B] = EmptyH
  def filter(predicate:(Nothing => Boolean)):MyListH[Nothing] = EmptyH

  def ++[B>:Nothing](list:MyListH[B]):MyListH[B] = list

  def foreach(function:Nothing => Unit) = ()
  def sort(compare: (Nothing,Nothing) => Int):MyListH[Nothing] = EmptyH
  def zipWith[B,C](list:MyListH[B], zip: (Nothing,B) => C):MyListH[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have same length")
    else EmptyH
  def fold[B](start:B)(function: (B,Nothing) => B):B = start

}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsH[+A](h:A, t:MyListH[A]) extends MyListH[A] {

  def head: A = h
  def tail: MyListH[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListH[B] = new ConsH(element, this)
  def printElements(): String = {
    if (t.isEmpty) "" + h else h + "," + t.printElements()
  }

  def map[B](transformer:(A => B)):MyListH[B] =
    new ConsH(transformer(h),t.map(transformer))



  def filter(predicate:(A => Boolean)):MyListH[A] =
    if(predicate(h)) new ConsH(h,t.filter(predicate))
    else t.filter(predicate)



  def ++[B>:A](list:MyListH[B]):MyListH[B] = new ConsH(h,t ++ list)

  def flatMap[B](transformer:(A => MyListH[B])):MyListH[B] =
    transformer(h) ++ t.flatMap(transformer)

  def foreach(function: A => Unit): Unit = {
    function(h)
    t.foreach(function)
  }

  def sort(func:(A,A)=> Int):MyListH[A] = {
    def insert(x:A, sortedList:MyListH[A]):MyListH[A] = {
      if(sortedList.isEmpty) new ConsH(x,EmptyH)
      else if(func(x,sortedList.head) <= 0) new ConsH(x,sortedList)
      else new ConsH(sortedList.head, new ConsH(x,sortedList.tail))
    }
    val sorted = tail.sort(func)
    insert(h,sorted)
  }

  def zipWith[B,C](list:MyListH[B], zip: (A,B) => C):MyListH[C] =
    //if(t.isEmpty) throw new RuntimeException("Lists do not have same length")
    new ConsH(zip(h,list.head),t.zipWith(list.tail,zip))
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


object Exercise3 extends App {

  val list:MyListH[Int] = new ConsH(5, new ConsH(3, new ConsH(8, new ConsH(4, EmptyH))))
  println(list)

  list.foreach(println) // equivalent to list.foreach(x => println(x))

  println(list.sort((x,y) => y-x))
  val anotherList = EmptyH.add(2).add(3).add(4).add(1)
  println("List " +list)
  println("anotherList " +anotherList)
  println(list.zipWith(anotherList, (x:Int,y:Int) => x*y))

  println("fold")
  println(list.fold(3)((x,y) => x+y))

}
