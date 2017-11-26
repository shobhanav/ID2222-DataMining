package se.kth.spark.assignment3

import org.apache.spark.graphx._
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.HashMap

class HyperBall(graph: Array[(VertexId, Array[(VertexId, Int)])]) {
  val c: HashMap[Int, HyperLogLogCounter] = HashMap.empty[Int, HyperLogLogCounter]
  var t = 0


  graph.foreach((v) => {
    val id = v._1.toInt
    val counter = new HyperLogLogCounter()
    c += ((id, counter))
    counter.add(id)
  })

  def run() = {
    var changed = true
    while (changed) {
      val pairs: HashMap[Int, HyperLogLogCounter] = HashMap.empty[Int, HyperLogLogCounter]
      changed = false
      var a: HyperLogLogCounter = null
      graph.foreach((gr) => {
        val v = gr._1.toInt
        a = c(v).copy()
        pairs += ((v, a))
        gr._2.foreach((n) => {
          val w = n._1.toInt
          a.union(c(w))
        })
      })

      graph.foreach(ver => {
        val idx = ver._1.toInt

        if (!changed && !pairs(idx).equals(c(idx))) {
          changed = true
        }
        c(idx) = pairs(idx)
      })
      t += 1
      println("t: " + t)
    }
  }
}

object HyperBallMain {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ID2222 Assignment 3").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val graph = GraphLoader.edgeListFile(sc, "src/main/resources/ego-facebook/out.ego-facebook")
    val hb = new HyperBall(graph.collectNeighbors(EdgeDirection.Either).collect())
    hb.run()
  }

}