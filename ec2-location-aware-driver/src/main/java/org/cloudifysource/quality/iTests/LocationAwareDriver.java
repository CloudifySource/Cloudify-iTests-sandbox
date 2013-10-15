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
import org.cloudifysource.esc.driver.provisioning.jclouds.DefaultProvisioningDriver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: elip
 * Date: 5/18/13
 * Time: 3:42 PM
 */
public abstract class LocationAwareDriver extends DefaultProvisioningDriver {

    private List<String> locationsToStartMachinesWith;
    private AtomicInteger index;

    public LocationAwareDriver() {
        locationsToStartMachinesWith = Arrays.asList(getAvailabilityZones());
        index = new AtomicInteger(0);
    }

    @Override
    public MachineDetails startMachine(final ProvisioningContext context, final long timeout, final TimeUnit unit)
            throws TimeoutException, CloudProvisioningException {
        MachineDetails machineDetails;
        if (locationsToStartMachinesWith.contains(context.getLocationId())) {
            logger.info((new StringBuilder()).append("recieved request from Cloudify Adapter to start machine in a " +
                    "specific cloud location = ").append(context.getLocationId()).toString());
            machineDetails = super.startMachine(context, timeout, unit);
        } else {
            logger.info((new StringBuilder()).append("recieved request from Cloudify Adapter to start machine with " +
                    "zone = ").append(context.getLocationId()).append(". which is not a cloud specific location. continuing with " +
                    "" +
                    "ound robin machine allocation").toString());
            String currentLocation = (String) locationsToStartMachinesWith.get(index.getAndIncrement()
                    % locationsToStartMachinesWith.size());
            logger.info((new StringBuilder()).append("starting machine with location : ").append(currentLocation).toString());
            machineDetails = super.startMachine(context, timeout, unit);
        }
        return machineDetails;
    }

    /**
     * Get available availability zones.
     * To enable running in many regions with the same code.
     * @return The availability zones.
     */
    protected abstract String[] getAvailabilityZones();


}
