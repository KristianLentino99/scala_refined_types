import eu.timepit.refined.api.{Min, RefType, Refined, Validate}
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.Size
import eu.timepit.refined.numeric._
import eu.timepit.refined.string.{MatchesRegex, Trimmed, Uuid}
import eu.timepit.refined.{W, refineMV}
import eu.timepit.refined.boolean.{And, Not}
import eu.timepit.refined.numeric.Interval.{Closed, ClosedOpen, Open}
import eu.timepit.refined.types.string.{NonEmptyFiniteString, NonEmptyString}
import shapeless.Nat._1

object Main {
  val aPositiveInteger: Refined[Int, Positive] = 42
  type Email = String Refined MatchesRegex[W.`"""([\\w\\.!#$%&*+/=?^_{|}~-]+)@([\\w]+)([\\.]{1}[\\w]+)+"""`.T]
  type ValidName = String Refined MatchesRegex[W.`"""[A-Z][a-z]+"""`.T]
  type Province = String  Refined MatchesRegex[W.`"""[A-Z]{2}+"""`.T]
  /**
   * Closed will include the two maximum and minimum values
   *
   */
  type ClassicPercentage = Int Refined Closed[W.`0`.T,W.`100`.T]
  type UserUUID = String Refined Uuid
  type AmountInvested = Int Refined NonNegative
  type AStringWithMaxCharacters[C] = NonEmptyFiniteString[C]
  type ATrimmedStringWithMaxCharacters[C] =  Size[Interval.Closed[_1, C]] And Trimmed
  //type AUppercaseStringWithMaxCharacters[C] = Size[Interval.Closed[_1, C]] And StringUppercase
  type ZeroToOne = Not[Less[0.0]] And Not[Greater[1.0]]
  case class User(name: ValidName, email: Email)
  case class City(name: ValidName, province: Province)


  def main(args: Array[String]): Unit = {
    emailExamples()
    provinceExamples()
    //this won't compile
    //val invalidZeroToHundred: ClassicPercentage = 200
    //this will compile
    val zeroToHundred: ClassicPercentage = 100
    //val invalidUUID: UserUUID = "1"
    val validUUID: UserUUID = "7f56f356-23e2-4359-81da-83c9f02970cc"

    //this wont compile
    //val invalidAmountInvested: AmountInvested = -100
    val amountInvestedPositive: AmountInvested = 100
    val aStringWithMaxThreeCharacters: AStringWithMaxCharacters[3] = "ABC"
    val aStringWithMaxOneCharacter: AStringWithMaxCharacters[1] = "A"
    val aTrimmedStringWithMaxCharacters: Refined[String, ATrimmedStringWithMaxCharacters[2]] =
      refineMV[ATrimmedStringWithMaxCharacters[2]]("CI")


    println(s"aTrimmedStringWithMaxCharacters: $aTrimmedStringWithMaxCharacters")
  }

  def provinceExamples(): Unit = {
    val milano =  City("Milano", "MI")
  }
  def emailExamples(): Unit = {
    val validUser: User = new User("Kristian", "kristian.lentino@moneyfarm.com")
    //val invalidEmailUser: User = new User("Kristian", "kristian.lentino@moneyfarmcom")
    //val invalidNameUser: User = new User("Kristian99", "kristian.lentino@moneyfarmcom")

    //refining at runtime
    val poorEmail = "kristian"
    val refineCheck = RefType.applyRef[Email](poorEmail)

    refineCheck match {
      case Right(email: Email) =>
        println(s"$email it's a valid email!")
      case Left(value) =>
        println(s"$value it's not a valid email!")
    }

    val validEmail = "kristian@moneyfarm.com"
    val validRefineCheck = RefType.applyRef[Email](validEmail)

    validRefineCheck match {
      case Right(email: Email) =>
        println(s"$email it's a valid email!")
      case Left(value) =>
        println(s"$value it's not a valid email!")
    }
  }
}