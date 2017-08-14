package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.Category;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.CategoryService;

@Path("/category")
public class CategoryRest extends RestAbstract<Category, Integer, CategoryService>{}
