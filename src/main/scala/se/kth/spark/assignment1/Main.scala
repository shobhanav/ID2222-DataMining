package se.kth.spark.assignment1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.immutable.Set
import org.apache.spark.rdd.RDD

object Main {

  def main(args: Array[String]) {
    val shingle = new Shingle()
    val jaccardCompare = new CompareSet()
    val minHashing = new MinHashing(8)
    val sigCompare = new CompareSignatures()
    val k = 10
    val hashShingles = shingle.createHashedShingles("src/main/resources/documents", k)

    val list = hashShingles.collect()
    list.foreach(println)

    //compute jaccard similarity among documents
    for (i <- 0 to list.size - 2) {
      for (j <- i + 1 to list.size - 1) {
        val sim = jaccardCompare.computeJaccardSimilarity(list(i)._2, list(j)._2)
        println(s"JaccardSim(File${i},File${j}) = ${sim}")
      }
    }

    val minHashed = hashShingles.mapValues { case (s) => minHashing.sig(s.toList) }
    
    minHashed.map{case(k,v) => (k,v.toList)}.collect().foreach(println)
    

    val minHashedlist = minHashed.collect()
    

    //compute minhash similarity
    for (i <- 0 to minHashedlist.size - 2) {
      for (j <- i + 1 to minHashedlist.size - 1) {
        val sim = sigCompare.compare(minHashedlist(i)._2, minHashedlist(j)._2)
        println(s"MinHashSim(File${i},File${j}) = ${sim}")
      }
    }

  }

}