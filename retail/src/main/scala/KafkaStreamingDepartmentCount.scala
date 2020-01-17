import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka._


object KafkaStreamingDepartmentCount {

  def main(args: Array[String]): Unit = {

    val executionMode = args(0)
    val conf = new SparkConf().setAppName("Kafka Dept wise count").setMaster(executionMode)
    val ssc = new StreamingContext(conf,Seconds(30))
    val kafkaParams = Map[String, String]("metadata.broker.list" ->
      "wn01.itversity.com:6667,wn02.itversity.com:6667,wn03.itversity.com:6667,wn04.itversity.com:6667")
    val topics = Set("fkdemommsr")

    val kafkaStream = KafkaUtils.
      createDirectStream[String, String, StringDecoder,StringDecoder](ssc, kafkaParams,topics)
    // kafkaStream type will be tuple. first element is key and second element value.
    val messages = kafkaStream.map( rec => rec._2)

    val deptMessages = messages.filter(msg => {
      val endpoint = msg.split(" ")(6)
      endpoint.split("/")(1) == "department"
    })
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
