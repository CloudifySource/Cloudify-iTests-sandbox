package org.cloudifysource.quality.iTests;

import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BasicLocationAwareByonProvisioningDriver extends ByonProvisioningDriver {

    public MachineDetails startMachine(String locationId, long timeout, TimeUnit timeUnit)
            throws TimeoutException, CloudProvisioningException
    {
        MachineDetails machineDetails = super.startMachine(locationId, timeout, timeUnit);
        machineDetails.setLocationId(locationId);
        return machineDetails;

    }

}
