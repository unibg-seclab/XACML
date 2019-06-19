package com.att.research.xacmlatt.pdp.std.temp_console_test;

import com.att.research.xacml.util.FactoryException;
import com.att.research.xacmlatt.pdp.test.TestBase;
import com.att.research.xacmlatt.pdp.test.policy.TestPolicy;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import java.io.IOException;

public class Proxy_test_2 {

    @Test
    public void proxy_test() {

        //TODO the test seems not really working, why?

        // to compile new tests inside this module please type
        // mvn package -DskipTests
        // to run this test position inside the directory of XACML-TEST module and run the following command
        // mvn -Dtest=Proxy_test_2 -Dxacml.properties=src/test/resources/testsets/policy/xacml_mod.properties test

        //init args as asked by test
        String args []  = new String[4];
        //current working directory
        args[0] ="-dir";
        args[1] ="src/test/resources/testsets/pip/configurable-csv-hyper";
        //current policy
        args[2] ="-policy";
        args[3] ="src/test/resources/testsets/pip/configurable-csv-hyper/CSV-Legal-Age-Marriage-v1.xml";

        //get current dir to detect broken paths
        System.out.println("\n[+][+][+][+][+]The current working directory is " + System.getProperty("user.dir") + "[+][+][+][+][+]\n");
        //get current JVM system properties
        System.out.println("\n[+][+][+][+][+]The current xacml.properties is " + System.getProperty("xacml.properties") + "[+][+][+][+][+]\n");


        //do test stuff
        try {

            new TestPolicy(args).run();

        } catch (ParseException | IOException | FactoryException e) {
            System.out.println(e.getMessage());
        } catch (TestBase.HelpException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n[+][+][+][+][+]test executed[+][+][+][+][+]\n");

        return ;
    }

}