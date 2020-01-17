package com.home.practice

object ExpressionsDemo extends App{

  // Everything in Scala is an expression which returns either a value or Unit ()

  val codeBlock = {
    val a = 2
    val b = a+4
    if(a>b) "greater" else "smaller"
  }

  println(codeBlock)

  // value of the above block is the value of the expression

}
