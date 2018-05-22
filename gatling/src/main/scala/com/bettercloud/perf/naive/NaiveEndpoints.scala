package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.concurrent.duration._

object NaiveEndpoints {

  def read(name: String): ChainBuilder = exec(
    http(s"read-$name")
      .get("/read")
  )

  def write(name: String): ChainBuilder = exec(
    http(s"write-$name")
      .get("/write")
  )

  def cpu(name: String): ChainBuilder = exec(
    http(s"cpu-$name")
      .get("/cpu")
  )

  def noop(name: String): ChainBuilder = exec(
    http(s"noop-$name")
      .get("/noop")
  )

}

object NaiveInjectionSteps {

  val steps: Iterable[InjectionStep] = Iterable(
    constantUsersPerSec(10) during (30 seconds),
    rampUsersPerSec(10) to 30 during (30 seconds)
  )

}

/**
  * Runs
  */
object NaiveScenario {

  def readWriteScen(name: String): ScenarioBuilder = {
    scenario(name).randomSwitch(
      90.0 -> NaiveEndpoints.read(name),
      10.0 -> NaiveEndpoints.write(name)
    )
  }

  def allScen(name: String): ScenarioBuilder = {
    scenario(name)
      .exec(NaiveEndpoints.read(name))
      .pause(5)
      .exec(NaiveEndpoints.write(name))
      .pause(5)
      .exec(NaiveEndpoints.cpu(name))
      .pause(5)
      .exec(NaiveEndpoints.noop(name))
      .pause(5)
  }

}
