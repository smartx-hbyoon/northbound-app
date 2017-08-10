/*
 * Copyright 2017-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.ac.gist.fsteer.cli;

import kr.ac.gist.fsteer.HostPair;
import kr.ac.gist.fsteer.SteerService;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.DeviceId;

import java.util.concurrent.ConcurrentMap;

/**
 * Sample Apache Karaf CLI command
 */
@Command(scope = "onos", name = "steer",
         description = "Sample Apache Karaf CLI command")
public class SteerCommand extends AbstractShellCommand {

    private static final String DEVICE_FMT = "================= %s =================";
    private static final String HOST_FMT = "src = %s, dst = %s, counter = %d";

    @Argument(index = 0,name = "deviceId", description = "Device ID of switch", required = false, multiValued = false)
    private String deviceId =null;

    private SteerService service;
    private ConcurrentMap<DeviceId,ConcurrentMap<HostPair,Long>> map;

    @Override
    protected void execute() {
        service = get(SteerService.class);
        map = service.getMap();

        DeviceId device;

        if (deviceId !=null){
            device = DeviceId.deviceId(deviceId);
            if (!map.containsKey(device)) {
                return ;
            }
            map.get(device).forEach((k,v)-> print(HOST_FMT, k.getSrc(),k.getDst(),v));
        } else {
            for (DeviceId devId : map.keySet()){
                print(DEVICE_FMT,devId);
                map.get(devId).forEach((k,v)-> print(HOST_FMT, k.getSrc(),k.getDst(),v));
            }
        }
    }
}
