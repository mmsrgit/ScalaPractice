package retail_db

import org.apache.spark.{SparkConf,SparkContext}

object GetRevenueForOrder {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster(args(0)).setAppName("Get Order Revenue")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    val orderItems = sc.textFile(args(1))
    val oiTuples = orderItems.map(rec => (rec.split(",")(1).toInt, rec.split(",")(4).toFloat))
    val oir = oiTuples.reduceByKey((a,b) => a+b)
    val revenue = oir.map(rec => rec._1 + "," + rec._2)
    revenue.saveAsTextFile(args(2))


  }

}
