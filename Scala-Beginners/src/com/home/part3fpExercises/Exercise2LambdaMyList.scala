package com.home.part3fpExercises
/*
  1. Convert all functions to Lambda
  2. Rewrite superAdder as anonymous function
 */

abstract class MyListL[+A] {

  def head:A
  def tail:MyListL[A]
  def isEmpty:Boolean
  def add[B >: A](element:B):MyListL[B]
  def printElements():String
  override def toString():String = "["+printElements()+"]"

  def map[B](transformer:(A => B)):MyListL[B]
  def flatMap[B](transformer:(A=>MyListL[B])):MyListL[B]
  def filter(predicate:(A => Boolean)):MyListL[A]

  // concatenation
  def ++[B >: A](list:MyListL[B]):MyListL[B]


}

// ******* Empty should be proper substiture of MyList of any type
object EmptyL extends MyListL[Nothing] {

  def head:Nothing = throw new NoSuchElementException
  def tail:MyListL[Nothing] = throw new NoSuchElementException
  def isEmpty:Boolean = true
  def add[B >: Nothing](element:B):MyListL[B] = new ConsL(element, EmptyL)
  def  printElements():String = "Empty"

  def map[B](transformer:((Nothing) => B)):MyListL[B] = EmptyL
  def flatMap[B](transformer:((Nothing) => MyListL[B])):MyListL[B] = EmptyL
  def filter(predicate:(Nothing => Boolean)):MyListL[Nothing] = EmptyL

  def ++[B>:Nothing](list:MyListL[B]):MyListL[B] = list

}

// ******** Since MyList is covariant, ConsG must be covariant as well
class ConsL[+A](h:A, t:MyListL[A]) extends MyListL[A] {

  def head: A = h
  def tail: MyListL[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListL[B] = new ConsL(element, this)
  def printElements(): String = {
    if (t.isEmpty) "" + h else h + "," + t.printElements()
  }

  def map[B](transformer:(A => B)):MyListL[B] =
    new ConsL(transformer(h),t.map(transformer))



  def filter(predicate:(A => Boolean)):MyListL[A] =
    if(predicate(h)) new ConsL(h,t.filter(predicate))
    else t.filter(predicate)



  def ++[B>:A](list:MyListL[B]):MyListL[B] = new ConsL(h,t ++ list)

  def flatMap[B](transformer:(A => MyListL[B])):MyListL[B] =
    transformer(h) ++ t.flatMap(transformer)
}



object Exercise2LambdaMyList extends App {

  val concatenate: ((String,String) => String) = new Function2[String,String, String] {
    override def apply(v1: String, v2: String): String = v1.concat(v2)
  }
  println(concatenate("This"," is Spartan"))

  var listOfIntegers:MyListL[Int] = EmptyL;
  var listOfStrings:MyListL[String] = EmptyL;

  listOfIntegers = listOfIntegers.add(1).add(2).add(3)
  listOfStrings = listOfStrings.add("This").add("is").add("Spartan")

  listOfIntegers = listOfIntegers.map(new Function1[Int,Int] {
    override def apply(element: Int): Int = element+2
  })
  println("NonAnonymous "+listOfIntegers)

  // or above Transformer can be replaced by Anonymous function or Lambda
  listOfIntegers = listOfIntegers.map(x => x+2)
  println("Anonymous "+listOfIntegers)

  listOfIntegers = listOfIntegers.filter(new Function1[Int,Boolean] {
    override def apply(element: Int): Boolean = element%2==0
  })
  println("NonAnonymous "+listOfIntegers)
  //or
  listOfIntegers = listOfIntegers.filter(x => x%2==0)
  println("Anonymous "+listOfIntegers)

  // Since it takes one parameter and returns one parameter. The function type is Function1
  // Function1[Int, Function[Int,Int]]

  val superAdder:Function1[Int,Function1[Int,Int]] = new Function1[Int,Function1[Int,Int]] {
    override def apply(v1: Int): Int => Int = new Function[Int,Int] {
      override def apply(y: Int): Int = v1+y;
    }
  }

  val superAdd:(Int => Int => Int) = (x => y => x+y)





  val adder = superAdder(3) // v1=3
  println("***** -> "+adder) // y=4
  // we can also merge above 2 statements
  println(superAdder(3)(4))  // curried Function

  val add3 = superAdd(3)
  println("curried function -> "+add3)


  listOfIntegers = listOfIntegers.add(1).add(2).add(3).add(4).add(5).add(6)
  val mapped = listOfIntegers.map(x => x*2)
  val filtered = listOfIntegers.filter(x => x%2==0)
  val flatMapped = mapped.flatMap(x => new ConsL(x,new ConsL(x+1,EmptyL)))
  println("mapped "+mapped)
  println("filtered "+filtered)
  println("flatmapped "+flatMapped)




}
