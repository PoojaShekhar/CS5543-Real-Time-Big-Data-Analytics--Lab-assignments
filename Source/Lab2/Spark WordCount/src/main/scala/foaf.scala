import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by poojashekhar on 9/10/16.
  */
object foaf {
  def main(args: Array[String]) {



      val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")


      sparkConf.setAppName("AvgPopulation")
      var sc = new SparkContext(sparkConf)


      val rdd = sc.textFile("/home/poojashekhar/Downloads/RealTimeBigData/Tutorial1/Spark WordCount/friend-db")

      val minimum = -99999999

      val uf= rdd.map(x => (x.split("\t")(0), x.split("\t")(1)))

      val friend = uf.map(line=>line._2.split(","))

    connecteds = [((user, friend), minimum) for friend in friends]
    commons = [(pair, 1) for pair in itertools.permutations(friends, 2)]

      friendsListRDD = (sc
      .textFile( fileName, 16 )
      .flatMap( connecteds_and_commons )
      .reduceByKey( lambda total, current: total + current )
      .filter(lambda (pair, counts): counts > 0)
    .map(lambda ((user, friend), counts): (user, (counts, friend)))
      .groupByKey()
      .map(lambda (user, suggestions):(user, Counter( dict( (friend, count) for count, friend in suggestions ) ).most_common( N ) ) )
    #.cache()
    )


    print "924"
    print friendsListRDD.lookup('924')

    }




}
