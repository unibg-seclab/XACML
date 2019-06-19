package com.att.research.xacmlatt.pdp.std.temp_console_test;

import com.att.research.xacml.util.FactoryException;
import com.att.research.xacmlatt.pdp.test.TestBase;
import com.att.research.xacmlatt.pdp.test.annotations.TestAnnotation;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.IOException;

public class Proxy_test_1 {

    @Test
    public void proxy_test() {

        //init args as asked by test
        String args []  = new String[2];
        //current working directory
        args[0] ="-dir";
        args[1] ="src/test/resources/testsets/annotation";

        //get current dir to detect broken paths
        System.out.println("\n[+][+][+][+][+]The current working directory is " + System.getProperty("user.dir") + "[+][+][+][+][+]\n");

        //do test stuff
        try {

            new TestAnnotation(args).run();

        } catch (ParseException | IOException | FactoryException e) {
            System.out.println(e.getMessage());
        } catch (TestBase.HelpException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n[+][+][+][+][+]test executed[+][+][+][+][+]\n");

        return ;

    }

}