package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class NaiveExpressSimulation extends Simulation {

  val springBootConf = http
    .baseURL("http://localhost:13031")
    .acceptHeader("application/json")

  val request = http("naive_endpoint").get("/read")

  val scn = scenario("Read Naive").exec(request)

  setUp(
    scn.inject(constantUsersPerSec(100) during (10 seconds))
  ).protocols(springBootConf)

}
