package com.projectStruct.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringAPI {
	

	@GetMapping("/filtering")
	public MappingJacksonValue filterApi() {
		FilterBean filterBean = new FilterBean("value1", "value2", "value3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterBean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("FilterBeanFilter", filter );
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	//Dynamic filtering
	@GetMapping("/filtering-list")
	public MappingJacksonValue filterApi2() {
		
		List<FilterBean> list = Arrays.asList((new FilterBean("value1", "value2", "value3")),
				new FilterBean("value4", "value5", "value6"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("FilterBeanFilter", filter );
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
}
