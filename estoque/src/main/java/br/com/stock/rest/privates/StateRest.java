package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.State;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.StateService;

@Path("/state")
public class StateRest extends RestAbstract<State,Integer,StateService>{}
