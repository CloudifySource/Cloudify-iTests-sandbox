package org.cloudifysource.quality.iTests;

import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.ProvisioningContext;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FailoverAwareByonProvisioningDriver extends ByonProvisioningDriver {

	final String challenge = String.valueOf(new Random().nextInt());

	@Override
    public MachineDetails startMachine(final ProvisioningContext context, final long timeout, final TimeUnit timeUnit)
            throws TimeoutException, CloudProvisioningException {
    {
		
    	final MachineDetails prevMachineDetails = context.getPreviousMachineDetails();
    	
        final MachineDetails machineDetails = super.startMachine(context, timeout, timeUnit);
        if (prevMachineDetails  == null) {
        	machineDetails.setEnv("challenge", challenge);
        }
        else {
        	String response = prevMachineDetails.getEnv("challenge");
        	if (response != null && response.equals(challenge)) {
        		// test expects waits for this ESM log entry to pass  
        		logger.info("failover-aware-provisioning-driver");
        	}
        	else {
        		throw new IllegalStateException("New machine was started with wrong machine details. Expected " + challenge +" actual " + response);
        	}
        }
        return machineDetails;

    }

}
