-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 30 mai 2024 à 18:01
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `crm_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `info_add` varchar(100) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `siret` varchar(100) DEFAULT NULL,
  `activity` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `civility` varchar(100) DEFAULT NULL,
  `company_activity` varchar(100) DEFAULT NULL,
  `company_address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id`, `name`, `country`, `address`, `email`, `phone`, `website`, `info_add`, `company_name`, `siret`, `activity`, `status`, `civility`, `company_activity`, `company_address`) VALUES
(1, 'John Doe', 'France', '123 Rue de Paris, 75001 Paris', 'john.doe@example.com', '+33123456789', 'http://johndoe.com', 'N/A', 'Doe Enterprises', '12345678900010', 'Consulting', 'Active', 'M.', 'Business Consulting', '123 Rue de Paris, 75001 Paris'),
(2, 'Jane Smith', 'Canada', '456 Maple Street, Toronto, ON', 'jane.smith@example.ca', '+14165551234', 'http://janesmith.ca', 'N/A', 'Smith Solutions', '98765432100012', 'IT Services', 'Active', 'Ms.', 'Software Development', '456 Maple Street, Toronto, ON'),
(3, 'Luis Garcia', 'Spain', '789 Calle Mayor, 28013 Madrid', 'luis.garcia@example.es', '+34912345678', 'http://luisgarcia.es', 'N/A', 'Garcia Innovaciones', '56473829100022', 'Engineering', 'Active', 'Sr.', 'Civil Engineering', '789 Calle Mayor, 28013 Madrid'),
(4, 'Anna Müller', 'Germany', '321 Berliner Strasse, 10117 Berlin', 'anna.mueller@example.de', '+493012345678', 'http://annamueller.de', 'N/A', 'Müller GmbH', '13579246800033', 'Finance', 'Active', 'Frau', 'Financial Consulting', '321 Berliner Strasse, 10117 Berlin'),
(5, 'Maria Rossi', 'Italy', '654 Via Roma, 00184 Rome', 'maria.rossi@example.it', '+390612345678', 'http://mariarossi.it', 'N/A', 'Rossi & Partners', '24681357900044', 'Legal Services', 'Active', 'Sig.ra', 'Legal Consulting', '654 Via Roma, 00184 Rome');


-- --------------------------------------------------------

--
-- Structure de la table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `comment` text NOT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `comments`
--

INSERT INTO `comments` (`id`, `date`, `comment`, `id_client`) VALUES
(1, '2023-01-15', 'Excellent service and very professional.', 1),
(2, '2023-02-20', 'Quick response times and great support.', 2),
(3, '2023-03-05', 'Highly recommend their engineering services.', 3),
(4, '2023-04-12', 'Very knowledgeable and reliable.', 4),
(5, '2023-05-30', 'Great legal advice and support.', 5);


-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `identifier` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `identifier`, `password`, `role`) VALUES
(8, 'admin', 'admin', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
