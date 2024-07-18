package com.hg.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "myk8s")
public class MyK8sConfiguration 
{
	
	private MyUser user;
	
	private String location;

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}

record MyUser (String user_name, String last_name, String password) {
	
}