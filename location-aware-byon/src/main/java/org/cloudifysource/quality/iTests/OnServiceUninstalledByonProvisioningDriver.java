package org.cloudifysource.quality.iTests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

public class OnServiceUninstalledByonProvisioningDriver extends ByonProvisioningDriver {

    /**
     * @see org.cloudifysource.quality.iTests.test.esm.stateless.manual.memory.DedicatedStatelessManualPerZoneByonFailoverTest
     */   
    @Override
    public void onServiceUninstalled(long timeout, TimeUnit timeUnit) {
    	if (super.getCloud().getCustom().containsKey("on-service-uninstalled-failure-injection")) {
    		logger.info("throwing exception!");
    		//test expects this message in the log file:
    		throw new IllegalStateException("on-service-uninstalled-failure-injection");
    	}
    	//test expects this message in the log file:
    	logger.info("on-service-uninstalled-complete");
    }
}
