/*
 * Copyright (c) 2011 GigaSpaces Technologies Ltd. All rights reserved
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.cloudifysource.quality.iTests;

import org.cloudifysource.esc.driver.provisioning.CloudProvisioningException;
import org.cloudifysource.esc.driver.provisioning.MachineDetails;
import org.cloudifysource.esc.driver.provisioning.ProvisioningContext;
import org.cloudifysource.esc.driver.provisioning.byon.ByonProvisioningDriver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class BasicLocationAwareByonProvisioningDriver extends ByonProvisioningDriver {

    @Override
    public MachineDetails startMachine(final ProvisioningContext context, final long timeout, final TimeUnit timeUnit)
            throws TimeoutException, CloudProvisioningException {
        MachineDetails machineDetails = super.startMachine(context, timeout, timeUnit);
        machineDetails.setLocationId(context.getLocationId());
        return machineDetails;
    }
}
