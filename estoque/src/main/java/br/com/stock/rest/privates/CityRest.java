package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.City;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.CityService;

@Path("/city")
public class CityRest extends RestAbstract<City,Integer,CityService>{}
