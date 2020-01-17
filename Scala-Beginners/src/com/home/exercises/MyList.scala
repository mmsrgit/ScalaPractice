package com.home.exercises

abstract class MyList {

  /*
  head() = first element of the list
  tail() = remainder of the list
  isEmpty() = is this list empty
  add(number:Int) = returns new list with element added
  toString = a string representation of the list

   */


  def head():Int
  def tail():MyList
  def isEmpty():Boolean
  def add(number:Int):MyList
  def printElements():String
  override def toString():String = "["+printElements()+"]"

}

object Empty extends MyList{

  def head():Int = throw new NoSuchElementException
  def tail():MyList = throw new NoSuchElementException
  def isEmpty():Boolean = true
  def add(number:Int):MyList = new Cons(number, Empty)
  def  printElements():String = "Empty"
}

class Cons(h:Int, t:MyList) extends MyList {

  def head():Int = h
  def tail():MyList = t
  def isEmpty():Boolean = false
  def add(number:Int):MyList = new Cons(number,this)
  def printElements():String = {
    // s"${this.h},${this.t}"
    if(t.isEmpty()) ""+h else h + "," + t.printElements()
  }
}

object ListTest extends App {
  val myList = Empty;
  println(myList.isEmpty())

  println(myList add 3 head())
  var list = myList add 3
  list = list add 4
  list = list add 5
  println(list.toString())

  //println(.isEmpty())
}

/*
class MyListImpl(val list:List[Int]) extends MyList {

  var indexHead = 0;
  var indexTail = 0;

  def head():Int = {list(indexHead)}
  def tail():Int = {2}
  def isEmpty():Boolean = {indexHead == indexTail}
  def add(number:Int):List[Int] = {
    list()
    new MyListImpl(List)
  }
  override def toString():String = ""

}

*/





