package com.home.part3fp

import scala.util.Random

object C5Sequences extends App {

  val list = Seq(1,2,3,7,3)
  println(list)

  println(list ++ Seq(8,2,4))

  println(list.sortBy(o => -o))

  // Range

  val aRange = (0 to 10)
  // println(aRange.foreach(println))

  val aList = List(5,2,4,9)
  val prepended = 4 :: aList ++ List(9,6,3)
  println(prepended)

  //val appended = prepended
  //println(appended)

  val anotherList = 4 +: aList :+ 8
  println(anotherList)

  // fill - curried function in List

  val apples5 = List.fill(5)("apple")
  println(list.mkString("-"))

  // arrays
  val numbers = Array(1,2,3,4)
  numbers(2) = 0
  println(numbers.mkString(" "))

  val threeElements = Array.ofDim[String](3)
  threeElements.foreach(println)

  //arrays and seq
  val numbersSeq:Seq[Int] = numbers // Implicit conversion
  println(numbersSeq)

  // Vector
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  //vectors vs lists


  val maxRuns = 1000
  val maxCapacity = 100000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random

    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity),r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum / maxRuns
  }

  val numbersList = 1 to maxCapacity toList
  val numbersVector = 1 to maxCapacity toVector

  println(getWriteTime(numbersList))
  println(getWriteTime(numbersVector))
}
