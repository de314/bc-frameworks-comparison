package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import scala.concurrent.duration._

class NaiveSpringBootSimulation extends Simulation {

  setUp(
    NaiveScenario
      .scen("spring")
      .inject(
        constantUsersPerSec(5) during (60 seconds),
        rampUsersPerSec(5) to 10 during (30 seconds),
        constantUsersPerSec(10) during (30 seconds)
      )
  ).protocols(SpringConfiguration.conf)

}
