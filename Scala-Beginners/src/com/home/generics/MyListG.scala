package com.home.generics

abstract class MyListG[+A] {

  def head:A
  def tail:MyListG[A]
  def add[B >: A](element:B):MyListG[B]
  def isEmpty:Boolean
  def printElements:String
  override def toString:String = "["+printElements+"]"


}

object EmptyG extends MyListG[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListG[Nothing] = throw new NoSuchElementException
  def add[B >: Nothing](element:B):MyListG[B] = new ConsG(element, EmptyG)
  def isEmpty:Boolean = true
  def printElements:String = "Empty"

}

class ConsG[A](h:A, t:MyListG[A]) extends MyListG[A]{
  def head:A = h
  def tail:MyListG[A] = t
  def add[B >: A](element:B):MyListG[B] = new ConsG(element, this)
  def isEmpty:Boolean = false
  def printElements:String = if(t.isEmpty) h+"" else h + " " +t.printElements
}

object TestMyListG extends App {
  val listOfIntegers:MyListG[Int] = new ConsG(1, new ConsG(2, new ConsG(3, EmptyG)))
  val listOfStrings:MyListG[String] = new ConsG("THIS", new ConsG("IS", new ConsG("SPARTAN", EmptyG)))
  println(listOfIntegers)
  println(listOfStrings)
}