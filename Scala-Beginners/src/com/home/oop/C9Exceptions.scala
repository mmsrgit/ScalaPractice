package com.home.oop

import java.nio.{BufferOverflowException, BufferUnderflowException}

object C9Exceptions extends App {


  // throwing exceptions
  // val exception = throw new NullPointerException // As everything in Scala is an expression
  // Throwable classes extend Throwable class
  // Exception and Error are major Throwable sub types

  def getInt(withException:Boolean):Int =
    if(withException) throw new RuntimeException("throwing RunTimeException")
    else 42

  try{
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught RunTimeException")
  } finally {
    println("finally")
  }

  // Define own exceptions

  class MyException extends Exception

  val exception = new MyException

  //throw exception

  /*
  1. Crash your program with an OutOfMemoryError
  2. Crash with StackOverflowError
  3. PocketCalculator
     - add(x,y)
     - subtract(x,y)
     - multiply(x,y)
     - divide(x,y)

     Throw Overflow exception if add(x,y) exceeds Int.maxvalue
     Throw Underflow exception if subtract(x,y) exceeds Int.minValue
     Throw MathCalculation exception for division by 0
   */

  // val array = Array.ofDim(Int.MaxValue)
  def infinite:Int = 1+infinite

  //println(infinite)

  def fact(n:Int):Int = {
    if(n==1) 1 else n+fact(n-1)
  }


  class PocketCalc(val x:Int, val y:Int) {
    def add:Int = if(x+y > Int.MaxValue) throw new OverflowException else x+y
    def subtract:Int = if(x-y > Int.MinValue) throw new UnderflowException else x-y
    def multiply:Int = x*y
    def divide:Int = if(y==0) throw new MathCalculationException else x/y
  }

  class OverflowException extends Exception
  class UnderflowException extends Exception
  class MathCalculationException extends Exception

  println(fact(10000))
  println(Int.MaxValue)
}
