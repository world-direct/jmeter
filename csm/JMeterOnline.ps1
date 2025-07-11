#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

$jmeterExe = "$PSScriptRoot\apache-jmeter-5.6.3\bin\jmeter.bat"

#$PSScriptRoot is the path of the current script
$arguments = @()
$arguments += "--addprop $PSScriptRoot\csmjmeterOnline.properties"
$arguments += "--testfile $PSScriptRoot\CsmJMeterOnlineLasttest.jmx"

Start-Process -FilePath $jmeterExe -ArgumentList $arguments -NoNewWindow