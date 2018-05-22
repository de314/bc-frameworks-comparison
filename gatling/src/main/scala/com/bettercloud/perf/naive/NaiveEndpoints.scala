package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

object NaiveEndpoints {

  val read = exec(http("read").get("/read"))
  val write = exec(http("write").get("/write"))
  val cpu = exec(http("cpu").get("/cpu"))
  val noop = exec(http("noop").get("/noop"))

}

/**
  * Runs
  */
object NaiveScenario {

  def scen(name: String): ScenarioBuilder = {
    scenario(name)
      .exec(NaiveEndpoints.read)
      .pause(5)
      .exec(NaiveEndpoints.write)
      .pause(5)
      .exec(NaiveEndpoints.cpu)
      .pause(5)
      .exec(NaiveEndpoints.noop)
      .pause(5)
  }

}
