package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.LogStockDAO;
import br.com.stock.object.LogStock;

public class LogStockJPADAO extends JPAAbstract<LogStock,Integer> implements LogStockDAO{}
