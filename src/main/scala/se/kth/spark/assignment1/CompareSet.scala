package se.kth.spark.assignment1

import scala.collection.immutable.Set

class CompareSet {
  def computeJaccardSimilarity(set1: Set[(Int)], set2: Set[(Int)]) : Double = {
    return (set1.intersect(set2).size) / (set1.union(set2).size).toDouble
  }
}