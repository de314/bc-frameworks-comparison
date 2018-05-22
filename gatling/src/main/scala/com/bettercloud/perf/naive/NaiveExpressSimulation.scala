package com.bettercloud.perf.naive

import io.gatling.core.Predef._

class NaiveExpressSimulation extends Simulation {

  setUp(
    NaiveScenario
      .readWriteScen("express")
      .inject(NaiveInjectionSteps.steps)
  ).protocols(ExpressConfiguration.conf)

}
