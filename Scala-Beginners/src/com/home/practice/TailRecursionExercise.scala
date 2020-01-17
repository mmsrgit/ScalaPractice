package com.home.practice

import scala.annotation.tailrec

object TailRecursionExercise extends App {

  /**
    * 1. Concatenate the string n times
    * 2. IsPrime function tail recursive
    * 3. Fibonacci function which is tail recursive
    *
    * Any recursive function can be made tail recursive. The trick is to use accumulator variables as many as we want
    */

  def concatStringNtimes(aString: String, n:Int): String = {

    def auxConcat(anotherString: String, k: Int): String = {
      if(k==1) anotherString else auxConcat(anotherString+aString,k-1)
    }
    auxConcat(aString,n)
  }

  println(concatStringNtimes("hello",4))

  def isPrime(n: Int): Boolean = {
    def isAuxPrime(p: Int): Boolean = {
      if(p==1) true else n%p!=0 && isAuxPrime(p-1)
    }
    isAuxPrime(n/2)
  }

  println(isPrime(103))

  def fibonacci(n:Int): Int = {
    @tailrec
    def auxFib(fib: Int, before: Int, prev: Int, index: Int): Int = {
      if(index==n) fib else auxFib(fib+prev,prev,fib, index+1)
    }
    auxFib(1,1,1,2)
  }

  println(fibonacci(9))

  def factorial(n:Int,inter:Int):Int = {
    if(n==1) inter else factorial(n-1,inter*(n-1))
  }

  println(factorial(10,10))

}
