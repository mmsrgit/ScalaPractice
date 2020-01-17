import org.apache.spark.SparkConf
import org.apache.spark.streaming._


object StreamingDepartmentCount {

  def main(args: Array[String]): Any = {

    val executionMode = args(0);

    val conf = new SparkConf().setAppName("Department wise count").setMaster(executionMode)
    val ssc = new StreamingContext(conf, Seconds(30))

    val messages = ssc.socketTextStream(args(1),args(2).toInt)

    val exp = ".+?/department/(.+?)/.+?".r

    val deptMessages = messages.filter(msg => exp.pattern.matcher(msg).matches)
    val departmentTuples = deptMessages.map(msg => {
      val endpoint = msg.split(" ")(6)
      (endpoint.split("/")(2) , 1)//3rd element is department name
    })

    val departmentTraffic = departmentTuples.reduceByKey(_+_)
    departmentTraffic.saveAsTextFiles("/user/maruthi_rao2000/deptwisetraffic/cnt")

    ssc.start()
    ssc.awaitTermination()

  }
}
