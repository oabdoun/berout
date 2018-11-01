package me.berout

import scala.io.StdIn

/** The Berlin Routing CLI application */
object Berout {

  private val routeQuery = """route (\w+) -> (\w+)""".r
  private val nearbyQuery = """nearby (\w+), (\d+)""".r

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

          // no route
          if (result.isEmpty) {
            println(s"Error: No route from ${start} to ${target}")
            return
          }

          // format result to output
          var total = 0
          print(result.head.source)
          for (e <- result) {
            print(s" -> ${e.destination}")
            total += e.weight
          }
          println(s": ${total}")
        }

        case nearbyQuery(start, max) => {
          // select paths by max cost
          val result = GraphTool.selectPathsByMaxCost(network, start, max.toInt)

          // compute cost for each path
          var nearby = new scala.collection.mutable.HashMap[String, Int]
          for (path <- result) {
            val cost = path.map(_.weight).foldLeft(0)(_ + _)
            nearby += (path.last.destination -> cost)
          }

          // output result sorted by cost
          val dests = nearby.toSeq
          .sortWith((a, b) => a._2 < b._2)
          .map(n => s"${n._1}: ${n._2}")
          println(dests.mkString(", "))
        }

      }
    }
  }
}

