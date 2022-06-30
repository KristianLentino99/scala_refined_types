import eu.timepit.refined.api.Refined
import eu.timepit.refined.refineV
object CustomPredicates {
  case class Point(x: Int, y: Int)
  case class Quadrant1()
  case class Quadrant2()
  case class Quadrant3()
  case class Quadrant4()
  import eu.timepit.refined.api.Validate

  implicit val quadrant1Validate: Validate.Plain[Point, Quadrant1] =
    Validate.fromPredicate(p => p.x >= 0 && p.y >= 0, p => s"($p is not quadrant 1)", Quadrant1())

  implicit val quadrant2Validate: Validate.Plain[Point, Quadrant2] =
    Validate.fromPredicate(p => p.x < 0 && p.y >= 0, p => s"($p is not quadrant 2)", Quadrant2())

  implicit val quadrant3Validate: Validate.Plain[Point, Quadrant3] =
    Validate.fromPredicate(p => p.x < 0 && p.y < 0, p => s"($p is not quadrant 3)", Quadrant3())

  implicit val quadrant4Validate: Validate.Plain[Point, Quadrant4] =
    Validate.fromPredicate(p => p.x >= 0 && p.y < 0, p => s"($p is not quadrant 4)", Quadrant4())

  type InsideQuadrant2 = Point Refined Quadrant2
  def main(args: Array[String]): Unit = {

    //runtime
    val verifiedPointInsiderFirstQuadrant = refineV[Quadrant1](Point(1, 3))
    val invalidPointInsiderFirstQuadrant = refineV[Quadrant1](Point(1, -3))
    println(verifiedPointInsiderFirstQuadrant)
    println(invalidPointInsiderFirstQuadrant)

  }
}
