setlocal

SET MVN_LIB=C:\Users\cnguyen.MITEK\.m2\repository
SET COMMONS_LANG=%MVN_LIB%\org\apache\commons\commons-lang3\3.0\commons-lang3-3.0.jar
SET COMMONS_DB=%MVN_LIB%\commons-dbutils\commons-dbutils\1.5\commons-dbutils-1.5.jar
SET COMMONS_IO=%MVN_LIB%\commons-io\commons-io\2.4\commons-io-2.4.jar
SET MYSQL_CONN=%MVN_LIB%\mysql\mysql-connector-java\5.1.25\mysql-connector-java-5.1.25.jar
SET JSOUP=%MVN_LIB%\org\jsoup\jsoup\1.8.3\jsoup-1.8.3.jar

java -classpath .\target\classes;^
..\DataWarehouse\target\classes;^
%COMMONS_LANG%;^
%COMMONS_DB%;^
%MYSQL_CONN%;^
%COMMONS_IO%;^
%JSOUP%^
 com.mohawk.webcrawler.Main %1 %2

endlocal