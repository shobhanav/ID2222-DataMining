package se.kth.spark.assignment3

class HyperLogLogCounter {

  val b: Int = 4
  val a: Double = 0.673 // value depends on b

  val counter: Array[Int] = Array.fill[Int](scala.math.pow(2, b).toInt)(0)


  def add(item: Int) = {
    //find bucket index which is identified by the first b bits
    val index = item >>> (32 - b)
    val w = item << b
    counter(index) = scala.math.max(counter(index), Integer.numberOfLeadingZeros(w) + 1)
  }

  def union(N: HyperLogLogCounter) = {
    var i = 0
    counter.foreach((x) => {
      counter(i) = scala.math.max(x, N.counter(i))
      i += 1
    })
  }

  def copy(): HyperLogLogCounter = {
    var i = 0
    val c = new HyperLogLogCounter
    counter.foreach((x) => {
      c.counter(i) = x
      i += 1
    })
    c
  }


  def getCardinality(): Double = {
    val size = counter.size
    val Z = 1 / (counter.map(x => (1 / scala.math.pow(2, x))).reduce(_ + _))
    return a * size * size * Z
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[HyperLogLogCounter]

  override def equals(other: Any): Boolean = other match {
    case that: HyperLogLogCounter =>
      (that canEqual this) &&
        counter.sameElements(that.counter)
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(counter)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}