
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.collection.Size
import eu.timepit.refined.numeric._
import eu.timepit.refined.numeric.Interval
import eu.timepit.refined.refineV
import shapeless.Nat._1

object CustomUppercaseStringWithMaxCharacters {
  case class StringUppercase()
  implicit val validation: Validate.Plain[String, StringUppercase] = Validate.fromPredicate(s => s.toUpperCase().equals(s), t => s"$t is not uppercased", StringUppercase())

  type AUppercaseStringWithMaxCharacters[C] = Size[Interval.Closed[_1, C]] And StringUppercase
  def main(args: Array[String]): Unit = {

    //runtime time
    val stringUppercase = refineV[AUppercaseStringWithMaxCharacters[3]]("CIAOOOO")
    println(stringUppercase)
  }
}
