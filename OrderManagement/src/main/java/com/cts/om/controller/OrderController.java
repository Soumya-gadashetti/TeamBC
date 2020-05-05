package com.cts.om.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.om.entities.Order;
import com.cts.om.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/order")
@Api(value = "The Order Controller", description = "Rest controller for order")
public class OrderController {
	@Autowired
	OrderService service;
	
	@ApiOperation(value = "Get all orders",
			produces = "A list of orders",
			notes = "Hit this URL to get all orders details"
			)
	@RequestMapping("/all")
	List<Order> getOrder(){
		return service.getorders();
	}
	
	@ApiOperation(value = "Add a order",
			consumes = "A new order is JSON",
			notes = "Hit this URL to insert a new order details"
			)
	
	@RequestMapping(method = RequestMethod.POST, value="/add")
	void addOrder(@RequestBody Order order) {
		service.addOrder(order);
	}
	
	@ApiOperation(value = "Update a order details",
			consumes = "An existing order in JSON",
			notes = "Hit this URL to update a order details"
			)
	@RequestMapping(method = RequestMethod.PUT,value="/update")
	void updateOrder(@RequestBody Order order) {
		service.updateOrder(order);
	}
	
	@ApiOperation(value = "Delete a order",
			consumes = "An existing order id",
			notes = "Hit this URL to delete a order details"
			)
	@RequestMapping(method = RequestMethod.DELETE, value="/deleteId")
	void deleteOrder(@PathVariable Long orderId) {
		service.deleteOrder(orderId);
	}
	
	@GetMapping("/default")
	@HystrixCommand(fallbackMethod="getOrder")
	public List<Order> retrieveConfiguration() {
		throw new RuntimeException("Not available");
	}
}
