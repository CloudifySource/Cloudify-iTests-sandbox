package org.cloudifysource.quality.iTests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

public class OnServiceUninstalledByonProvisioningDriver extends ByonProvisioningDriver {

    private Logger logger = Logger.getLogger(this.getClass().getName()); 

    /**
     * @see org.cloudifysource.quality.iTests.test.esm.stateless.manual.memory.DedicatedStatelessManualPerZoneByonFailoverTest
     */   
    @Override
    public void onServiceUninstalled(long timeout, TimeUnit timeUnit) {
    	if (super.getCloud().getCustom().containsKey("on-service-uninstalled-failure-injection")) {
    		throw new IllegalStateException("on-service-uninstalled-failure-injection");
    	}
    	logger.info("on-service-uninstalled-complete");
    }
}
