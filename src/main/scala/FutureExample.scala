package piggybox

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

object FutureExample {
  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrotheMilk = String
  type Espresso = String
  type Cappuccino = String

  case class Water(temperature: Int)

  case class GrindingException(msg: String) extends Exception(msg)

  case class FrothingException(msg: String) extends Exception(msg)

  case class WaterBoilingException(msg: String) extends Exception(msg)

  case class BrewingException(msg: String) extends Exception(msg)

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground cofee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println(" heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println(" hot, it's hot!")
    water.copy(temperature = 85)
  }

  grind("baked beans").onComplete {
    case Success(ground) => println(s"got my $ground")
    case Failure(ex) => println("This grinder needs a replacement, seriously!")
  }

  def prepareCappuccino(): Future[ Cappuccino] = {
    val groundCoffee = grind(" arabica beans")
    val heatedWater = heatWater( Water( 20))
    val frothedMilk = frothMilk(" milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew( ground, water)
    } yield combine( espresso, foam)
  }
}



