# First stage - Build WAR
FROM openjdk:7-jdk as build

# Install ant
RUN apt-get update && apt-get install -y --no-install-recommends \
        ant \
        libtomcat7-java \
        tomcat7-common \
    && rm -rf /var/lib/apt/lists/*

# Set work directory
ENV WORKDIR=/usr/src/app
WORKDIR ${WORKDIR}
RUN mkdir -p ${WORKDIR}

# Set library directory
ENV LIBDIR=WebContent/WEB-INF/lib
RUN mkdir -p ${LIBDIR}

# Get library files from libtomcat7-java
RUN ln -s /usr/share/java/catalina-ant.jar ${LIBDIR} && \
    ln -s /usr/share/java/tomcat-coyote.jar ${LIBDIR} && \
    ln -s /usr/share/java/tomcat-juli.jar ${LIBDIR} && \
    ln -s /usr/share/java/tomcat-util.jar ${LIBDIR}

# Download JSTL library
ADD http://repo2.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar ${LIBDIR}

# Download and unpack MYSQL JDBC Driver
ENV MYSQLCONNECT=mysql-connector-java-5.1.45
RUN cd /tmp && \
    wget -q https://dev.mysql.com/get/Downloads/Connector-J/${MYSQLCONNECT}.tar.gz && \
    tar xfz ${MYSQLCONNECT}.tar.gz && \
    cp ${MYSQLCONNECT}/${MYSQLCONNECT}-bin.jar ${WORKDIR}/${LIBDIR}/mysql.jar && \
    rm -rf ${MYSQLCONNECT}* && \
    cd -

# Copy source files
COPY . ${WORKDIR}

# Build WAR file
RUN ant war

## Second stage - Run application
FROM tomcat:7-jre7 as run

EXPOSE 8080

# Copy WAR from build stage
COPY --from=build /usr/src/app/11429074Email.war webapps/Email.war
