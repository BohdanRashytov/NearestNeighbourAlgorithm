package nearestneighbouralgorithm

import java.awt.{Color, Frame}
import javax.swing.JFrame

import org.math.plot.Plot2DPanel

object Graph {
  val plot: Plot2DPanel = new Plot2DPanel()
  val frame: JFrame = new JFrame("Plot")
  frame.setExtendedState(Frame.MAXIMIZED_BOTH)
  frame.setVisible(true)
  frame.setContentPane(plot)

  def paintLines(point: Point, points: List[Point]) = plot.addLinePlot(s"Line", Color.BLACK, points.flatMap(p =>
    List(p, point)).map(_.x).toArray, points.flatMap(p => List(p, point)).map(_.y).toArray)

  def paintPoints(points: List[Point], color: Color) = plot.addScatterPlot("Points", color,
    points.map(_.x).toArray, points.map(_.y).toArray)
}