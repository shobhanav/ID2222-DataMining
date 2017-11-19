package se.kth.spark.assignment2

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ArrayBuffer

object Main {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ID2222 Assignment 2").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val file = sc.textFile("src/main/resources/lab2/baskets.dat")
    val baskets = file.map(_.split("\\s+").map(_.toInt).toSet).cache()
    println("Baskets:", baskets.count())
    val s = 1000
    println("Support", s)



    val founded = findFreqItems(baskets, s)

    println(founded.size)
    founded.foreach(println)
  }


  def findFreqItemsPair(all:RDD[Set[Int]], s:Int, l:Int = 1, nPairs:Array[Set[Int]] = null, freq:Array[Int] = null):Array[(Seq[Int], Int)] = {
    var partial: Array[(Seq[Int], Int)] = all.filter(basket => nPairs.exists(pr => pr subsetOf basket)).flatMap(_.filter(v => freq contains v).toSeq.combinations(l)).map(p => (p, 1)).reduceByKey(_ + _).filter(basket => basket._2 >= s).collect()
    if(partial.size == 0){
      return Array()
    }
    val res = findFreqItemsPair(all, s, l+1, partial.map(s => s._1.toSet), freq)
    res ++ partial
  }

  def findFreqItems(all:RDD[Set[Int]], s:Int):Array[(Seq[Int], Int)] = {
    var partialRdd = all.flatMap(items => items.map(p => (p, 1))).reduceByKey(_ + _).filter(basket => basket._2 >= s)
    val partial = partialRdd.collect()
    if(partial.size == 0) {
      return Array()
    }
    val results = findFreqItemsPair(all, s, 1, partial.map(s => Set(s._1)), partial.map(s => s._1))
    results ++ partial.map(s => (ArrayBuffer(s._1), s._2))
  }

  def time[R](block: => R): R = {
    val t0 = System.currentTimeMillis()
    val result = block // call-by-name
    val t1 = System.currentTimeMillis()
    println("Elapsed time: " + (t1 - t0) + "ms")
    result
  }

}