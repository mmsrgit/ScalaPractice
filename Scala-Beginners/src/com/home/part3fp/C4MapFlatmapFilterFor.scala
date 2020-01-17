package com.home.part3fp

object C4MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list)
  println(list.head)
  println(list.tail)

  val mapped = list.map(x => x*1)
  println(mapped)
  val flatmapped = list.flatMap(x => List(x,x+1))
  println(flatmapped)
  val filtered = list.filter(x => x%2==0)
  println(filtered)
  val foreached = list.foreach(println)

  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black","white")

  numbers.foreach(x=> chars.foreach(y => print(x+""+y+" ")))
  println()

  println(numbers.flatMap(n => chars.map(c => ""+n+c)))

  println(numbers.flatMap(n => chars.flatMap(c => colors.map(color => ""+n+c+"-"+color))))

  // for-comprehension

  val forComprehensions = for {
    n <- numbers if n%2==0
    c <- chars
    color <- colors
  } yield ""+n+c+"-"+color

  println(forComprehensions)

}
