package com.home.exercises



  /*
  Expand MyList - use case Classes and case objects

   */

  abstract class MyListPG[+A] {

    def head:A
    def tail:MyListPG[A]
    def isEmpty:Boolean
    def add[B >: A](element:B):MyListPG[B]
    def printElements():String
    //override def toString():String = "["+printElements()+"]"

    def map[B](transformer:MyTransformer[A,B]):MyListPG[B]
    def flatMap[B](transformer:MyTransformer[A,MyListPG[B]]):MyListPG[B]
    def filter(predicate:MyPredicate[A]):MyListPG[A]

    // concatenation
    def ++[B >: A](list:MyListPG[B]):MyListPG[B]

  }

  case object EmptyPG extends MyListPG[Nothing] {

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

  case class ConsPG[+A](h:A, t:MyListPG[A]) extends MyListPG[A] {

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


object TestCaseClassExercise extends App {

  val myList:MyListPG[Int] = new ConsPG[Int](1, new ConsPG[Int](2,new ConsPG[Int](3,new ConsPG[Int](4, EmptyPG))))

  println(myList)
}
