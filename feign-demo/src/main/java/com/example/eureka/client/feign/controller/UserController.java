package com.example.eureka.client.feign.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.eureka.client.feign.service.user.UserServiceFeignClient;
import com.example.eureka.client.feign.service.user.rest.UserServiceRestClient;
import com.example.eureka.client.feign.vo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserServiceFeignClient userServiceFeignClient;
	
	@Autowired
	private UserServiceRestClient userServiceRestClient;
	
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") })
	@RequestMapping(value = "/user-feign/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable int id) {
		return userServiceFeignClient.getUser(id);
	}
	
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") })
	@RequestMapping(value = "/user-feign/getUserByName/{name}", method = RequestMethod.GET)
	public User getUserByName(@PathVariable String name) {
		return userServiceFeignClient.findUserByName(name);
	}
	
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") })
	@RequestMapping(value = "/user-rest/{id}", method = RequestMethod.GET)
	public User getUserByRest(@PathVariable int id) {
		return userServiceRestClient.getUser(id);
	}
	
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") })
	@RequestMapping(value = "/user-feign/getUserByAddress/{address}", method = RequestMethod.GET)
	public String getUserByAddress(@PathVariable String address) {
		return userServiceFeignClient.findUserByAddress(address);
	}
}
