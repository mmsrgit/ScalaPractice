package com.home.generics

abstract class MyList {
  def head:Int
  def tail:MyList
  def add(element:Int):MyList
  def isEmpty:Boolean
  def toString:String


}

object Empty extends MyList{
  def head:Int = throw new NoSuchElementException
  def tail:MyList = throw new NoSuchElementException
  def add(element:Int):MyList = new Cons(element, Empty)
  def isEmpty:Boolean = true
  override def toString:String = ""


}

class Cons(h: Int, t:MyList) extends MyList {
  def head:Int = h
  def tail:MyList = t
  def add(element:Int):MyList = new Cons(element, this)
  def isEmpty:Boolean = false
  override def toString:String = if(t.isEmpty) h+"" else h + " " + t.toString
}

object testMyList extends App {
  val list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.toString)
}
