package org.cloudifysource.quality.iTests;

import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ReleaseCloudResourcesByonProvisioningDriver extends ByonProvisioningDriver {

    private Logger logger = Logger.getLogger(this.getClass().getName()); 

    /**
     * @see org.cloudifysource.quality.iTests.test.esm.stateless.manual.memory.DedicatedStatelessManualPerZoneByonFailoverTest
     */   
    @Override
    public void releaseCloudResources(long timeout, TimeUnit timeUnit) {
    	if (super.getCloud().getCustom().containsKey("release-cloud-resources-failure-injection")) {
    		throw new IllegalStateException("release-cloud-resources-failure-injection");
    	}
    	logger.info("release-cloud-resources-complete");
    }
}
