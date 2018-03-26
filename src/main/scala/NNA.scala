package nearestneighbouralgorithm

import java.awt.Color
import java.lang.Math._

case class Point(x: Double, y: Double, color: Color)

object NNA {

  val N = 1000
  val M = 1000

  val (mu, sigma) = (0.0, 1.5)

  val red = (1 to N).map(_ => getGenPoint(1.0, 1.0, Color.RED)).toList
  val blue = (1 to N).map(_ => getGenPoint(4.0, 5.0, Color.BLUE)).toList
  val green = (1 to N).map(_ => getGenPoint(7.0, 3.0, Color.GREEN)).toList
  val points = red ::: blue ::: green

  val redLive = (1 to M).map(_ => getGenPoint(1.0, 1.0, Color.RED)).toList
  val blueLive = (1 to M).map(_ => getGenPoint(4.0, 5.0, Color.BLUE)).toList
  val greenLive = (1 to M).map(_ => getGenPoint(7.0, 3.0, Color.GREEN)).toList
  val pointsLive = redLive ::: blueLive ::: greenLive

  val point = getGenPoint(4.0, 3.0, Color.BLACK)

  val K = optimalK

  def main(args: Array[String]): Unit = {
    Graph.paintPoints(red, red.head.color)
    Graph.paintPoints(blue, blue.head.color)
    Graph.paintPoints(green, green.head.color)

    val paintedLines = points sortBy (p => sqrt((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y))) take 100
    Graph.paintPoints(List(point), point.color)
    Graph.paintLines(point, paintedLines)

    val successfulExperiments = pointsLive.count(p => experiment(p) == p.color)
    println(s"Sigma = $sigma")
    println(s"K = $K")
    println(s"Frequency of success = ${1.0 * successfulExperiments / pointsLive.size}")

  }

  def getGenPoint(x0: Double, y0: Double, color: Color) = Point(
    x0 + sigma * (sqrt(-2 * log(random)) * cos(2 * PI * random) + mu),
    y0 + sigma * (sqrt(-2 * log(random)) * sin(2 * PI * random) + mu),
    color)

  def optimalForK(k: Int) = {
    1.0 * points.indices.count(l => {
      val currentPoint = points.apply(l)
      val currentPoints = points.zipWithIndex.filterNot(_._2 == l).map(_._1)

      val list = currentPoints sortBy (p => sqrt((p.x - currentPoint.x) * (p.x - currentPoint.x) + (p.y - currentPoint.y) * (p.y - currentPoint.y))) take k
      List(Color.RED -> list.filter(_.color == Color.RED).map(p => 1.0 / ((p.x - currentPoint.x) * (p.x - currentPoint.x) + (p.y - currentPoint.y) * (p.y - currentPoint.y))).sum,
        Color.BLUE -> list.filter(_.color == Color.BLUE).map(p => 1.0 / ((p.x - currentPoint.x) * (p.x - currentPoint.x) + (p.y - currentPoint.y) * (p.y - currentPoint.y))).sum,
        Color.GREEN -> list.filter(_.color == Color.GREEN).map(p => 1.0 / ((p.x - currentPoint.x) * (p.x - currentPoint.x) + (p.y - currentPoint.y) * (p.y - currentPoint.y))).sum).maxBy(_._2)._1 == currentPoint.color
    }) / points.size
  }

  def optimalK = {
    var (k1, k2, k3) = (10, (10 + points.size) / 2, points.size)
    var (freq1, freq2, freq3) = (optimalForK(k1), optimalForK(k2), optimalForK(k3))
    while (k3 - k1 > N / 25) {
      freq1 max freq2 max freq3 match {
        case value if value == freq1 =>
          k3 = k2
          freq3 = freq2
          k2 = (k1 + k3) / 2
          freq2 = optimalForK(k2)
        case value if value == freq2 =>
          k1 = (k1 + k2) / 2
          k3 = (k3 + k2) / 2
          freq1 = optimalForK(k1)
          freq3 = optimalForK(k3)
        case value if value == freq3 =>
          k1 = k2
          freq1 = freq2
          k2 = (k1 + k3) / 2
          freq2 = optimalForK(k2)
      }
    }
    k2
  }

  def experiment(point: Point): Color = {
    val list = points sortBy (p => sqrt((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y))) take K
    List(Color.RED -> list.filter(_.color == Color.RED).map(p => 1.0 / ((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y))).sum,
      Color.BLUE -> list.filter(_.color == Color.BLUE).map(p => 1.0 / ((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y))).sum,
      Color.GREEN -> list.filter(_.color == Color.GREEN).map(p => 1.0 / ((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y))).sum).maxBy(_._2)._1
  }
}
