package se.kth.spark.assignment1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD 

class Shingle {  

  def createHashedShingles(dirPath: String, k:Int) : RDD[(String, List[Int])] = {
    val conf = new SparkConf().setAppName("ID2222 Assignment 1").setMaster("local")
    val sc = new SparkContext(conf)
    val files = sc.wholeTextFiles(dirPath)
    
    val shingles = files.flatMapValues {
      case (str) =>
        str.replaceAll("\\s{2,}", " ").grouped(k)
    }

    val hashShingles = shingles.mapValues(str => str.hashCode()).groupByKey().mapValues(l => l.toList.sorted)    
    return hashShingles
  }
}