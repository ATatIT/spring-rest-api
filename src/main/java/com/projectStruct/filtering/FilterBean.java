package com.projectStruct.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties("field1")  //way 1 Static filtering
@JsonFilter("FilterBeanFilter") // Dynamic filtering
public class FilterBean {
	private String field1;
	
	//@JsonIgnore   // way 2 Static filtering
	private String field2;
	
	private String field3;

	public FilterBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}

	@Override
	public String toString() {
		return "FilterBean [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
	
	
}
