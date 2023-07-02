package net.javaguides.sms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.javaguides.sms.entity.Product;
import net.javaguides.sms.repository.ProductRepositry;


public class GetViewController {
private ProductRepositry productRepo;
	
	@RequestMapping(value = "/addProduct",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView returnAddProduct()
	{
	  ModelAndView mv = new ModelAndView();
	  mv.setViewName("addProduct");
	  //mv.addObject("var", "halim");
	  return mv;  
	}
	
	@RequestMapping(value = "/listProduct",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView returnListProduct()
	{
	  ModelAndView mv = new ModelAndView();
	  List<Product> products = productRepo.findAll();
	  mv.setViewName("listProducts");
	  mv.addObject("products", products);
	  return mv;
	  
	}
}
