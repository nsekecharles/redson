package com.sidemash.redson.scalatest

import org.scalatest._

abstract class UnitSpec extends FlatSpec
  with Matchers with OptionValues with Inside with Inspectors