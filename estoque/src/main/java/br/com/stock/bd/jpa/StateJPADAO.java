package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.StateDAO;
import br.com.stock.object.State;

public class StateJPADAO extends JPAAbstract<State,Integer> implements StateDAO{}
