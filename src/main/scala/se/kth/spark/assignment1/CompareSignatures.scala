package se.kth.spark.assignment1

import scala.collection.immutable.Set

class CompareSignatures {
  def compare(sig1:Array[BigInt], sig2:Array[BigInt]): Double = { 
    return sig1.toSet.intersect(sig2.toSet).size.toDouble / sig1.toSet.size
  }
}