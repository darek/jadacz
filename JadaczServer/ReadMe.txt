################################################################################

Jadacz server v0.1
February 2007
README.TXT English

################################################################################

Thank you for choosing Jadacz.

################################################################################

Table of Contents

################################################################################

1. Configuration
2. Known Issues
3. Important Web Sites, Contact Information and Technical Support
4. Copyright


################################################################################

1. Configuration

################################################################################

All is done in config.xml.
To see an example config file scroll down to end of doc.

/---------------------------------------------\
|   key    |          what it does            |
|=============================================|
|   port   | Port to run the server on        |
|----------|----------------------------------|
|  dbuser  | User name in used data base      |
|----------|----------------------------------|
|  dbpass  | User password in used data base  |
|----------|----------------------------------|
|  dbtype  | Type of used data base           |
|----------|----------------------------------|
|  dbsrv   | Address of data base (if on same |
|          | computer use localhost or lo)    |
|----------|----------------------------------|       
|  dbport  | Port on which data base listens  |
|----------|----------------------------------|
|  dbname  | Name of data base to use         |
|----------|----------------------------------|
|jdbcdriver| JDBC driver to use               |
\---------------------------------------------/

Link to db is built like this: jdbc:dbtype://dbsrv:dbport/dbname
for example: jdbc:mysql://localhost:3306/jadacz 
if:
dbtype = mysql
dbsrv = localhost
dbname = jadacz


################################################################################

2. Known Issues

################################################################################

While creating this server we tried to find as many bugs as time let us.
All we found has been fixed.
If you find any bugs, contact us and we will try to fix it :).

################################################################################

3. Important Web Sites, Contact Information and Technical Support

################################################################################


3.1. Technical Support

Whenever you contact us, please include the following information:

-Exact error message reported (if applicable) and a brief description of the 
 problem you're encountering
-Operating system

Contact Us Over the Internet

This is the best way to contact us. Please check Jadacz site on sourceforge.net.


################################################################################

4. Copyright

################################################################################


GPL Licence.

Made by tecku and topdrive.

################################################################################

5. Example config.

################################################################################

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
<comment>Server Config</comment>
<entry key="port">8060</entry>
<entry key="dbuser">jadacz</entry>
<entry key="dbpass">jadacz</entry>
<entry key="dbtype">mysql</entry>
<entry key="dbsrv">localhost</entry>
<entry key="dbport">3306</entry>
<entry key="dbname">jadacz</entry>
<entry key="jdbcdriver">com.mysql.jdbc.Driver</entry>
</properties>
