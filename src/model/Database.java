package model;

import controller.LoginViewController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    public static Statement statement;
    
    public static void startNewConnection() throws ClassNotFoundException{
        connection = null;
        String driver   = "com.mysql.jdbc.Driver";
        String db       = "U04RGT";
        String url      = "jdbc:mysql://52.206.157.109/" + db;
        String user     = "U04RGT";
        String pass     = "53688321456";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected to database : " + db);
        } catch (SQLException e) { 
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
    }
    public static void createAppointmentTable(){
        /*
        CREATE TABLE APPOINTMENTS (
            appointmentId INT(10) PRIMARY KEY NOT NULL,
            customerId INT(10),
            title VARCHAR(255),
            description TEXT,
            location TEXT,
            contact TEXT,
            url VARCHAR(255),
            start DATETIME,
            end DATETIME,
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(40)
        )
        */
        
        
        
        String TableName = "APPOINTMENTS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                        + "appointmentId INT(10) NOT NULL PRIMARY KEY,\n"
                        + "customerId INT(10),\n"
                        + "title VARCHAR(255),\n"
                        + "description TEXT,\n"
                        + "location TEXT,\n"
                        + "contact TEXT,\n"
                        + "url VARCHAR(255),\n"
                        + "start DATETIME,\n"
                        + "end DATETIME,\n"
                        + "createDate DATETIME,\n"
                        + "createdBy VARCHAR(40),\n"
                        + "lastUpdate TIMESTAMP,\n"
                        + "lastUpdatedBy VARCHAR(40)"
                        + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCustomerTable(){
        /*
        CREATE TABLE APPOINTMENTS (
            customerId INT(10) PRIMARY KEY NOT NULL,
            customerName VARCHAR(45),
            addressId INT(10),
            active TINYINT(1),
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdateBy VARCHAR(40)
        )           
        */
 
        String TableName = "CUSTOMERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                        + "customerId INT(10) PRIMARY KEY NOT NULL,\n"
                        + "customerName VARCHAR(45),\n"
                        + "addressId INT(10),\n"
                        + "active TINYINT(1),\n"
                        + "createDate DATETIME,\n"
                        + "createdBy VARCHAR(40),\n"
                        + "lastUpdate TIMESTAMP,\n"
                        + "lastUpdateBy VARCHAR(40)"
                        + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createUserTable(){
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
 
        String TableName = "USERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1) DEFAULT 1,\n"
                    + "createdBy VARCHAR(40) DEFAULT 'Admin',\n"
                    + "createDate DATETIME DEFAULT CURRENT_TIMESTAMP,\n"
                    + "lastUpdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50) DEFAULT 'Admin'"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createAddressTable(){
        /*
        CREATE TABLE ADDRESS (
            addressId INT(10),
            address VARCHAR(50),
            address2 VARCHAR(50),
            cityId INT(10),
            postalCode VARCHAR(10),
            phone VARCHAR(20),
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdateBy VARCHAR(40)
        )       
        */
 
        String TableName = "ADDRESS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "addressId INT(10),\n"
                    + "address VARCHAR(50),\n"
                    + "address2 VARCHAR(50),\n"
                    + "cityId INT(10),\n"
                    + "postalCode VARCHAR(10),\n"
                    + "phone VARCHAR(20),\n"
                    + "createDate DATETIME,\n"
                    + "createdBy VARCHAR(40),\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdateBy VARCHAR(40)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCityTable(){
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
 
        String TableName = "USERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1),\n"
                    + "createdBy VARCHAR(40),\n"
                    + "createDate DATETIME,\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCountryTable() throws SQLException{
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
        int rowCount = 0;
        String query = "SELECT COUNT(*) AS count FROM country";
        
        ResultSet results = resultQuery(query);
        while (results.next()){
            rowCount = results.getInt(1);
        }
        if (rowCount == 0){
            String insert = "INSERT INTO country"
                + " VALUES "
                +"(1,'AD - Andorra',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(2,'AE - United Arab Emirates',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(3,'AF - Afghanistan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(4,'AG - Antigua and Barbuda',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(5,'AI - Anguilla',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(6,'AL - Albania',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(7,'AM - Armenia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(8,'AO - Angola',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(9,'AQ - Antarctica',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(10,'AR - Argentina',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(11,'AS - American Samoa',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(12,'AT - Austria',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(13,'AU - Australia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(14,'AW - Aruba',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(15,'AZ - Azerbaijan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(16,'BA - Bosnia and Herzegovina',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(17,'BB - Barbados',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(18,'BD - Bangladesh',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(19,'BE - Belgium',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(20,'BF - Burkina Faso',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(21,'BG - Bulgaria',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(22,'BH - Bahrain',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(23,'BI - Burundi',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(24,'BJ - Benin',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(25,'BL - Saint Barthelemy',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(26,'BM - Bermuda',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(27,'BN - Brunei',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(28,'BO - Bolivia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(29,'BR - Brazil',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(30,'BS - Bahamas, The',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(31,'BT - Bhutan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(32,'BV - Bouvet Island',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(33,'BW - Botswana',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(34,'BY - Belarus',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(35,'BZ - Belize',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(36,'CA - Canada',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(37,'CC - Cocos (Keeling) Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(38,'CD - Congo, Democratic Republic of the',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(39,'CF - Central African Republic',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(40,'CG - Congo, Republic of the',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(41,'CH - Switzerland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(42,'CI - Cote d Ivoire',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(43,'CK - Cook Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(44,'CL - Chile',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(45,'CM - Cameroon',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(46,'CN - China',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(47,'CO - Colombia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(48,'CR - Costa Rica',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(49,'CU - Cuba',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(50,'CV - Cape Verde',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(51,'CW - Curacao',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(52,'CX - Christmas Island',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(53,'CY - Cyprus',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(54,'CZ - Czech Republic',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(55,'DE - Germany',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(56,'DJ - Djibouti',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(57,'DK - Denmark',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(58,'DM - Dominica',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(59,'DO - Dominican Republic',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(60,'DZ - Algeria',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(61,'EC - Ecuador',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(62,'EE - Estonia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(63,'EG - Egypt',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(64,'EH - Western Sahara',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(65,'ER - Eritrea',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(66,'ES - Spain',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(67,'ET - Ethiopia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(68,'FI - Finland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(69,'FJ - Fiji',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(70,'FK - Falkland Islands (Islas Malvinas)',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(71,'FM - Micronesia, Federated States of',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(72,'FO - Faroe Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(73,'FR - France',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(74,'FX - France, Metropolitan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(75,'GA - Gabon',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(76,'GB - United Kingdom',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(77,'GD - Grenada',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(78,'GE - Georgia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(79,'GF - French Guiana',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(80,'GG - Guernsey',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(81,'GH - Ghana',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(82,'GI - Gibraltar',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(83,'GL - Greenland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(84,'GM - Gambia, The',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(85,'GN - Guinea',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(86,'GP - Guadeloupe',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(87,'GQ - Equatorial Guinea',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(88,'GR - Greece',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(89,'GS - South Georgia and the Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(90,'GT - Guatemala',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(91,'GU - Guam',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(92,'GW - Guinea-Bissau',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(93,'GY - Guyana',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(94,'HK - Hong Kong',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(95,'HM - Heard Island and McDonald Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(96,'HN - Honduras',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(97,'HR - Croatia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(98,'HT - Haiti',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(99,'HU - Hungary',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(100,'ID - Indonesia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(101,'IE - Ireland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(102,'IL - Israel',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(103,'IM - Isle of Man',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(104,'IN - India',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(105,'IO - British Indian Ocean Territory',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(106,'IQ - Iraq',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(107,'IR - Iran',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(108,'IS - Iceland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(109,'IT - Italy',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(110,'JE - Jersey',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(111,'JM - Jamaica',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(112,'JO - Jordan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(113,'JP - Japan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(114,'KE - Kenya',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(115,'KG - Kyrgyzstan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(116,'KH - Cambodia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(117,'KI - Kiribati',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(118,'KM - Comoros',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(119,'KN - Saint Kitts and Nevis',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(120,'KP - Korea, North',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(121,'KR - Korea, South',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(122,'KW - Kuwait',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(123,'KY - Cayman Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(124,'KZ - Kazakhstan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(125,'LA - Laos',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(126,'LB - Lebanon',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(127,'LC - Saint Lucia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(128,'LI - Liechtenstein',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(129,'LK - Sri Lanka',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(130,'LR - Liberia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(131,'LS - Lesotho',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(132,'LT - Lithuania',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(133,'LU - Luxembourg',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(134,'LV - Latvia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(135,'LY - Libya',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(136,'MA - Morocco',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(137,'MC - Monaco',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(138,'MD - Moldova',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(139,'ME - Montenegro',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(140,'MF - Saint Martin',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(141,'MG - Madagascar',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(142,'MH - Marshall Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(143,'MK - Macedonia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(144,'ML - Mali',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(145,'MM - Burma',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(146,'MN - Mongolia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(147,'MO - Macau',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(148,'MP - Northern Mariana Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(149,'MQ - Martinique',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(150,'MR - Mauritania',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(151,'MS - Montserrat',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(152,'MT - Malta',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(153,'MU - Mauritius',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(154,'MV - Maldives',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(155,'MW - Malawi',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(156,'MX - Mexico',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(157,'MY - Malaysia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(158,'MZ - Mozambique',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(159,'NA - Namibia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(160,'NC - New Caledonia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(161,'NE - Niger',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(162,'NF - Norfolk Island',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(163,'NG - Nigeria',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(164,'NI - Nicaragua',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(165,'NL - Netherlands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(166,'NO - Norway',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(167,'NP - Nepal',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(168,'NR - Nauru',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(169,'NU - Niue',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(170,'NZ - New Zealand',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(171,'OM - Oman',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(172,'PA - Panama',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(173,'PE - Peru',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(174,'PF - French Polynesia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(175,'PG - Papua New Guinea',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(176,'PH - Philippines',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(177,'PK - Pakistan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(178,'PL - Poland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(179,'PM - Saint Pierre and Miquelon',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(180,'PN - Pitcairn Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(181,'PR - Puerto Rico',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(182,'PS - Gaza Strip',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(183,'PS - West Bank',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(184,'PT - Portugal',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(185,'PW - Palau',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(186,'PY - Paraguay',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(187,'QA - Qatar',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(188,'RE - Reunion',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(189,'RO - Romania',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(190,'RS - Serbia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(191,'RU - Russia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(192,'RW - Rwanda',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(193,'SA - Saudi Arabia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(194,'SB - Solomon Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(195,'SC - Seychelles',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(196,'SD - Sudan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(197,'SE - Sweden',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(198,'SG - Singapore',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(199,'SH - Saint Helena, Ascension, and Tristan da Cunha',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(200,'SI - Slovenia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(201,'SJ - Svalbard',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(202,'SK - Slovakia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(203,'SL - Sierra Leone',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(204,'SM - San Marino',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(205,'SN - Senegal',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(206,'SO - Somalia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(207,'SR - Suriname',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(208,'SS - South Sudan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(209,'ST - Sao Tome and Principe',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(210,'SV - El Salvador',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(211,'SX - Sint Maarten',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(212,'SY - Syria',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(213,'SZ - Swaziland',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(214,'TC - Turks and Caicos Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(215,'TD - Chad',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(216,'TF - French Southern and Antarctic Lands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(217,'TG - Togo',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(218,'TH - Thailand',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(219,'TJ - Tajikistan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(220,'TK - Tokelau',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(221,'TL - Timor-Leste',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(222,'TM - Turkmenistan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(223,'TN - Tunisia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(224,'TO - Tonga',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(225,'TR - Turkey',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(226,'TT - Trinidad and Tobago',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(227,'TV - Tuvalu',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(228,'TW - Taiwan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(229,'TZ - Tanzania',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(230,'UA - Ukraine',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(231,'UG - Uganda',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(232,'UM - United States Minor Outlying Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(233,'US - United States',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(234,'UY - Uruguay',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(235,'UZ - Uzbekistan',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(236,'VA - Holy See (Vatican City)',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(237,'VC - Saint Vincent and the Grenadines',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(238,'VE - Venezuela',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(239,'VG - British Virgin Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(240,'VI - Virgin Islands',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(241,'VN - Vietnam',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(242,'VU - Vanuatu',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(243,'WF - Wallis and Futuna',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(244,'WS - Samoa',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(245,'XK - Kosovo',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(246,'YE - Yemen',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(247,'YT - Mayotte',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(248,'ZA - South Africa',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(249,'ZM - Zambia',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin'),"
                +"(250,'ZW - Zimbabwe',CURRENT_TIMESTAMP,'admin',CURRENT_TIMESTAMP,'admin')";

                Database.actionQuery(insert);
        }
    }
    public static void actionQuery(String query) throws SQLException{
        statement = connection.createStatement();
        statement.execute(query);
    }
    public static void printQuery(String query) throws SQLException{
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        while (results.next()){
            System.out.println(
                "appointmentId: " + results.getString("appointmentId")
                + " | title: " + results.getString("title")
                + " | time: " + results.getString("start")
            );
        }
        System.out.println(results);
    }
    public static void closeConnection(Connection connection) {
       try {
         if (connection != null)
           connection.close();
       } catch (SQLException e) { }
    }
    public static ResultSet resultQuery(String query) throws SQLException{
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        return results;
    }
    
    
}
