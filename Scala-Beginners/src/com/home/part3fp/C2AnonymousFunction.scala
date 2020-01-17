package com.home.part3fp

object C2AnonymousFunction extends App {

  val doubler = new Function1[Int,Int] {
    override def apply(x: Int): Int = x*2
  }

  // above function can be replaced by

  val doubler1 = (x: Int) => x*2

  // if we declare doubler type, then we don't have to specify parameter type

  val doubler2:(Int => Int) = x => x*2

}
