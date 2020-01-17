package com.home.practice

object StringOps extends App {

  val str: String = "Hello world"

  println(str.substring(6,11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replaceAll("world","Maruthi"))

  val numberString = "2"
  println("a" +: numberString +: "z")

  // Scala specific: String interpolators

  // S-interpolators
  val name = "Maruthi"
  val age = "36"

  val greeting = s"I am $name and I am $age"
  val anotherGreeting = s"I am $name and I will be turning ${age + 1}"

  println(greeting)
  println(anotherGreeting)

  //F-interpolated strings
  val num=1.2f
  val geek = f"$name can eat $num%2.2f pizzas in a minute"
  println(geek)
}
