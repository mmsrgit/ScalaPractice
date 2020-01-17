package com.home.exercises

abstract class MyListG[+A] {

  def head:A
  def tail:MyListG[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListG[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

}

// ******* Empty should be proper substiture of MyList of any type
object EmptyG extends MyListG[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListG[Nothing] = throw new NoSuchElementException
  def isEmpty:Boolean = true
  def add[B >: Nothing](element:B):MyListG[B] = new ConsG(element, EmptyG)
  def  printElements():String = "Empty"
}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsG[+A](h:A, t:MyListG[A]) extends MyListG[A] {

  def head:A = h
  def tail:MyListG[A] = t
  def isEmpty:Boolean = false
  def add[B >: A](element:B):MyListG[B] = new ConsG(element,this)
  def printElements():String = {
    // s"${this.h},${this.t}"
    if(t.isEmpty) ""+h else h + "," + t.printElements()
  }
}

object TestMyListGenerics extends App {

  val listOfIntegers = EmptyG;
  val listOfStrings = EmptyG;

  listOfIntegers.add(1).add(2).add(3)
  listOfStrings.add("This").add("is").add("Spartan")
  println(listOfIntegers.add(1).add(2).add(3).toString())
  println(listOfStrings.add("This").add("is").add("Spartan").toString())

}








