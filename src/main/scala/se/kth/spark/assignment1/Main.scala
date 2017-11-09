package se.kth.spark.assignment1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Main {  

  def main(args: Array[String]) {    
  val shingle = new Shingle()
  val hashShingles = shingle.createHashedShingles("src/main/resources/documents", 10)  
  
  hashShingles.collect().foreach(println)
  
  
  }
  
}