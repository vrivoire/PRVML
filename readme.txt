Le projet est à l'URL suivate: https://github.com/vrivoire/PRVML

Note: les tables Appointment and Availability aurraient pu être une seule table avec un Booléen mais j'ai suivit l'énoncé au plus près...

Dans le répertoire du projet cloné (PRVML):

L'image du graphe de la base de données se trouve dans le fichier "Database Graph.png"
La structure de la base de données SQL est dans le fichier "prvml.db.sql"

Construire le projet:
mvn clean install

Lancer le projet:
	java -jar target/PRVML-1.0.jar
	ou
	mvn -q spring-boot:run   (en couleurs)
	
Dans un navigateur, allez à: http://localhost:8080
La console H2 est à l'URL: http://127.0.0.1:8080/h2-console
        JDBC_URL: jdbc:h2:./database/prvml.db
        user: sa
        password: <-- vide


Énoncé:

Vous êtes en charge de développer l’API REST qui sert à supporter une application
simplifiée de prise de rendez-vous médical en ligne. Après analyse, vous arrivez à la
conclusion que vous aurez à implémenter les fonctionnalités suivantes :
- Le patient peut effectuer une recherche des différents professionnels de la clinique
- Le patient peut consulter les disponibilités d’un professionnel donné dans un
intervalle de temps défini (par exemple, les disponibilités du Dr. A entre le 8 et 12 mai
2019)
- Le patient peut prendre rendez-vous en choisissant l’une des disponibilités d’un
professionnel
Implémenter l’API REST simplifiée qui supporte ces fonctionnalités.
Quelques précisions :
- Clinique

o L’application ne supporte qu’une seule clinique pour l’instant
o Une clinique ne comporte qu’un seul attribut soit son nom qui agit
également à titre d’identifiant unique

- Patient
o
- Professionnel

o Une clinique peut avoir un ou plusieurs professionnels à son actif
o Un professionnel est défini par les attributs suivants :
§ Prénom
§ Nom
o La combinaison du prénom et du nom agit à titre d’identifiant unique

- Disponibilité

o Une disponibilité est une plage horaire pendant laquelle un
professionnel peut recevoir un patient, si ce dernier a pris rendez-vous
o Chaque professionnel possède une ou plusieurs disponibilités par jour
o Une disponibilité comporte les attributs suivants :
§ Date et heure (timestamp) de début
§ Date et heure (timestamp) de fin
o Afin de simplifier le problème, on suppose que les disponibilités sont
organisées par tranche de 15 minutes (8:00, 8:15, 8:30, etc.)

- Rendez-vous

o Une disponibilité qui a été choisie par un patient est convertie en
rendez-vous (une disponibilité = un rendez-vous)
o Un rendez-vous comporte les attributs suivants :
§ Un patient
§ Un professionnel
§ Une date de début
§ Une date de fin
o Comme pour les disponibilités, on supposera que les rendez-vous sont
organisés par tranche de 15 minutes