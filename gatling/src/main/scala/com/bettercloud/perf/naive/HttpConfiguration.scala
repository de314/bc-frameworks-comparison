package com.bettercloud.perf.naive

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object ExpressConfiguration {

  val conf = http
    .baseURL("http://localhost:13031")
    .acceptHeader("application/json")

}

object SpringConfiguration {

  val conf = http
    .baseURL("http://localhost:13030")
    .acceptHeader("application/json")

}
