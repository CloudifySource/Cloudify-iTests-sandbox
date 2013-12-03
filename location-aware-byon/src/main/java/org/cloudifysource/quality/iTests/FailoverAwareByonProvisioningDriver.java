package org.cloudifysource.quality.iTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.cloudifysource.dsl.internal.CloudifyConstants;
import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.ComputeDriverConfiguration;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.ProvisioningContext;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

public class FailoverAwareByonProvisioningDriver extends ByonProvisioningDriver {

    private String challenge;
    private boolean enabled;
    private File file;

	@Override
    public void setConfig(final ComputeDriverConfiguration configuration) throws CloudProvisioningException {
        super.setConfig(configuration);
        final String serviceName = configuration.getServiceName();
        enabled = !serviceName.equals(CloudifyConstants.MANAGEMENT_SPACE_NAME) &&
                  !serviceName.equals(CloudifyConstants.MANAGEMENT_REST_SERVICE_NAME) &&
                  !serviceName.equals(CloudifyConstants.MANAGEMENT_WEBUI_SERVICE_NAME);
        
        if (enabled) {
            try {
                // service name is changes each test, but does not change when esm restarts. 
                file = new File(serviceName+"-challenge.txt");
                try {
                    challenge = readChallenge(file);
                    logger.info("Read challenge " + challenge + " from file " + file.getAbsolutePath());
                } catch(FileNotFoundException ex) {
                    challenge = String.valueOf(new Random().nextInt());
                    writeChallenge(file, challenge);
                    logger.info("Created a new challenge " + challenge + " and saved it to the file " + file.getAbsolutePath());
                }
            }
            catch (IOException e) {
                throw new CloudProvisioningException("Failed to handle challenge file", e);
            }
        }
    }

	/**
	 * This method is only called when pu has undeployed, or ESM does graceful shutdown.
	 * It is not called when ESM is killed with "kill -9" which is a good thing since
	 * the next ESM should be able to read this file.
	 */
	@Override
    public void close() {
	    boolean deleted = file.delete();
	    if (!deleted) {
	        file.deleteOnExit();
	    }
	}

    @Override
    public MachineDetails startMachine(final ProvisioningContext context, final long timeout, final TimeUnit timeUnit)
            throws TimeoutException, CloudProvisioningException {

        final MachineDetails machineDetails = super.startMachine(context, timeout, timeUnit);
        if (enabled) {
            final MachineDetails prevMachineDetails = context.getPreviousMachineDetails();
            return injectEnvironmentToMachineDetails(machineDetails, prevMachineDetails);
        }
        return machineDetails;
    }

    private MachineDetails injectEnvironmentToMachineDetails(
            final MachineDetails machineDetails,
            final MachineDetails prevMachineDetails) {
        

        if (prevMachineDetails  == null) {
        	final Map<String, String> env = new LinkedHashMap<String,String>();
        	env.put("challenge", challenge);
        	machineDetails.setEnvironment(env);
        }
        else {
        	final String response = prevMachineDetails.getEnvironment().get("challenge");
        	if (response != null && response.equals(challenge)) {
        		// test expects waits for this ESM log entry to pass  
        		logger.info("failover-aware-provisioning-driver");
        	}
        	else {
        		throw new IllegalStateException("New machine was started with wrong prevMachineDetails challenge. Expected " + challenge +" actual " + response);
        	}
        }
        return machineDetails;
    }

    private static void writeChallenge(File file, String challenge) throws IOException {
        final BufferedWriter bw  = new BufferedWriter(new FileWriter(file));
        try {
            bw.write(challenge);
        }
        finally {
            bw.close();
        }
    }

    private static String readChallenge(File file) throws FileNotFoundException, IOException {
        final BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

}
