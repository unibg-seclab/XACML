package eu.mosaicrown.pdp.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.research.xacml.api.Request;
import com.att.research.xacml.api.Response;
import com.att.research.xacml.api.pep.PEPException;
import com.att.research.xacml.std.dom.DOMRequest;
import com.att.research.xacml.std.dom.DOMStructureException;
import com.att.research.xacml.std.json.JSONStructureException;
import com.att.research.xacml.util.FactoryException;
import com.att.research.xacml.util.XACMLProperties;
import com.att.research.xacmlatt.pdp.test.TestBase;

public class TestRequest extends TestBase {
	private static final Logger logger	= LoggerFactory.getLogger(TestRequest.class);

	private Path policy;
	private Path request;
	private Request XACMLrequest;

	//
	// Our command line parameters
	//
	public static final String OPTION_POLICY = "policy";
	public static final String OPTION_REQUEST = "request";
	
	static {
		options.addOption(new Option(OPTION_POLICY, true, "Path to the policy file."));
		options.addOption(new Option(OPTION_REQUEST, true, "Path to the request file."));
	}
	
	public TestRequest(String[] args) throws ParseException, MalformedURLException, HelpException {
		super(args);
	}
	
	/* 
	 * Look for the -policy command line argument. This application needs a pointer to a specific policy
	 * in order to run.
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.att.research.xacmlatt.pdp.test.TestBase#parseCommands(java.lang.String[])
	 */
	@Override
	protected void parseCommands(String[] args) throws ParseException, MalformedURLException, HelpException {
		//
		// Have our super do its job
		//
		super.parseCommands(args);
		//
		// Look for the policy option
		//
		CommandLine cl;
		cl = new DefaultParser().parse(options, args);
		if (cl.hasOption(OPTION_POLICY)) {
			this.policy = Paths.get(cl.getOptionValue(OPTION_POLICY));
			//
			// Ensure it exists
			//
			if (Files.notExists(this.policy)) {
				throw new ParseException("Policy file does not exist.");
			}
		} else {
			throw new ParseException("You need to specify the policy file to be used.");
		}
		
		//
		// Look for the request option
		//
		if (cl.hasOption(OPTION_REQUEST)) {
			this.request = Paths.get(cl.getOptionValue(OPTION_REQUEST));
			//
			// Ensure it exists
			//
			if (Files.notExists(this.request)) {
				throw new ParseException("Request file does not exist.");
			}
		} else {
			throw new ParseException("You need to specify the request file to be used.");
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.att.research.xacmlatt.pdp.test.TestBase#configure()
	 */
	@Override
	protected void configure() throws FactoryException {
		//
		// Have our base class do its thing
		//
		super.configure();
		
		//
		// Set where the PDP can find the policy
		//
		XACMLProperties.setProperty(XACMLProperties.PROP_ROOTPOLICIES, "policy");
		XACMLProperties.setProperty("policy.file", this.policy.toString());
	}
	
	@Override
	public void run() throws IOException, FactoryException {
		// Configure this test
		this.configure();
		
		//
		// Generate the XACML Request
		//
		try {
			this.XACMLrequest = this.generateRequest(this.request, "");
		} catch (JSONStructureException | PEPException | DOMStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("It is not possible to decide because the policy is not loaded.");
			return;
		}
		
		System.out.println("=== REQUEST ===");
		System.out.println(this.XACMLrequest.toString());
		
		// Call the pdp and decide
		Response resp = this.callPDP(this.XACMLrequest);
		
		System.out.println("=== RESPONSE ===");
		System.out.println(resp.toString());
	}

	public static void main(String[] args) {
		try {
			new TestRequest(args).run();
		} catch (ParseException | IOException | FactoryException e) {
			logger.error(e.getMessage());
			System.err.println(e.getMessage());
		} catch (HelpException e) {
		}
	}

}
