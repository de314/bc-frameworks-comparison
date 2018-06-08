package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import scala.concurrent.duration._

class NaiveSpringBootSimulation extends Simulation {

  setUp(
    NaiveScenario
      .readWriteScen("spring")
      .inject(NaiveInjectionSteps.steps)
  ).protocols(SpringConfiguration.conf)

}
