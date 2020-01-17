import org.apache.spark.SparkConf
import org.apache.spark.streaming._

object StreamingWordCount {

  def main(args: Array[String]): Any = {

    val executionMode = args(0) // 'yarn-client' in case of remote

    val conf = new SparkConf().setAppName("Streaming word count").setMaster(executionMode)
    val ssc = new StreamingContext(conf,Seconds(10))

    //val lines = ssc.socketTextStream("localhost",9999)
    val lines = ssc.socketTextStream(args(1),args(2).toInt)
    val words = lines.flatMap(line => line.split(" "))
    val wordTuples = words.map(word => (word,1));
    val wordCount = wordTuples.reduceByKey((t,v) => t+v )

    wordCount.print()
    ssc.start()
    // Comment below if you are running in local
    ssc.awaitTermination()

  }

}
