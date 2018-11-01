package me.berout

import scala.collection.mutable.HashMap

case class Path(path: Seq[Edge], cost: Int) {}

object GraphTool {
  private val emptyPath = new Path(Seq.empty, 0)

  def shortestPath(graph: Graph, start: String, target: String): Seq[Edge] = {
    val costs = HashMap(start -> 0)
    val paths: HashMap[String, Path] = HashMap(start -> new Path(Seq.empty, 0))
    var waypoints = List(start)
    while (!waypoints.isEmpty) {
      for (next <- graph.outEdges(waypoints.head)) {
        if (!paths.isDefinedAt(next.destination)
            || (paths(next.destination).cost > paths(next.source).cost + next.weight)) {
          paths += (next.destination -> new Path(
            paths(next.source).path :+ next,
            paths(next.source).cost + next.weight
          ))
          waypoints = waypoints :+ next.destination
        }
      }
      waypoints = waypoints.tail
    }
    paths.getOrElse(target, emptyPath).path
  }
}

