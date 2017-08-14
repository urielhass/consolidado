package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.Unit;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.UnitService;

@Path("/unit")
public class UnitRest extends RestAbstract<Unit,Integer,UnitService>{}
