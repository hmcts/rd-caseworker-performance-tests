package uk.gov.hmcts.reform.CRD.performance.scenarios.utils

import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.CRD.performance.scenarios.utils.Environment._

object  S2SHelper {

  val getOTP =
    exec(
      session => {
        val otp: String = String.valueOf(new GoogleAuthenticator().getTotpPassword(s2sSecret))
        session.set("OTP", otp)

      })

  val otpp="${OTP}"

  val S2SAuthToken =

    exec(http("Token_020_GetServiceToken")
      .post(s2sURL+"/lease")
      .header("Content-Type", "application/json")
      .body(StringBody(
        s"""{
       "microservice": "${s2sService}"
        }"""
      )).asJson
      .check(bodyString.saveAs("s2sToken"))
      .check(bodyString.saveAs("responseBody")))
      .pause(2)
}
