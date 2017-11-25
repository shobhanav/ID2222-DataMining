package se.kth.spark.assignment3

import org.apache.spark.{SparkConf, SparkContext}

object HyperLogLogCounter { 
  
  val b: Int = 5
  val a: Double = 0.693 // value depends on b
  val counter:Array[Int] = Array.fill[Int](scala.math.pow(2, b).toInt)(0)  
  
  
  def add(item:Int)= {
    //find bucket index which is identified by the first b bits
    val index = item >>> (32-b)
    val w = item << b
    counter(index) = scala.math.max(counter(index), Integer.numberOfLeadingZeros(w) + 1)
  }
  
  def getCardinality(): Double={
    val size = counter.size
    counter.foreach(println)
    val Z = 1/(counter.map(x => (1/scala.math.pow(2,x))).reduce(_+_))
    return a*size*size*Z    
  }
  
    def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ID2222 Assignment 3").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val file = sc.textFile("src/main/resources/amazon-ratings/out.amazon-ratings")
    val temp = file.map(str => str.split("\\s+").take(3).mkString(",").hashCode())
    println("size original - " + temp.collect().size)
    println("size unique - " + temp.collect().toSet.size)
    
    temp.collect().foreach(x => add(x))
    println("Estimated Cardinality: " + getCardinality() )
   
  }
  
}