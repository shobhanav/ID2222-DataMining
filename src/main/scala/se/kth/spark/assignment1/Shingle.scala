package se.kth.spark.assignment1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD 
import scala.collection.immutable.Set

class Shingle {  

  def createHashedShingles(dirPath: String, k:Int) : RDD[(String, Set[Int])] = {
    val conf = new SparkConf().setAppName("ID2222 Assignment 1").setMaster("local")
    val sc = new SparkContext(conf)
    val files = sc.wholeTextFiles(dirPath)
    
    val shingles = files.flatMapValues {
      case (str) =>
        str.replaceAll("\\s{2,}", " ").grouped(k)
    }     
    return shingles.mapValues(str => str.hashCode()).groupByKey().mapValues(l => l.toSet)
  }
}