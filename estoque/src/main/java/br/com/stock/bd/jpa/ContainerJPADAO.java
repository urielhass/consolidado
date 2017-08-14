package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.ContainerDAO;
import br.com.stock.object.Container;

public class ContainerJPADAO extends JPAAbstract<Container, Integer> implements ContainerDAO{}
