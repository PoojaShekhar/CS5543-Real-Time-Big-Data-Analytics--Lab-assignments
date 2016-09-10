import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by poojashekhar on 9/7/16.
  */
object  mapreduceexample {

  def main(args: Array[String]) {

    try{

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")


    sparkConf.setAppName("AvgPopulation")
    var sc = new SparkContext(sparkConf)
    sc.addJar("/home/poojashekhar/Downloads/RealTimeBigData/WordCount/WordCount.jar")

    var rdd = sc.textFile("/home/poojashekhar/Downloads/RealTimeBigData/Population")
    var count = rdd.count()
    println("count"+count)

    val sum = rdd.flatMap(line=>{line.split("\n")}).flatMap(line=>line.replaceAll("(?m)^[ \t]*\r?", "")).map(line=>line.toDouble).reduce((a, b) => (a + b))
    println("AvgAge:" + (sum/count))}
    catch{
      case e: NumberFormatException  => System.err.println("Ilegal input");

    }
  }
}