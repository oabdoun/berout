package me.berout

import scala.collection.mutable.HashMap

object GraphTool {
  def shortestPath(graph: Graph, start: String, target: String): Seq[Edge] = {
    val costs = HashMap(start -> 0)
    val paths: HashMap[String, Seq[Edge]] = HashMap(start -> Seq.empty)
    var waypoints = List(start)
    while (!waypoints.isEmpty) {
      for (next <- graph.outEdges(waypoints.head)) {
        if (!costs.isDefinedAt(next.destination)
            || (costs(next.destination) > costs(next.source) + next.weight)) {
          costs += (next.destination -> (costs(next.source) + next.weight))
          paths += (next.destination -> (paths(next.source) :+ next))
          waypoints = waypoints :+ next.destination
        }
      }
      waypoints = waypoints.tail
    }
    paths.getOrElse(target, Seq.empty[Edge])
  }
}

