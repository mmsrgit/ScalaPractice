import scala.io.Source

object OrderRevenue {
  def main(args:Array[String]) = {
    val orderId = args(0).toInt
    val orders = Source.fromFile("/Users/RAMA/Documents/data/retail_db/order_items/part-00000").getLines.toList
    val sum = orders.filter(_.split(",")(1).toInt == orderId).map(_.split(",")(4).toFloat).reduce(_+_)
    println(sum)
  }
}
