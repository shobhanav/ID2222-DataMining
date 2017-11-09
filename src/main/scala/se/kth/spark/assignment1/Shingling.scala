package se.kth.spark.assignment1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object Shingling {
  def main(args: Array[String]) {
    
  val conf = new SparkConf().setAppName("ID2222 Assignment 1").setMaster("local")
  val sc = new SparkContext(conf)  
  val k = 10
  val docDirPath = "src/main/resources/documents"
  
  val files = sc.wholeTextFiles(docDirPath)
  
  //Before shingling, replace multiple spaces with single spaces
  val forShingling = files.mapValues{case (str) =>
    str.replaceAll("\\s{2,}", " ")
  }
  
  val shingled = forShingling.mapValues{case (str) =>
    str.grouped(k).toList
  }
    
  shingled.foreach{case (file, shingles) =>
    println(shingles)    
  }
  }
  
}