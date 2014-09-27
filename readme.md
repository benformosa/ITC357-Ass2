---
title: ITC357-Assignment 2 Report
author: Ben Formosa 11429074
header-includes:
    - \usepackage{fancyhdr}
    - \pagestyle{fancy}
    - \fancyfoot[L]{Ben Formosa 11429074}
    - \fancyfoot[C]{\thepage}
---

<!-- pandoc readme.md -o 11429074.pdf -f markdown+auto_identifiers -s -V papersize:"a4paper" -->

# Build and Deploy

## Pre-requisites

I tested using Tomcat 7 and MySQL 14.14 on Ubuntu:

```
$ /usr/share/tomcat7/bin/version.sh
Using CATALINA_BASE:   /usr/share/tomcat7
Using CATALINA_HOME:   /usr/share/tomcat7
Using CATALINA_TMPDIR: /usr/share/tomcat7/temp
Using JRE_HOME:        /usr
Using CLASSPATH:       /usr/share/tomcat7/bin/bootstrap.jar:/usr/share/tomcat7/bin/tomcat-juli.jar
Server version: Apache Tomcat/7.0.52 (Ubuntu)
Server built:   Jul 24 2014 08:38:51
Server number:  7.0.52.0
OS Name:        Linux
OS Version:     3.13.0-34-generic
Architecture:   amd64
JVM Version:    1.7.0_65-b32
JVM Vendor:     Oracle Corporation
```

```
$ mysql -V
mysql  Ver 14.14 Distrib 5.5.38, for debian-linux-gnu (x86_64) using readline 6.3
```

The following libraries should be in the directory `WebContent/WEB-INF/lib`

```
catalina-ant.jar
jstl-1.2.jar
mysql.jar
tomcat-coyote.jar
tomcat-juli.jar
tomcat-util.jar
```

## Database setup

The application uses MySQL as its database.
To create the database and tables, run this command:

```bash
mysql -u root -p < database.sql
```

After the database is created, load the demo data with:

```bash
mysql -u root -p ST11429074email < demo.sql
```

The application uses a properties file `WebContent/WEB-INF/db.properties`.
Edit this file to select a database server. The user will need read and write access to the database.

```properties
user=root
password=abc123
server=localhost
port=3306
database=ST11429074email
```

## Deploy

I built and deployed using ant and Tomcat Manager:

```bash
ant deploy
```

You could also build hotel.war and deploy to Tomcat via the admin gui.  
Create the war file with `ant war`, then deploy using the Tomcat Manager page.

![Deploy to Tomcat Admin](image/deploy.png)

# Run

Browse to the context path [http://localhost:8080/11429074Email/](http://localhost:8080/11429074Email/):  


