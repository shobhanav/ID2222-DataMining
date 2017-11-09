package se.kth.spark.assignment1

import org.apache.spark.rdd.RDD 

class CompareSet {
  def computeJaccardSimilarity(set1: RDD[(Int)], set2: RDD[(Int)]) : Double = {
    
    val jaccardSim = (set1.intersection(set2).count())/(set1.union(set2).count())
    return jaccardSim
  }
}