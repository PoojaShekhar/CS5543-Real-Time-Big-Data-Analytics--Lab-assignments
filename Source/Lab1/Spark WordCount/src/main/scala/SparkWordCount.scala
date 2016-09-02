

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Mayanka on 09-Sep-15.
 */
object SparkWordCount {

  def main(args: Array[String]) {

    //System.setProperty("hadoop.home.dir","F:\\winutils");

    import org.apache.log4j.Logger
    import org.apache.log4j.Level

    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)



    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input")

    val wc=input.flatMap(line=>{line.split(".\n")}).map(word=>(word,1)).cache()

    val output=wc.reduceByKey(_+_)

    output.sortBy(_._1).coalesce(1).saveAsTextFile("output")

    val o=output.collect()

    var s:String="Sentences:Count \n"
    o.foreach{case(word,count)=>{

      s+=word+" : "+count+"\n"


    }}

    println(s)

  }

}
