package com.home.oop

object OOBasics extends App {

  val person = new Person("Maruthi",22)

  person.greet("Airita")

  val writer1= new Writer("Charles","Dickens",1812)
  val writer2 = new Writer("Charles","Dickens",1812)
  val novel = new Novel("expectations",1862,writer1)

  println("isWrittenBy "+novel.isWrittenByAuthor(writer2))

  val counter = new Counter()
  println(counter.inc.inc.inc.counter)
  println(counter.counter)
  //println(counter.inc.counter)
  //println(counter.inc.counter)
  //println(counter.inc.inc.inc.counter)
  //println(counter.inc(3))
  //println(counter.counter)

}

class Person(name:String, val age:Int){
  println(3+1)

  def greet(name:String): Unit = println(s"$name says hi to ${this.name}")

  def greet(): Unit = println(s"Hi this is $name")

  def greet(name: String , anotherName:String = "default") = println(s"$name and $anotherName")

}

/**
  *  Novel and Writer
  *
  *  Writer : first name, surname, year
  *
  *  - method fullname
  *
  *  Novel: name, yearofrelease, author
  *  - method ageOfAuthor at the yearOfRelease
  *  - method isWrittenBy Authoer
  *  - copy (new year of release) = new instance of Novel
  *
  */

/*
   Counter class
   - receives int value
   - method current count
   - method to increment/decrement => new Counter
   - overload inc/dec to receive an amount

 */

class Writer(val firstName:String ,val lastName:String, val year: Int) {
  def fullName():String = firstName.concat(" ").concat(lastName)
}

class Novel(val name:String, val yearOfRelease:Int, var author:Writer) {

  def ageOfAuthorAtTheRelease():Int = this.yearOfRelease - this.author.year

  def isWrittenByAuthor(authorArg:Writer) = this.author == authorArg

  def copy(newYear:Int):Novel = new Novel(this.name,newYear,this.author)
}

class Counter (val counter: Int=0){

  def inc() = {
    println("incrementing")
    new Counter(counter+1)

  }
  def dec()={
    println("decrementing")
    new Counter(counter-1)
  }

  def inc(n:Int):Counter = {
    if(n==0) this
    else inc.inc(n-1)
    //new Counter(counter+n)
  }
  def dec(n:Int):Counter = {
    if(n==0) this
    else dec.dec(n-1)
    //new Counter(counter-n)
  }

}
