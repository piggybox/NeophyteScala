package piggybox

trait User {
    def name: String
}

class FreeUser(val name: String) extends User

class PremiumUser(val name: String) extends User

object FreeUser {
    def unapply(user: FreeUser): Option[String] = Some(user.name)
}

object PremiumUser {
    def unapply(user: PremiumUser): Option[String] = Some(user.name)
}

object Extractor {
  def main(args: Array[String]) {
    val user: User = new PremiumUser("Tom")

    user match {
        case FreeUser(name) => print("Hello " + name)
        case PremiumUser(name) => print("Welcome back, dear " + name)
    }
  }
}

