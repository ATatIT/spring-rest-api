package com.projectStruct.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningAPI {
	
	//By URL
	@GetMapping("/v1/person")
	public PersonV1 firstVersionofPerson() {
		return new PersonV1("Abhishek Thesiya");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 secondVersionofPerson() {
		return new PersonV2(new Name("Abhishek","Thesiya"));
	}
	
	//By Request Parameter
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 firstVersionofPersonRequestParam() {
		return new PersonV1("Abhishek Thesiya");
	}
	
	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 secondVersionofPersonRequestParam() {
		return new PersonV2(new Name("Abhishek","Thesiya"));
	}
	
	//By Header
	@GetMapping(path = "/person/header", headers = "X-API-VERSIONS=1")
	public PersonV1 firstVersionofPersonRequestHeader() {
		return new PersonV1("Abhishek Thesiya");
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSIONS=2")
	public PersonV2 secondVersionofPersonRequestHeader() {
		return new PersonV2(new Name("Abhishek","Thesiya"));
	}
	
	//By media type
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 firstVersionofPersonAcceptHeader() {
		return new PersonV1("Abhishek Thesiya");
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
	public PersonV2 secondVersionofPersonAcceptHeader() {
		return new PersonV2(new Name("Abhishek","Thesiya"));
	}
	
}
