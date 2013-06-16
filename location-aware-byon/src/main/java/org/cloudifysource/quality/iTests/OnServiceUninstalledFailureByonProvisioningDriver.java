package org.cloudifysource.quality.iTests;

import java.util.concurrent.TimeUnit;

import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

public class OnServiceUninstalledFailureByonProvisioningDriver extends ByonProvisioningDriver {

    /**
     * @see org.cloudifysource.quality.iTests.test.esm.stateless.manual.memory.DedicatedStatelessManualByonCleanupFailureTest
     */   
    @Override
    public void onServiceUninstalled(long timeout, TimeUnit timeUnit) {
		logger.info("throwing exception!");
		//test expects this exception message in the log file:
		throw new IllegalStateException("on-service-uninstalled-failure-injection");
    }
}
