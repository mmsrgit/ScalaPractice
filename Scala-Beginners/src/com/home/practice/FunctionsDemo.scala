package com.home.practice

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object FunctionsDemo extends App {

  def printNtimes(aString: String, n: Int): String = {
    if(n == 1) aString else aString+printNtimes(aString,n-1)
  }

  println(printNtimes("hello",3))

  def greetingFunction(name:String , age: Int): String = {
    "Hi, my name is "+name+" and I am "+age
  }
  println(greetingFunction("Maruthi",36))


  def factorial(n: Int):Int = {
    if(n==1                                                                                                                                                                                                                                    ) 1 else n*factorial(n-1)
  }

  println(factorial(5))

  def isPrime(n: Int):Boolean = {
    (2 to n/2) forall (n%_!=0)
  }

  def isPrime1(n: Int):Boolean = {
    !((2 until n/2) exists(n%_==0))
  }

  // using recursion
  def isPrime2(n:Int):Boolean = {
    @tailrec
    def isPrimeUntil(p: Int):Boolean ={
      if(p==1) true else n%p !=0 && isPrimeUntil(p-1)
    }
    isPrimeUntil(n/2)
  }

  println(isPrime(107))

  def febanacci(n:Int): Int = {
    if(n == 2 || n==1 ) 1 else febanacci(n-1)+febanacci(n-2)
  }

  println(febanacci(8))

  //auxillary function
  def aBigFunction(n:Int): Int = {
   def aSmallFunction(a:Int, b:Int):Int = a+b

   aSmallFunction(n, n-1)
 }

  def listPrimeNumbers(n:Int) = {
    val listp = new ListBuffer[Int]()
    //println( ((2 to n) filter isPrime).toList)
    //println(List.range(2 ,n+1) filter isPrime)
    println(List.range(2,n+1) filter isPrime2)

  }

  listPrimeNumbers(1000)

}
