package com.home.part3fp

object C1WhatsAFunction extends App {

  // use functions as first class elements

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element*2
  }

  println(doubler(2))

  trait MyFunction[A,B] {
    def apply(element:A): B
  }

  //
  val stringToInt = new Function1[String,Int] {
    override def apply(element: String): Int = element.toInt
  }

  println(stringToInt("3"))

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }

  println(adder(1,2))

  // adder type can be represented as ((Int,Int) => Int) like below

  val anotherAdder:((Int,Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }



}
