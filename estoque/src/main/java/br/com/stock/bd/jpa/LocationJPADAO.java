package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.LocationDAO;
import br.com.stock.object.Location;

public class LocationJPADAO extends JPAAbstract<Location, Integer> implements LocationDAO{}
