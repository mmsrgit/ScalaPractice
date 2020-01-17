package com.home.practice

import scala.annotation.tailrec

object DefaultArgs extends App {

  def factorial(n: Int): Int ={
    @tailrec
    def computeFact(n:Int, acc:Int=1): Int = {
      if(n==1) acc else computeFact(n-1,n*acc)
    }
    computeFact(n)
  }
  println(factorial(5))

}
