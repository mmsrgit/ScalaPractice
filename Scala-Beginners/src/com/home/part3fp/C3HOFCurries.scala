package com.home.part3fp

object C3HOFCurries extends App {

  // val superFunction:(Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???

  // function that applies a function n times over a value x

  // nTimes(f,n,x)
  // nTimes(f,3,x) = f(f(f(x)))

  def nTimes(f:Int => Int,n:Int,x:Int) :Int =
    if(n <=0 ) x
    else nTimes(f,n-1,f(x))

  val plusOne = nTimes(x =>x+1,4,8)
  println(plusOne)

  // nTimes(f,3,f(x))
  // nTimes(f,2,f(f(x)))
  // nTimes(f,1,f(f(f(x))))
  // nTimes(f,0,f(f(f(f(x)))))

  def nTimesBetter(f:Int=>Int, n:Int):Int=>Int =
    if(n<=0) x => x
    else (x:Int) =>  nTimesBetter(f,n-1)(f(x))

  println(nTimesBetter(x=>x+1,0)(4))

  println(nTimesBetter(x=>x+1,4))

  val superAdder :Int => (Int => Int) = x => y => x+y
  val add3 = superAdder(3)



  println(add3(4))
  println(superAdder(3)(4))

  // curriedFormatter which converts from double to string. Gets multiple parameters formatter and double.
  def curriedFormatter: String => Double => String = c => d => c.format(d)
  // or
  def curriedFormatter(c:String)(x:Double): String = c.format(x) // there is format function in String class

  val curried:String => (Double => String) = c => x => c.format(x)

  val standardFormat  = curried("%4.2f")
  //val preciseFormat: (Double => String)
  println(standardFormat)
  println("%4.2f".format(23483.1223345))
  println("")


  def func(f:Int => Int, n:Int):Int => Int =
    if(n<=0) x => x
    else x => func(f,n-1)(f(x))


}
