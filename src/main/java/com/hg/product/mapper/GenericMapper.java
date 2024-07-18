package com.hg.product.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.product.annotations.Since;

public class GenericMapper {
	
	private static final Logger LOG = LoggerFactory.getLogger(GenericMapper.class);

	@Since(version = "Old Version")
	public String jsonAsString(Object in) 
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(in);
		} catch (JsonProcessingException e) {
			LOG.error("At GenericMapper::jsonAsString exception occurred when object:{} transform", in);
		}
		return null;
	}
}
