package me.berout

import scala.io.StdIn

object Berout {

  private val routeQuery = """route (\w+) -> (\w+)""".r

  def main(args: Array[String]): Unit = {
    // parse network spec
    val network = new Graph
    val edgeCount = StdIn.readInt
    for (i <- 1 to edgeCount) {
      val e = StdIn.readf3("{0} -> {1}: {2, number}")
      network.add(Edge(
        e._1.asInstanceOf[String],
        e._2.asInstanceOf[String],
        e._3.asInstanceOf[Long].toInt))
    }

    // process queries
    var line: String = null
    while ({line = StdIn.readLine; line != null}) {
      line match {
        case routeQuery(start, target) => {
          // compute shortest path
          val result = GraphTool.shortestPath(network, start, target)

          // format result to output
          var total = 0
          print(result.head.source)
          for (e <- result) {
            print(s" -> ${e.destination}")
            total += e.weight
          }
          println(s": ${total}")
        }
      }
    }
  }
}

