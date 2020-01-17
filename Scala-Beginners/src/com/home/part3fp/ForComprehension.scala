package com.home.part3fp

object ForComprehension extends App {

  val values = for {
    i <- 0 to 10
  }yield i+1
  println(values.mkString(" "))

}
