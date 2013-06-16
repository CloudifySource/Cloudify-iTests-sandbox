package org.cloudifysource.quality.iTests;

import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationAwareByonProvisioningDriver extends ByonProvisioningDriver {

    private List<String> locationsToStartMachinesWith;
	private AtomicInteger index;


	
	public LocationAwareByonProvisioningDriver () {
		this.locationsToStartMachinesWith = Arrays.asList(getAvailabilityZones());
		this.index = new AtomicInteger(0);
	}

    public String[] getAvailabilityZones()
    {
        return new String[] { "zone1", "zone2", "zone3","zone4" };
    }

    public MachineDetails startMachine(String locationId, long timeout, TimeUnit timeUnit)
            throws TimeoutException, CloudProvisioningException
    {
        MachineDetails machineDetails = null;
        if (this.locationsToStartMachinesWith.contains(locationId)){
            machineDetails = super.startMachine(locationId, timeout, timeUnit);
            machineDetails.setLocationId(locationId);
        }
        else {
            String location = this.locationsToStartMachinesWith.get(this.index.getAndIncrement() % this.locationsToStartMachinesWith.size());
            machineDetails = super.startMachine(location, timeout, timeUnit);
            machineDetails.setLocationId(location);

        }
        return machineDetails;
    }

}
