package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.exception.OrderNotFoundException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * TODO.
 *
 * @author Tamas_Knizner
 */
public class OrderController {

    @RequestMapping(value="/orders/{id}", method=GET, produces="application/json")
    @ResponseBody
    public Order showOrder(@PathVariable("id") long id) {
        if (id <= 0) throw new IllegalArgumentException("Order ID should be positive");
        Order order = new Order();
        order.id = id;
        if (order == null) throw new OrderNotFoundException(id);
        return order;
    }

    @ExceptionHandler({JSONException.class})
    public String jsonConversionError() {
        return "error";
    }

    private static class Order {
        long id;
    }

}
