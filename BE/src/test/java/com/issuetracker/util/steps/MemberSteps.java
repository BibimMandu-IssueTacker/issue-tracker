package com.issuetracker.util.steps;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.issuetracker.member.ui.dto.MemberUpdateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

public class MemberSteps {

	public static ExtractableResponse<Response> 회원_정보_조회_요청(long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/members/{id}", id)
			.then().log().all().extract();
	}
}
