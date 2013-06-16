package org.cloudifysource.quality.iTests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

public class OnServiceUninstalledByonProvisioningDriver extends ByonProvisioningDriver {

    /**
     * @see org.cloudifysource.quality.iTests.test.esm.stateless.manual.memory.DedicatedStatelessManualByonCleanupTest
     */   
    @Override
    public void onServiceUninstalled(long timeout, TimeUnit timeUnit) {
    	//test expects this message in the log file:
    	logger.info("on-service-uninstalled-complete");
    }
}
