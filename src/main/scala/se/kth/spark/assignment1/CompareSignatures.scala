package se.kth.spark.assignment1

import scala.collection.immutable.Set

class CompareSignatures {

  def compare(sig1:Array[BigInt], sig2:Array[BigInt]): Double = {
    var count = 0
    
    for(i <- 0 to sig1.size-1){
      if(sig1(i) == sig2(i)) {
        count +=1
      }
    }
    return count.toDouble / sig1.size
  }
}