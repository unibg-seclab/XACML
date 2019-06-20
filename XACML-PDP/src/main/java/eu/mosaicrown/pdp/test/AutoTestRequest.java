package eu.mosaicrown.pdp.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;

import com.att.research.xacml.util.FactoryException;
import com.att.research.xacmlatt.pdp.test.TestBase.HelpException;

public class AutoTestRequest {
	
	public static void main(String[] args) {
		String testDir = "/home/marco/git/XACML/XACML-PDP/src/main/resources";
		List<String> allPolicies = new ArrayList<String>();
		List<String> allRequests = new ArrayList<String>();
		
		//
		// Get all policies
		//
		try (Stream<Path> walk = Files.walk(Paths.get(testDir, "policies"))) {
			allPolicies = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
//			allPolicies.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//
		// Get all requests
		//
		try (Stream<Path> walk = Files.walk(Paths.get(testDir, "requests"))) {
			allRequests = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
//			allRequests.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Found " + allPolicies.size() + " policies, and " +
				allRequests.size() + " requests");
		
		for (String policy : allPolicies) {
			for (String request : allRequests) {
				System.out.println("Policy " +  FilenameUtils.getName(policy));
				System.out.println("Request " +  FilenameUtils.getName(request));
				
				//
				// Build arguments for the test
				//
				String[] argumentsToUse = new String[6];
				argumentsToUse[0] = "-dir";
				argumentsToUse[1] = testDir;
				argumentsToUse[2] = "-policy";
				argumentsToUse[3] = policy;
				argumentsToUse[4] = "-request";
				argumentsToUse[5] = request;
				
				//
				// Execute test
				//
				System.out.println("Evaluation");
				try {
					new TestRequest(argumentsToUse).run();
				} catch (ParseException | IOException | FactoryException e) {
					System.err.println(e.getMessage());
				} catch (HelpException e) {
				}
				
				System.out.println("----------------------------------------");
			}
		}
		
		
	}

}
