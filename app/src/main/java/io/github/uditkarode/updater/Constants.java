/*
   * Copyright 2018 Udit Karode
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at:
   *
   * http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
 */

package io.github.uditkarode.updater;

public class Constants {

    // CHANGE THESE CONSTANTS //
    public static final String DEVICE_CODENAME = "zero";
    public static final String JSON_URL = "https://uditkarode.github.io/ROMUpdater/server.json";
    public static final String ROM_NAME = "ExampleROM";

    // DO NOT CHANGE //
    public static final String PROP_OTA_DATE = "ro.ota.date2";
    public static final String PROP_BUILD_DATE = "ro.build.date.utc";
    public static final String UPDATE_PACKAGE = "ROMUpdater/update.zip";
}
