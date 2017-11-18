package se.kth.spark.assignment2

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ArrayBuffer

object BonusMain {
  
    def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ID2222 Assignment 2").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val file = sc.textFile("src/main/resources/lab2/baskets.dat")
    val baskets = file.map(_.split("\\s+").map(_.toInt).toSet).cache()
    println("Baskets:", baskets.count())
    val s = 1000
    
    val minc = 0.01 //(0.01% confidence)
    println("Support", s)
    val founded = Main.findFreqItems(baskets, s)
    
        //compute association rules
    val rules = founded.flatMap(i => genRules(baskets, i._1.toSet, i._2).filter(x => x._3>= minc))
    
    println(founded.size)
    founded.foreach(println)
    
    println("**********************Rules***********************************")    
    rules.foreach(println)
    

  }
    
    // For rule lhs->rhs, returns lhs, rhs, confidence=(support(lhs U rhs)/ support(lhs))
    def genRules(all:RDD[Set[Int]], largeItemSet:Set[Int], supp:Int) : Array[(Set[Int], Set[Int], Double)] ={
      var i =0
      var temp :  Array[(Set[Int], Set[Int], Double)] = Array((Set(),Set(), -1.doubleValue()))
      if(largeItemSet.size < 2){
        return temp
      }
      
      for(i:Int <- 2 to largeItemSet.size -1){
         temp = temp ++ largeItemSet.toSeq.combinations(i).toArray.map(s => (s.toSet,largeItemSet.diff(s.toSet), 
             supp.doubleValue()/(all.filter(x => s.toSet subsetOf x).count())))
      }
      return temp
    }
}