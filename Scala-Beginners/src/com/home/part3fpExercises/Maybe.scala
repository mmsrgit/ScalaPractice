package com.home.part3fpExercises

// continuation of previous exercise
abstract class Maybe[+T] {

  def map[B](transformer: T => B):Maybe[B]
  def flatMap[B](transformer:T => Maybe[B]):Maybe[B]
  def filter(predicate: T=>Boolean): Maybe[T]

}

case object MaybeNot extends Maybe[Nothing] {

  override def map[B](transformer: Nothing => B): Maybe[B] = MaybeNot

  override def flatMap[B](transformer: Nothing => Maybe[B]): Maybe[B] =  MaybeNot

  override def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+T](value:T) extends Maybe[T] {

  override def map[B](transformer: T => B): Maybe[B] = Just(transformer(value))

  override def flatMap[B](transformer: T => Maybe[B]): Maybe[B] = transformer(value)

  override def filter(predicate: T => Boolean): Maybe[T] =
    if(predicate(value)) Just(value)
    else MaybeNot

}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3)

  println(just3.map(x => x*2))
  println(just3.flatMap(x => Just(x+1)))
  println(just3.flatMap(x => Just(x%2==0)))
  println(just3.filter(x => x%2==0))
}
