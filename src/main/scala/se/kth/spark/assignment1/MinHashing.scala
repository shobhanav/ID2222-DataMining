package se.kth.spark.assignment1

class MinHashing(sigLen: Int) extends Serializable{
  val r = scala.util.Random
  val prime = 4294975753L
  val coefA = generateCoef(sigLen, Int.MaxValue)
  val coefB = generateCoef(sigLen, Int.MaxValue)

  def generateCoef(n: Int, m: Int): IndexedSeq[Int] = {
    return for (i <- 1 to n) yield r.nextInt(m)
  }

  def sig(shingles: List[Int]): Array[BigInt] = {

    val sigInit = Array.fill[BigInt](coefA.length)(prime + 1)
    return shingles.aggregate(sigInit)((sigTmp, shingle) => {
      for (i <- 0 to coefA.length - 1) {
        val a = coefA(i);
        val b = coefB(i);

        val hash = BigInt(a * shingle + b) % prime
        if (sigTmp(i) > hash) {
          sigTmp(i) = hash
        }
      }
      //println(sigTmp mkString ",")
      sigTmp
    }, (acc1, acc2) => {
      for (i <- 0 to acc1.length - 1) {
        if (acc1(i) > acc2(i)) {
          acc1(i) = acc2(i)
        }
      }
      acc1
    })
  }
}

/*
Usage:
val minHashing = new MinHashing(8)
println(minHashing.sig(List(3, 4, 5, 6, 90)) mkString ",")
println(minHashing.sig(List(3, 4, 6, 4, 7)) mkString ",")
*/