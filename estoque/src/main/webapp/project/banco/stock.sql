-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Tempo de geração: 22/06/2017 às 01:40
-- Versão do servidor: 5.7.11-log
-- Versão do PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `stock`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `category`
--

CREATE TABLE `Category` (
  `Id` int(11) NOT NULL,
  `description` varchar(199) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `city`
--

CREATE TABLE `City` (
  `id` int(11) NOT NULL,
  `initials` varchar(3) NOT NULL,
  `name` varchar(45) NOT NULL,
  `state_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `city`
--

INSERT INTO `City` (`id`, `initials`, `name`, `state_id`) VALUES
(1, 'JOI', 'Joinville', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `container`
--

CREATE TABLE `Container` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `location`
--

CREATE TABLE `Location` (
  `id` int(11) NOT NULL,
  `note` varchar(99) DEFAULT NULL,
  `position` varchar(10) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `container_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `logstock`
--

CREATE TABLE `LogStock` (
  `id` int(11) NOT NULL,
  `dateLog` datetime DEFAULT NULL,
  `difference` int(11) DEFAULT NULL,
  `operation` varchar(1) DEFAULT NULL,
  `quantityNext` int(11) DEFAULT NULL,
  `quantityPrevious` int(11) DEFAULT NULL,
  `stock_Id` int(11) DEFAULT NULL,
  `user_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `request`
--

CREATE TABLE `Request` (
  `Id` int(11) NOT NULL,
  `Branch` int(11) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `costCenter` varchar(255) DEFAULT NULL,
  `cr` varchar(255) DEFAULT NULL,
  `dateDelivery` datetime DEFAULT NULL,
  `dateExit` datetime DEFAULT NULL,
  `dateRequest` datetime DEFAULT NULL,
  `documentNumber` int(11) NOT NULL,
  `emissionDate` datetime DEFAULT NULL,
  `entryDate` datetime DEFAULT NULL,
  `project` varchar(255) DEFAULT NULL,
  `reason` varchar(45) DEFAULT NULL,
  `scheduleDate` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `UserId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `requeststockinfo`
--

CREATE TABLE `RequestStockInfo` (
  `id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `StockId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `request_requeststockinfo`
--

CREATE TABLE `Request_RequestStockInfo` (
  `Request_Id` int(11) NOT NULL,
  `listInfo_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `state`
--

CREATE TABLE `State` (
  `id` int(11) NOT NULL,
  `initials` varchar(3) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `state`
--

INSERT INTO `State` (`id`, `initials`, `name`) VALUES
(1, 'SC', 'Santa Catarina');

-- --------------------------------------------------------

--
-- Estrutura para tabela `stock`
--

CREATE TABLE `Stock` (
  `Id` int(11) NOT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `cod` varchar(45) NOT NULL,
  `cpa` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `emissionDate` datetime DEFAULT NULL,
  `enterDateInvoice` datetime DEFAULT NULL,
  `invoice` int(11) NOT NULL,
  `invoiceResp` varchar(255) DEFAULT NULL,
  `minimum` int(11) NOT NULL,
  `name` varchar(99) DEFAULT NULL,
  `printShop` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `title` varchar(99) NOT NULL,
  `unitaryValue` double NOT NULL,
  `SubCategoryId` int(11) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `stock_location`
--

CREATE TABLE `Stock_Location` (
  `Stock_Id` int(11) NOT NULL,
  `listLocation_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `subcategory`
--

CREATE TABLE `SubCategory` (
  `Id` int(11) NOT NULL,
  `description` varchar(200) NOT NULL,
  `name` varchar(45) NOT NULL,
  `CategoryId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `unit`
--

CREATE TABLE `Unit` (
  `Id` int(11) NOT NULL,
  `branch` int(11) NOT NULL,
  `costCenter` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` bigint(20) DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `unit`
--

INSERT INTO `Unit` (`Id`, `branch`, `costCenter`, `name`, `phone`, `cityId`) VALUES
(1, 1, '123456', 'SENAI', 123456, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `user`
--

CREATE TABLE `User` (
  `Id` int(11) NOT NULL,
  `adm` bit(1) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `permission` int(11) NOT NULL,
  `phone` bigint(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `user`
--

INSERT INTO `user` (`Id`, `adm`, `email`, `name`, `password`, `permission`, `phone`, `status`) VALUES
(1, b'1', 'adm@adm.adm', 'Nao Remover esse usuário até ter criado outro', 'e10adc3949ba59abbe56e057f20f883e', 30, 123456, b'1');

-- --------------------------------------------------------

--
-- Estrutura para tabela `userhasunit`
--

CREATE TABLE `userHasUnit` (
  `userId` int(11) NOT NULL,
  `unitId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `userhasunit`
--

INSERT INTO `userHasUnit` (`userId`, `unitId`) VALUES
(1, 1);

--
-- Índices de tabelas apagadas
--

--
-- Índices de tabela `category`
--
ALTER TABLE `Category`
  ADD PRIMARY KEY (`Id`);

--
-- Índices de tabela `city`
--
ALTER TABLE `City`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK19lu7ofio5d2w879ch76hemmw` (`state_id`);

--
-- Índices de tabela `container`
--
ALTER TABLE `Container`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `location`
--
ALTER TABLE `Location`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoqk5vx9j2uu7ms0icaq5ps4vq` (`container_id`);

--
-- Índices de tabela `logstock`
--
ALTER TABLE `LogStock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoel2315wxffeod1lp4uhx9n3k` (`stock_Id`),
  ADD KEY `FK40lmn343ur5m2xulsbxqgrr1h` (`user_Id`);

--
-- Índices de tabela `request`
--
ALTER TABLE `Request`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FKebc60qntbd31p1vu50j1jvrm2` (`UserId`);

--
-- Índices de tabela `requeststockinfo`
--
ALTER TABLE `RequestStockInfo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkuc977h1l2s8j1bb3x2eu4ox` (`StockId`);

--
-- Índices de tabela `request_requeststockinfo`
--
ALTER TABLE `Request_RequestStockInfo`
  ADD UNIQUE KEY `UK_isn85jjdd73d2qegxjl108cbq` (`listInfo_id`),
  ADD KEY `FKdabkqgi34ohagtfabs4e9dbmp` (`Request_Id`);

--
-- Índices de tabela `state`
--
ALTER TABLE `State`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `stock`
--
ALTER TABLE `Stock`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FKku60e6hjv40k3iq4g2hi7qp93` (`SubCategoryId`),
  ADD KEY `FKlffvhn3hhs0v9d4dkf9rx0wp6` (`UserId`);

--
-- Índices de tabela `stock_location`
--
ALTER TABLE `Stock_Location`
  ADD UNIQUE KEY `UK_mlnuwpj4wv0wh50ogtu0m9acc` (`listLocation_id`),
  ADD KEY `FK6b6ct6kat4su8omi06y9jxkgc` (`Stock_Id`);

--
-- Índices de tabela `subcategory`
--
ALTER TABLE `SubCategory`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FKtacijmjucuhmkxqknqyliagdg` (`CategoryId`);

--
-- Índices de tabela `unit`
--
ALTER TABLE `Unit`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FKb8lcp6vx5mpnbkk8demxe5ao5` (`cityId`);

--
-- Índices de tabela `user`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`Id`);

--
-- Índices de tabela `userhasunit`
--
ALTER TABLE `userHasUnit`
  ADD KEY `FK9v46kh2h2j2yl6ntdfucthc3g` (`unitId`),
  ADD KEY `FK6s77d470ibbvo1wsrl1ikc1ic` (`userId`);

--
-- AUTO_INCREMENT de tabelas apagadas
--

--
-- AUTO_INCREMENT de tabela `category`
--
ALTER TABLE `Category`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `city`
--
ALTER TABLE `City`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de tabela `container`
--
ALTER TABLE `Container`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `location`
--
ALTER TABLE `Location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `logstock`
--
ALTER TABLE `LogStock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `request`
--
ALTER TABLE `Request`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `requeststockinfo`
--
ALTER TABLE `RequestStockInfo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `state`
--
ALTER TABLE `State`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de tabela `stock`
--
ALTER TABLE `Stock`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `subcategory`
--
ALTER TABLE `SubCategory`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `unit`
--
ALTER TABLE `Unit`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de tabela `user`
--
ALTER TABLE `User`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Restrições para dumps de tabelas
--

--
-- Restrições para tabelas `city`
--
ALTER TABLE `City`
  ADD CONSTRAINT `FK19lu7ofio5d2w879ch76hemmw` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`);

--
-- Restrições para tabelas `location`
--
ALTER TABLE `Location`
  ADD CONSTRAINT `FKoqk5vx9j2uu7ms0icaq5ps4vq` FOREIGN KEY (`container_id`) REFERENCES `container` (`id`);

--
-- Restrições para tabelas `logstock`
--
ALTER TABLE `LogStock`
  ADD CONSTRAINT `FK40lmn343ur5m2xulsbxqgrr1h` FOREIGN KEY (`user_Id`) REFERENCES `user` (`Id`),
  ADD CONSTRAINT `FKoel2315wxffeod1lp4uhx9n3k` FOREIGN KEY (`stock_Id`) REFERENCES `stock` (`Id`);

--
-- Restrições para tabelas `request`
--
ALTER TABLE `Request`
  ADD CONSTRAINT `FKebc60qntbd31p1vu50j1jvrm2` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`);

--
-- Restrições para tabelas `requeststockinfo`
--
ALTER TABLE `RequestStockInfo`
  ADD CONSTRAINT `FKkuc977h1l2s8j1bb3x2eu4ox` FOREIGN KEY (`StockId`) REFERENCES `stock` (`Id`);

--
-- Restrições para tabelas `request_requeststockinfo`
--
ALTER TABLE `Request_RequestStockInfo`
  ADD CONSTRAINT `FKa86093tgq4788k92ev2xtr2y5` FOREIGN KEY (`listInfo_id`) REFERENCES `requeststockinfo` (`id`),
  ADD CONSTRAINT `FKdabkqgi34ohagtfabs4e9dbmp` FOREIGN KEY (`Request_Id`) REFERENCES `request` (`Id`);

--
-- Restrições para tabelas `stock`
--
ALTER TABLE `Stock`
  ADD CONSTRAINT `FKku60e6hjv40k3iq4g2hi7qp93` FOREIGN KEY (`SubCategoryId`) REFERENCES `subcategory` (`Id`),
  ADD CONSTRAINT `FKlffvhn3hhs0v9d4dkf9rx0wp6` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`);

--
-- Restrições para tabelas `stock_location`
--
ALTER TABLE `Stock_Location`
  ADD CONSTRAINT `FK3o3lg1tc9h0h0j0ocxn0rhocy` FOREIGN KEY (`listLocation_id`) REFERENCES `location` (`id`),
  ADD CONSTRAINT `FK6b6ct6kat4su8omi06y9jxkgc` FOREIGN KEY (`Stock_Id`) REFERENCES `stock` (`Id`);

--
-- Restrições para tabelas `subcategory`
--
ALTER TABLE `SubCategory`
  ADD CONSTRAINT `FKtacijmjucuhmkxqknqyliagdg` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`Id`);

--
-- Restrições para tabelas `unit`
--
ALTER TABLE `Unit`
  ADD CONSTRAINT `FKb8lcp6vx5mpnbkk8demxe5ao5` FOREIGN KEY (`cityId`) REFERENCES `city` (`id`);

--
-- Restrições para tabelas `userhasunit`
--
ALTER TABLE `userHasUnit`
  ADD CONSTRAINT `FK6s77d470ibbvo1wsrl1ikc1ic` FOREIGN KEY (`userId`) REFERENCES `user` (`Id`),
  ADD CONSTRAINT `FK9v46kh2h2j2yl6ntdfucthc3g` FOREIGN KEY (`unitId`) REFERENCES `unit` (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
