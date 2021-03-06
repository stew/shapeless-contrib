package shapeless.contrib.scalaz

import org.specs2.scalaz.Spec

import scalaz._
import scalaz.scalacheck.ScalazArbitrary._

import shapeless._

class SequenceTest extends Spec {

  import scalaz.std.option._
  import scalaz.std.string._
  import scalaz.syntax.apply._

  "sequencing Option" ! prop { (x: Option[Int], y: Option[String], z: Option[Float]) =>
    sequence(x :: y :: z :: HNil) must_== ((x |@| y |@| z) { _ :: _ :: _ :: HNil })
  }

  "sequencing Validation" ! prop { (x: ValidationNel[String, Int], y: ValidationNel[String, String], z: ValidationNel[String, Float]) =>
    sequence(x :: y :: z :: HNil) must_== ((x |@| y |@| z) { _ :: _ :: _ :: HNil })
  }

  "sequencing Kleisli" ! prop { (x: Kleisli[Option, Int, String], y: Kleisli[Option, Int, Boolean], z: Kleisli[Option, Int, String], i: Int) =>
    sequence(x :: y :: z :: HNil).apply(i) must_== (((x |@| y |@| z) { _ :: _ :: _ :: HNil }).apply(i))
  }

  type ErrorsOr[+A] = ValidationNel[String, A]

  "sequencing Kleisli of ValidationNel" ! prop { (x: Kleisli[ErrorsOr, Int, String], y: Kleisli[ErrorsOr, Int, Boolean], z: Kleisli[ErrorsOr, Int, String], i: Int) =>
    sequence(x :: y :: z :: HNil).apply(i) must_== (((x |@| y |@| z) { _ :: _ :: _ :: HNil }).apply(i))
  }

}

// vim: expandtab:ts=2:sw=2
