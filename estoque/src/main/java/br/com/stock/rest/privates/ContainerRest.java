package br.com.stock.rest.privates;

import javax.ws.rs.Path;

import br.com.stock.object.Container;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.ContainerService;

@Path("/container")
public class ContainerRest extends RestAbstract<Container, Integer, ContainerService>{}
