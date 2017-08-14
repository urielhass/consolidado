package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.Location;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.LocationService;

@Path("/location")
public class LocationRest extends RestAbstract<Location,Integer,LocationService>{}
